package com.network.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.network.management.common.CommonUtils;
import com.network.management.common.exception.Assert;
import com.network.management.common.socket.SocketClient;
import com.network.management.domain.enums.PowerCmdEnum;
import com.network.management.domain.enums.PowerStatusEnum;
import com.network.management.domain.vo.PowerReqVo;
import com.network.management.domain.vo.PowerStatusVo;
import com.network.management.service.PowerStatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 电源控制/查询服务实现类
 * @author yyc
 * @date 2021/12/19 17:31
 */
@Service
@Slf4j
public class PowerStatusServiceImpl implements PowerStatusService {
    /**
     * 空格
     */
    private static final String BLANK_SPACE = " ";
    /**
     * 查询返回值
     */
    private static final int QUERY_RETURN_LENGTH = 13;
    /**
     * 默认电量
     */
    private static final int DEFAULT_POWER_NUMBER = 100;

    /**
     * 电源切换交流阈值
     */
    private static final int MIN_POWER_NUMBER = 20;

    @Override
    public PowerStatusVo queryPowerStatus(PowerReqVo powerReqVo) {
        checkPowerReqVo(powerReqVo);
        String result = SocketClient.send(powerReqVo.getIp(), powerReqVo.getPort(), PowerCmdEnum.getCmd(powerReqVo.getType()), PowerCmdEnum.isControlCmd(powerReqVo.getType()));
        log.info("查询电源状态入参:powerReqVo->{},result->{}", JSON.toJSONString(powerReqVo), result);
        PowerStatusVo powerStatusVo = getPowerStatusVo(powerReqVo, result);
        /**判断当前为直流且电源电量小于20%，则需要切换为交流*/
        exchangePower(powerReqVo, powerStatusVo);
        return powerStatusVo;
    }

    @Override
    public Boolean exchangePowerStatus(PowerReqVo powerReqVo) {
        checkPowerReqVo(powerReqVo);
        SocketClient.send(powerReqVo.getIp(), powerReqVo.getPort(), PowerCmdEnum.getCmd(powerReqVo.getType()), PowerCmdEnum.isControlCmd(powerReqVo.getType()));
        return true;
    }

    /**
     * 校验电源请求入参
     * @param powerReqVo 电源请求入参对象 {@link PowerReqVo}
     */
    private void checkPowerReqVo(PowerReqVo powerReqVo) {
        Assert.notNull(powerReqVo, "PowerReqVo对象不能为空");
        Assert.notNull(powerReqVo.getIp(), "电源ip不能为空");
        Assert.notNull(powerReqVo.getPort(), "电源端口不能为空");
        Assert.notNull(PowerCmdEnum.getCmd(powerReqVo.getType()), "操作命令不能为空");
    }

    /**
     * 电源查询请求返回值封装PowerStatusVo对象
     * @param result 电源查询请求返回值
     * @return 电源状态对象 {@link PowerStatusVo}
     */
    private PowerStatusVo getPowerStatusVo(PowerReqVo powerReqVo, String result){
        if(StringUtils.isEmpty(result)){
            log.warn("查询电源返回结果为空:powerReqVo->{}", JSON.toJSONString(powerReqVo));
            return null;
        }
        String[] hexStrings = result.split(BLANK_SPACE);
        if(hexStrings.length < QUERY_RETURN_LENGTH){
            log.warn("查询电源返回值位数小于13:powerReqVo->{},result->{}", JSON.toJSONString(powerReqVo), result);
            return null;
        }
        PowerStatusVo powerStatusVo = new PowerStatusVo();
        powerStatusVo.setStatus(PowerStatusEnum.AC.getStatus());
        powerStatusVo.setPowerNumber(DEFAULT_POWER_NUMBER);
        Integer statusHex = CommonUtils.hexString2Integer(hexStrings[9]);
        Integer powerNum = CommonUtils.hexString2Integer(hexStrings[10]);
        String powerStatus = CommonUtils.integer2BinaryString(statusHex);
        if(StringUtils.isNotEmpty(powerStatus) && powerStatus.endsWith(String.valueOf(PowerStatusEnum.DC.getStatus()))){
            powerStatusVo.setStatus(PowerStatusEnum.DC.getStatus());
        }
        if(Objects.nonNull(powerNum) && powerNum.compareTo(DEFAULT_POWER_NUMBER) < 0){
            powerStatusVo.setPowerNumber(powerNum);
        }
        return powerStatusVo;
    }

    /**
     * 判断当前为直流且电源电量小于20%，则需要切换为交流
     * @param powerReqVo 电源请求对象 {@link PowerReqVo}
     * @param powerStatusVo 电源查询返回对象 {@link PowerStatusVo}
     */
    private void exchangePower(PowerReqVo powerReqVo, PowerStatusVo powerStatusVo){
        if(Objects.isNull(powerStatusVo) || Objects.isNull(powerStatusVo.getStatus())
                || Objects.isNull(powerStatusVo.getPowerNumber())){
            log.warn("查询电源返回参数未空:{}", JSON.toJSONString(powerReqVo));
            return;
        }
        if(PowerStatusEnum.DC.getStatus().equals(powerStatusVo.getStatus()) && powerStatusVo.getPowerNumber().compareTo(MIN_POWER_NUMBER) < 0){
            powerReqVo.setType(PowerCmdEnum.CMD_AC.getType());
            exchangePowerStatus(powerReqVo);
        }
    }
}
