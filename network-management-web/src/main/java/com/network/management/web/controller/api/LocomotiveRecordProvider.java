package com.network.management.web.controller.api;

import com.network.management.domain.vo.LocomotiveRecordVo;
import com.network.management.web.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@RestController
@RequestMapping("/api")
public class LocomotiveRecordProvider {

    @PostMapping("/locomotive/record/save")
    public Result saveLocomotiveRecord(@RequestBody LocomotiveRecordVo locomotiveRecordVo){
        return Result.success(null);
    }
}
