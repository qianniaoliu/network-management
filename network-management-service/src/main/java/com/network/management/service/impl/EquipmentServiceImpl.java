package com.network.management.service.impl;

import com.network.management.agent.DeviceMonitorContext;
import com.network.management.agent.collector.Collector;
import com.network.management.common.exception.Assert;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.bo.DeviceStatusResultBo;
import com.network.management.domain.bo.OtherDeviceStatusBo;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.enums.DeviceTypeEnum;
import com.network.management.domain.enums.EquipmentStatusEnum;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.excel.DeviceStatusData;
import com.network.management.domain.excel.FlashStationStatusExcel;
import com.network.management.domain.excel.OtherDeviceStatusExcel;
import com.network.management.domain.excel.WebStationStatusExcel;
import com.network.management.domain.search.EquipmentStatusSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.FlashStationStatusVo;
import com.network.management.domain.vo.WebStationStatusVo;
import com.network.management.mapper.EquipmentMapper;
import com.network.management.service.DeviceStatueHandler;
import com.network.management.service.EquipmentService;
import com.network.management.service.converter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author yusheng
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentMapper equipmentMapper;

    private final DeviceStatueHandler deviceStatueHandler;

    public EquipmentServiceImpl(EquipmentMapper equipmentMapper, DeviceStatueHandler deviceStatueHandler) {
        this.equipmentMapper = equipmentMapper;
        this.deviceStatueHandler = deviceStatueHandler;
    }

    @Autowired
    private DeviceMonitorContext deviceMonitorContext;
    @Autowired
    private DeviceConverter deviceConverter;
    @Autowired
    private DeviceStatusVoConverter deviceStatusVoConverter;

    @Autowired
    private WebStationStatusExcelConverter webStationStatusExcelConverter;
    @Autowired
    private OtherDeviceStatusExcelConverter otherDeviceStatusExcelConverter;
    @Autowired
    private FlashStationStatusExcelConverter flashStationStatusExcelConverter;

    @Override
    public Integer add(Equipment equipment) {
        Assert.notNull(equipment, "equipment对象不能为空");
        equipment.initCreateInfo();
        equipmentMapper.insert(equipment);
        return equipment.getId();
    }

    @Override
    public void update(Equipment equipment) {
        Assert.notNull(equipment, "equipment对象不能为空");
        equipment.initModifyInfo();
        equipmentMapper.updateByPrimaryKeySelective(equipment);
    }

    @Override
    public void delete(Set<Integer> ids) {
        Assert.notNull(ids, "ids对象不能为空");
        ids.forEach(equipmentMapper::deleteByPrimaryKey);
    }

    @Override
    public Equipment get(Integer id) {
        Assert.notNull(id, "id对象不能为空");
        return equipmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Equipment> getByBordId(Integer bordId) {
        List<Equipment> equipments = equipmentMapper.getByBordId(bordId);
        return Optional.ofNullable(equipments).orElse(Collections.emptyList());
    }

    @Override
    public DeviceStatusVo<?> queryStatus(Integer id) {
        Assert.notNull(id, "设备id不能为空");
        Equipment equipment = get(id);
        Collector pingCollector = deviceMonitorContext.getCollector(DeviceTypeEnum.OTHER_STATION.getTypeKey());
        if (Objects.nonNull(pingCollector)) {
            //首先ping一下ip是否连通
            DataBo<?> pingDataBo = pingCollector.collect(deviceConverter.convert(equipment));
            OtherDeviceStatusBo otherDeviceStatusBo = (OtherDeviceStatusBo) pingDataBo.getDataObj();
            //如果未连通或者设备本身是其他类型的设备，则不需要获取真实状态，直接返回
            if (otherDeviceStatusBo.getStatus().equals(YnEnum.NO.getCode())
                    || DeviceTypeEnum.OTHER_STATION.getTypeKey().equals(equipment.getEquipmentType())) {
                DeviceStatusVo<?> deviceStatusVo = deviceStatusVoConverter.convert(pingDataBo);
                deviceStatusVo.fillEquipment(equipment);
                //如果是连通状态，则使用绿色图标，否则使用红色图标
                if(otherDeviceStatusBo.getStatus().equals(YnEnum.YES.getCode())){
                    deviceStatusVo.setStatus(EquipmentStatusEnum.THREE.getCode());
                }else {
                    deviceStatusVo.setStatus(EquipmentStatusEnum.ONE.getCode());
                }
                return deviceStatusVo;
            } else {
                String deviceType = DeviceTypeEnum.getTypeKey(Objects.isNull(equipment) ? null : equipment.getEquipmentType());
                Collector collector = deviceMonitorContext.getCollector(deviceType);
                if (Objects.nonNull(collector)) {
                    DataBo<?> dataBo = collector.collect(deviceConverter.convert(equipment));
                    DeviceStatusVo<?> deviceStatusVo = deviceStatusVoConverter.convert(dataBo);
                    deviceStatusVo.fillEquipment(equipment);
                    deviceStatusVo.setStatus(EquipmentStatusEnum.TWO.getCode());
                    if(Objects.nonNull(deviceStatusVo.getStatusObj())){
                        Integer status = YnEnum.NO.getCode();
                        if(DeviceTypeEnum.FLASH_STATION.getType().equals(equipment.getEquipmentType())){
                            FlashStationStatusVo flashStationStatusVo = (FlashStationStatusVo) deviceStatusVo.getStatusObj();
                            status = flashStationStatusVo.getCellStatus();
                        }else if(DeviceTypeEnum.WEB_STATION.getType().equals(equipment.getEquipmentType())) {
                            WebStationStatusVo webStationStatusVo = (WebStationStatusVo) deviceStatusVo.getStatusObj();
                            status = webStationStatusVo.getCellStatus();
                        }
                        if(status.equals(YnEnum.YES.getCode())){
                            deviceStatusVo.setStatus(EquipmentStatusEnum.THREE.getCode());
                        }
                    }
                    return deviceStatusVo;
                }
            }
        }
        throw new IllegalArgumentException("设备类型错误id:" + id);
    }

    @Override
    public Page<DeviceStatusVo> searchDeviceStatus(EquipmentStatusSearch param) {
        Page<DeviceStatusVo> result = new Page<>();
        result.setCurrentPage(param.getCurrentPage());
        result.setPageSize(param.getPageSize());
        result.setCount(0);
        if (!Objects.isNull(param.getIp())) {
            Equipment equipment = equipmentMapper.getByIp(param.getIp());
            if (Objects.isNull(equipment)) {
                return result;
            }
            Integer equipmentType = equipment.getEquipmentType();
            Assert.notNull(equipmentType, "设备类型不能为空!");
            if (!equipmentType.equals(param.getEquipmentType())) {
                return result;
            }
        }
        DeviceStatusResultBo deviceStatusResultBo = deviceStatueHandler.search(param);
        Assert.notNull(deviceStatusResultBo, "设备类型不合法!");
        if (CollectionUtils.isEmpty(deviceStatusResultBo.getData())) {
            return result;
        }
        result.setData(deviceStatusResultBo.getData());
        result.setCount(deviceStatusResultBo.getCount());
        return result;
    }

    @Override
    public DeviceStatusData searchExportData(EquipmentStatusSearch param) {
        DeviceStatusData deviceStatusData = new DeviceStatusData();
        Page<DeviceStatusVo> pageData = searchDeviceStatus(param);
        List<DeviceStatusVo> deviceStatusVoList = pageData.getData();
        if (DeviceTypeEnum.OTHER_STATION.getType().equals(param.getEquipmentType())) {
            deviceStatusData.setClazz(OtherDeviceStatusExcel.class);
            deviceStatusData.setData(otherDeviceStatusExcelConverter.convertToList(deviceStatusVoList));
        } else if (DeviceTypeEnum.WEB_STATION.getType().equals(param.getEquipmentType())) {
            deviceStatusData.setClazz(WebStationStatusExcel.class);
            deviceStatusData.setData(webStationStatusExcelConverter.convertToList(deviceStatusVoList));
        } else if (DeviceTypeEnum.FLASH_STATION.getType().equals(param.getEquipmentType())) {
            deviceStatusData.setClazz(FlashStationStatusExcel.class);
            deviceStatusData.setData(flashStationStatusExcelConverter.convertToList(deviceStatusVoList));
        }
        return deviceStatusData;
    }
}
