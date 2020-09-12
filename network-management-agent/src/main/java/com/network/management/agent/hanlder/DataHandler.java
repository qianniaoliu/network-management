package com.network.management.agent.hanlder;

import com.network.management.bo.DataBo;

/**
 * 数据处理接口
 * @author yyc
 * @date 2020/9/12 21:23
 */
public interface DataHandler {
    /**
     * 状态数据处理接口
     * @param dataBo {@link DataBo}
     */
    public void handle(DataBo<?> dataBo);
}
