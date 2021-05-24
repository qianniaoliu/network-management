package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.ShareMessage;
import com.network.management.domain.vo.ShareMessageVo;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yusheng
 */
@Component
public class ShareMessageVoConverter implements Converter<ShareMessage, ShareMessageVo> {
    @Override
    public ShareMessageVo convert(ShareMessage shareMessage) {
        return Optional.ofNullable(shareMessage)
                .map(item -> {
                    ShareMessageVo result = new ShareMessageVo();
                    result.setId(item.getId());
                    result.setCaller(item.getCaller());
                    result.setContent(item.getContent());
                    result.setFileSize(item.getFileSize());
                    result.setFilePath(item.getFilePath());
                    result.setVideoPath(item.getVideoPath());
                    result.setFileName(item.getFileName());
                    result.setCalleeName(item.getCalleeName());
                    result.setCallerName(item.getCallerName());
                    result.setTimeName(item.getTimeName());
                    result.setCallee(item.getCallee());
                    result.setSendStatus(item.getSendStatus());
                    return result;
                })
                .orElse(null);
    }

    @Override
    public ShareMessage reverseConvert(ShareMessageVo s) {
        return Optional.ofNullable(s)
                .map(item -> {
                    ShareMessage result = new ShareMessage();
                    result.setId(item.getId());
                    result.setCaller(item.getCaller());
                    result.setContent(item.getContent());
                    result.setFileSize(item.getFileSize());
                    result.setFilePath(item.getFilePath());
                    result.setVideoPath(item.getVideoPath());
                    result.setFileName(item.getFileName());
                    result.setCalleeName(item.getCalleeName());
                    result.setCallerName(item.getCallerName());
                    result.setTimeName(item.getTimeName());
                    result.setCallee(item.getCallee());
                    result.setSendStatus(item.getSendStatus());
                    return result;
                })
                .orElse(null);
    }
}
