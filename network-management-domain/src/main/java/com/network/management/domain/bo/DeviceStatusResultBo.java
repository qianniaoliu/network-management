package com.network.management.domain.bo;

import com.network.management.domain.vo.DeviceStatusVo;
import lombok.Data;

import java.util.List;

/**
 * @author yusheng
 */
@Data
public class DeviceStatusResultBo {

    private List<DeviceStatusVo> data;

    private Integer count;
}
