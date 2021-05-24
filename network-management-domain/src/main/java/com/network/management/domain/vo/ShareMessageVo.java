package com.network.management.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 共享信息对象
 *
 * @author yusheng
 */
@Data
public class ShareMessageVo {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 调用方
     */
    private String caller;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 视频路径
     */
    private String videoPath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 调用名称
     */
    private String calleeName;
    /**
     * 调用方名称
     */
    private String callerName;
    /**
     * 时间
     */
    private Date timeName;
    /**
     * 调用
     */
    private String callee;
    /**
     * 发送状态
     */
    private Integer sendStatus;
}
