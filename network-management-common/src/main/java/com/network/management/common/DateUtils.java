package com.network.management.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * 时间工具
 *
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
public class DateUtils {

    /**
     * 将时间字符串转换成时间对象
     *
     * @param timeString 时间字符串
     * @return {@link Date} 时间对象
     */
    public static Date parseDateString(String timeString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(timeString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断目标时间是否是当天
     *
     * @param date 时间
     * @return true：是，false：不是
     */
    public static boolean isToday(Date date) {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return today.equals(targetDate);
    }

    /**
     * 判断目标时间是否是当周
     *
     * @param date 时间
     * @return true：是，false：不是
     */
    public static boolean isCurrentWeek(Date date) {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return targetDate.isAfter(today.minusDays(today.getDayOfWeek().getValue() - 1)) &&
                targetDate.isBefore(today.plusDays(7 - today.getDayOfWeek().getValue()));
    }

    /**
     * 判断目标时间是否是当月
     *
     * @param date 时间
     * @return true：是，false：不是
     */
    public static boolean isCurrentMonth(Date date) {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return targetDate.getYear() == today.getYear() && targetDate.getMonth() == today.getMonth();
    }
}
