package com.network.management.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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

    /**
     * 16进制字符串转换整型
     * @param hex 16进制字符串
     * @return
     */
    public static Integer hexString2Integer(String hex) {
        if(StringUtils.isNotEmpty(hex)){
            try{
                return Integer.valueOf(hex.trim(), 16);
            }catch (Exception e){
                log.warn("16进制字符串转换整型失败:{}", hex);
            }
        }
        return null;
    }

    /**
     * 十进制数转换为二进制
     * @param num
     * @return
     */
    public static String integer2BinaryString(Integer num){
        if(Objects.nonNull(num)){
            try{
                return Integer.toBinaryString(num);
            }catch (Exception e){
                log.error("integer to BinaryString error:{}", num);
            }
        }
        return null;
    }
}
