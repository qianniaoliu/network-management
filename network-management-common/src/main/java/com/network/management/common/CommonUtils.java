package com.network.management.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用工具类
 * @author yyc
 * @date 2020/9/15 17:18
 */
@Slf4j
public class CommonUtils {
    /**
     * 字符串转换为整数型
     * @param str
     * @return
     */
    public static Integer String2Integer(String str){
        if(StringUtils.isNotEmpty(str)){
            try{
                return Integer.parseInt(str);
            }catch (Exception e){
                log.error("String to Integer error:{}", str, e);
            }
        }
        return null;
    }

    /**
     * 获取图片存储路径
     * @return 默认返回System.getProperty('use.dir')
     */
    public static String getImgDirPath(){
        String imgDirPath = System.getProperty("bord.img.dir");
        if(StringUtils.isBlank(imgDirPath)){
            imgDirPath = System.getProperty("user.dir");
        }
        return imgDirPath;
    }
}
