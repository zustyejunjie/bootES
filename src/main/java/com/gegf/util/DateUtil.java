package com.gegf.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: yuanhao
 * @since: 2018/9/13 09:46
 */
@Slf4j
public class DateUtil {

    public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd";
    public static final String COMMON_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private DateUtil(){}

    public static String afterDayDateFormat(int after,String format){
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE,after);
        return DateFormatUtils.format(day.getTimeInMillis(),format);
    }

    /**
     * 时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * @throws ParseException
     */
    public static long getDaySub(String beginDateStr, String endDateStr) throws ParseException {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        Date beginDate;
        Date endDate;

        beginDate = format.parse(beginDateStr);
        endDate = format.parse(endDateStr);
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);

        return day;
    }

    /**
     * 获取指定月份的上一月日期
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getPreMonth(String month) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        return sdf.format(DateUtils.addMonths(sdf.parse(month), -1));
    }

    /**
     * 获取指定年份的上一年日期
     * @param year
     * @return
     * @throws ParseException
     */
    public static String getPreYear(String year) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        return sdf.format(DateUtils.addYears(sdf.parse(year), -1));
    }

    /**
     * 获取制定年份的上一天日期
     * @param day
     * @return
     * @throws ParseException
     */
    public static String getPreDay(String day)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        return sdf.format(DateUtils.addDays(sdf.parse(day),-1));
    }

    /**
     * 获取时间段内的星期
     * @return
     */
    public static Map<Integer,List<String>> getWeekFromDuringTime(String start,String end) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(DateFormatUtils.ISO_DATE_FORMAT.getPattern());
        Date startDate = format.parse(start);
        Date endDate = format.parse(end);
        Calendar cBegin = new GregorianCalendar();
        Calendar cEnd = new GregorianCalendar();
        cBegin.setTime(startDate);
        cEnd.setTime(endDate);
        int count = 1;
        cEnd.add(Calendar.DAY_OF_YEAR, 1);
        Map<Integer,List<String>> map = Maps.newHashMap();
        while(cBegin.before(cEnd)){
            if(map.containsKey(count)){
                map.get(count).add(new java.sql.Date(cBegin.getTime().getTime()).toString());
            }else{
                List<String> list = Lists.newArrayList();
                list.add(new java.sql.Date(cBegin.getTime().getTime()).toString());
                map.put(count,list);
            }
            if(cBegin.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                count++;
            }
            cBegin.add(Calendar.DAY_OF_YEAR, 1);
        }
        return map;
    }


    /**
     * 根据日期查询是当年的第几周
     * @param dateStr
     * @return
     */
    public static int getWeekFromDate(String dateStr) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(DateFormatUtils.ISO_DATE_FORMAT.getPattern());

        Date date = format.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);

    }

}
