package com.network.management.web.controller.api;

import com.network.management.domain.vo.LocomotiveRecordVo;
import com.network.management.service.LocomotiveRecordService;
import com.network.management.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@RestController
@RequestMapping("/openapi")
public class LocomotiveRecordProvider {

    @Autowired
    private LocomotiveRecordService locomotiveRecordService;

    @PostMapping("/locomotive/record/save")
    public Result saveLocomotiveRecord(@RequestBody LocomotiveRecordVo locomotiveRecordVo){
        locomotiveRecordVo.checkParam();
        locomotiveRecordService.save(locomotiveRecordVo);
        return Result.success(null);
    }
}
