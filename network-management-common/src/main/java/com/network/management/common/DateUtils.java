package com.network.management.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
     * 将时间类型转换成字符串
     *
     * @param date 时间
     * @return 字符串
     */
    public static String formatDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
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
        // 获取本周的开始日期和结束日期
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
        LocalDate targetDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return targetDate.isEqual(startOfWeek)
                || targetDate.isEqual(endOfWeek)
                || (targetDate.isAfter(startOfWeek) && targetDate.isBefore(endOfWeek));
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

    /**
     * 是否属于当月上旬
     *
     * @param date 时间
     * @return true：是，false：不是
     */
    public static boolean isFirstThirdMonth(Date date) {
        // 将目标时间字符串转换为 LocalDate 对象
        LocalDate targetDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        // 获取当月的第一天和第十一天
        LocalDate firstDayOfMonth = targetDate.withDayOfMonth(1);
        LocalDate eleventhDayOfMonth = targetDate.withDayOfMonth(11);
        // 判断目标时间是否属于上旬、中旬或下旬
        return targetDate.isEqual(firstDayOfMonth) || (targetDate.isAfter(firstDayOfMonth) && targetDate.isBefore(eleventhDayOfMonth));
    }

    /**
     * 是否属于当月中旬
     *
     * @param date 时间
     * @return true：是，false：不是
     */
    public static boolean isMiddleThirdMonth(Date date) {
        // 将目标时间字符串转换为 LocalDate 对象
        LocalDate targetDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        // 获取当月的第十一天，第二十一天
        LocalDate eleventhDayOfMonth = targetDate.withDayOfMonth(11);
        LocalDate twentyFirstDayOfMonth = targetDate.withDayOfMonth(21);

        // 判断目标时间是否属于上旬、中旬或下旬
        return (targetDate.isEqual(eleventhDayOfMonth) || (targetDate.isAfter(eleventhDayOfMonth) && targetDate.isBefore(twentyFirstDayOfMonth)));
    }

    /**
     * 是否属于当月下旬
     *
     * @param date 时间
     * @return true：是，false：不是
     */
    public static boolean isLastThirdMonth(Date date) {
        // 将目标时间字符串转换为 LocalDate 对象
        LocalDate targetDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        // 第二十一天，最后一天
        LocalDate twentyFirstDayOfMonth = targetDate.withDayOfMonth(21);
        LocalDate lastDayOfMonth = targetDate.withDayOfMonth(targetDate.lengthOfMonth());

        // 判断目标时间是否属于上旬、中旬或下旬
        return targetDate.isEqual(twentyFirstDayOfMonth) || (targetDate.isAfter(twentyFirstDayOfMonth) && targetDate.isBefore(lastDayOfMonth));
    }
}
