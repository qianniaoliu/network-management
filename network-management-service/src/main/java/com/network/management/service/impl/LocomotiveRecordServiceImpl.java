package com.network.management.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.network.management.domain.dao.LocomotiveRecord;
import com.network.management.domain.vo.LocomotiveRecordVo;
import com.network.management.mapper.LocomotiveRecordMapper;
import com.network.management.service.LocomotiveRecordService;
import com.network.management.service.converter.LocomotiveRecordConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Service
@Slf4j
public class LocomotiveRecordServiceImpl implements LocomotiveRecordService {

    @Autowired
    private LocomotiveRecordConverter locomotiveRecordConverter;

    @Autowired
    private LocomotiveRecordMapper locomotiveRecordMapper;

    @Override
    public boolean save(LocomotiveRecordVo recordRequest) {
        log.info("保存机车记录请求对象:{}", JSONObject.toJSONString(recordRequest));
        LocomotiveRecord locomotiveRecord = locomotiveRecordConverter.convert(recordRequest);
        Integer id = locomotiveRecordMapper.insert(locomotiveRecord);
        return Objects.nonNull(id);
    }

    @Override
    public List<LocomotiveRecordVo> queryLocomotiveRecords(Integer currentPage, Integer pageSize) {
        Integer currentIndex = (currentPage - 1) * pageSize;
        List<LocomotiveRecord> locomotiveRecords = locomotiveRecordMapper.queryLocomotiveRecords(currentIndex, pageSize);
        return locomotiveRecordConverter.reverseConvertToList(locomotiveRecords);
    }
}
