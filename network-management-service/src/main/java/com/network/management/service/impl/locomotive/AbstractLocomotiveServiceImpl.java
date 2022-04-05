package com.network.management.service.impl.locomotive;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.network.management.domain.dao.BordInformation;
import com.network.management.domain.dao.Locomotive;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.search.LocomotiveSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.LocomotiveVo;
import com.network.management.mapper.BordInformationMapper;
import com.network.management.mapper.LocomotiveMapper;
import com.network.management.service.LocomotiveService;
import com.network.management.service.converter.LocomotiveConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 机车服务实现类
 *
 * @author yyc
 * @date 2020/10/14 22:50
 */
@Service
@Slf4j
public abstract class AbstractLocomotiveServiceImpl implements LocomotiveService {
    @Autowired
    private LocomotiveMapper locomotiveMapper;
    @Autowired
    private LocomotiveConverter locomotiveConverter;
    @Autowired
    private BordInformationMapper bordInformationMapper;
    /**
     * 前一次机车及状态信息
     */
    private static Map<String, LocomotiveVo> preLocomotiveVoMap = new HashMap<>();

    @Override
    public Map<String, List<LocomotiveVo>> queryLocomotiveStatus() {
        Map<String, List<LocomotiveVo>> locomotiveMap = new HashMap<>();
        List<BordInformation> bordInformations = this.bordInformationMapper.selectAll();
        if (CollectionUtils.isNotEmpty(bordInformations)) {
            BordInformation bordInformation = bordInformations.get(0);
            if (Objects.nonNull(bordInformation) && StringUtils.isNotEmpty(bordInformation.getCoreIp())) {
                List<Locomotive> locomotives = this.locomotiveMapper.queryAllLocomotives();
                Map<String, String> ueNodeMapping = queryLocomotiveLocations(bordInformation.getCoreIp(), locomotives);
                log.info("机车映射关系查询:{}", JSON.toJSONString(ueNodeMapping));
                List<LocomotiveVo> locomotiveVos = convertLocomotiveVo(locomotives, ueNodeMapping);
                List<LocomotiveVo> allLocomotiveVos = getAllLocomotiveVos(locomotiveVos);
                ListUtils.emptyIfNull(allLocomotiveVos)
                        .stream()
                        .filter(Objects::nonNull)
                        .forEach(locomotiveVo -> {
                            if (StringUtils.isNotEmpty(locomotiveVo.getENodeBIP()))
                                if (locomotiveMap.containsKey(locomotiveVo.getENodeBIP())) {
                                    locomotiveMap.get(locomotiveVo.getENodeBIP()).add(locomotiveVo);
                                } else {
                                    locomotiveMap.put(locomotiveVo.getENodeBIP(), Lists.newArrayList(locomotiveVo));
                                }
                        });
            }
        }
        return locomotiveMap;
    }

    /**
     * 对比前一次机车及状态，填充前一次的机车信息
     *
     * @param locomotiveVos {@link List<LocomotiveVo>}
     * @return {@link List<LocomotiveVo>}
     */
    private List<LocomotiveVo> getAllLocomotiveVos(List<LocomotiveVo> locomotiveVos) {
        List<LocomotiveVo> newLocomotiveVos = new ArrayList<>();
        Map<String, LocomotiveVo> newLocomotiveVoMap = new HashMap<>(preLocomotiveVoMap);
        ListUtils.emptyIfNull(locomotiveVos)
                .stream()
                .filter(Objects::nonNull)
                .forEach(locomotiveVo -> {
                    if(StringUtils.isNotEmpty(locomotiveVo.getUeIp()) && StringUtils.isNotEmpty(locomotiveVo.getENodeBIP())
                            && Objects.nonNull(locomotiveVo.getNum())){
                        preLocomotiveVoMap.put(locomotiveVo.getUeIp(), locomotiveVo);
                        newLocomotiveVos.add(locomotiveVo);
                        newLocomotiveVoMap.remove(locomotiveVo.getUeIp());
                    }
                });
        if (MapUtils.isNotEmpty(newLocomotiveVoMap)) {
            for (Map.Entry<String, LocomotiveVo> entry : newLocomotiveVoMap.entrySet()) {
                if (Objects.nonNull(entry) && StringUtils.isNotEmpty(entry.getKey())
                        && Objects.nonNull(entry.getValue())) {
                    LocomotiveVo locomotiveVo = entry.getValue();
                    locomotiveVo.setStatus(YnEnum.NO.getCode());
                    newLocomotiveVos.add(locomotiveVo);
                    preLocomotiveVoMap.put(entry.getKey(), locomotiveVo);
                }
            }
        }
        return newLocomotiveVos;
    }

    @Override
    public Integer saveLocomotive(LocomotiveVo locomotiveVo) {
        Locomotive locomotive = locomotiveConverter.convert(locomotiveVo);
        if (Objects.nonNull(locomotive)) {
            return locomotiveMapper.insert(locomotive);
        } else {
            log.error("新增机车失败:{}", JSON.toJSONString(locomotiveVo));
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        locomotiveMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateLocomotive(LocomotiveVo locomotiveVo) {
        Locomotive locomotive = locomotiveConverter.convert(locomotiveVo);
        if (Objects.nonNull(locomotive)) {
            locomotiveMapper.updateByPrimaryKey(locomotive);
        } else {
            log.error("修改机车失败:{}", JSON.toJSONString(locomotiveVo));
        }
    }

    @Override
    public List<LocomotiveVo> queryAllLocomotiveVos() {
        return locomotiveConverter.reverseConvertToList(locomotiveMapper.queryAllLocomotives());
    }

    @Override
    public Page<LocomotiveVo> search(LocomotiveSearch search) {
        Page<LocomotiveVo> locomotiveVoPage = new Page<LocomotiveVo>();
        if (Objects.isNull(search)) {
            search = new LocomotiveSearch();
        }
        if (Objects.isNull(search.getCurrentPage())) {
            search.setCurrentPage(1);
        }
        if (Objects.isNull(search.getPageSize())) {
            search.setPageSize(10);
        }
        locomotiveVoPage.setCurrentPage(search.getCurrentPage());
        locomotiveVoPage.setPageSize(search.getPageSize());
        Integer count = locomotiveMapper.count(search);
        locomotiveVoPage.setCount(count);
        if (Objects.nonNull(count) && count > 0) {
            locomotiveVoPage.setData(locomotiveConverter.reverseConvertToList(locomotiveMapper.getByConditions(search)));
        } else {
            locomotiveVoPage.setData(Lists.newArrayList());
        }
        return locomotiveVoPage;
    }

    /**
     * 获取机车数据及状态
     *
     * @param coreIp      核心网ip
     * @param locomotives {@link List<Locomotive>}
     * @return {@link List<LocomotiveVo>}
     */
//    private List<LocomotiveVo> getLocomotiveVos(String coreIp, List<Locomotive> locomotives) {
//        List<LocomotiveVo> locomotiveVos = new ArrayList<>();
//        if (StringUtils.isNotEmpty(coreIp) && CollectionUtils.isNotEmpty(locomotives)) {
//            try {
//                CountDownLatch countDownLatch = new CountDownLatch(locomotives.size());
//                locomotives.stream()
//                        .forEach(locomotive -> {
//                            ThreadPoolUtils.getLocomotiveExecutor().execute(() -> {
//                                try {
//                                    LocomotiveVo locomotiveVo = queryLocomotiveMap(coreIp, locomotive);
//                                    if (Objects.nonNull(locomotiveVo)) {
//                                        locomotiveVos.add(locomotiveVo);
//                                    }
//                                } catch (Exception e) {
//                                    log.error("获取机车数据失败:{}", JSON.toJSONString(locomotive), e);
//                                } finally {
//                                    countDownLatch.countDown();
//                                }
//                            });
//                        });
//                countDownLatch.await();
//            } catch (Exception e) {
//                log.error("获取所有机车状态错误", e);
//            }
//        }
//        return locomotiveVos;
//    }

    private List<LocomotiveVo> convertLocomotiveVo(List<Locomotive> locomotives, Map<String, String> ueNodeMapping) {
        List<LocomotiveVo> locomotiveVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(locomotives)){
            for(Locomotive locomotive : locomotives){
                if(ueNodeMapping.containsKey(locomotive.getUeIp())){
//                    boolean isReachable = isReachable(locomotive.getUeIp());
                    LocomotiveVo locomotiveVo = locomotiveConverter.reverseConvert(locomotive);
                    String ueNodeIp = ueNodeMapping.get(locomotive.getUeIp());
                    if (Objects.nonNull(locomotiveVo) && StringUtils.isNotBlank(ueNodeIp)) {
                        locomotiveVo.setStatus(YnEnum.YES.getCode());
                        locomotiveVo.setENodeBIP(ueNodeIp);
                        locomotiveVos.add(locomotiveVo);
                    }
                }
            }
        }
        return locomotiveVos;
    }


    /**
     * 查询所有机车位置信息
     * @param coreIp 核心网ip
     * @param locomotives 机车对象列表 {@link List<Locomotive>}
     * @return {@link Map<String, String>} key:设备ip value:基站ip
     */
    protected abstract Map<String, String> queryLocomotiveLocations(String coreIp, List<Locomotive> locomotives);
}
