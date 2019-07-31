
package com.hq.hlibrary.utils;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @classname: 日期处理类
 */
@SuppressLint({"SimpleDateFormat", "UseValueOf"})
@SuppressWarnings("static-access")
public final class DateUtil {

    public static boolean isDataFormat(String textStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(textStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isDataFormat(String str, String pattern) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01     
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 得到当前日期格式为：2009-08-01 12:00:00
     */
    public static String getCreateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 得到当前日期格式为：2009-08-01 12:00:00
     */
    public static String getCreateTimeHM() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date());
    }

    /**
     * 得到当前日期的月首 格式为：2009-08-01
     */
    public static String monthFist() {
        Calendar localTime = Calendar.getInstance();
        String strY = null;// 日期属性：日
        int x = localTime.get(Calendar.YEAR); // 日期属性：年
        int y = localTime.get(Calendar.MONTH) + 1; // 日期属性：月
        strY = y >= 10 ? String.valueOf(y) : ("0" + y); // 组合月份
        return x + "-" + strY + "-01"; // 最后组合成yyyy-mm-dd形式字符串
    }

    /**
     * 得到上个月月首 格式为：2009-08-01
     */
    public static String beforeMonth() {
        Calendar localTime = Calendar.getInstance();
        localTime.add(Calendar.MONTH, -1); // 通过提取这个月计算上个月号
        String strz = null;
        int x = localTime.get(Calendar.YEAR); // 得到年
        int y = localTime.get(Calendar.MONTH) + 1; // 得到月
        strz = y >= 10 ? String.valueOf(y) : ("0" + y);
        return x + "-" + strz + "-01";
    }

    /**
     * 得到当前日期的前day天
     *
     * @param day
     * @return 格式2008-08-13
     */
    public static String beforeYearMonthDayByNum(int day) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(curDate());// 初始当天日期
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);// 设置日历时间
        c.add(Calendar.DAY_OF_MONTH, -day);
        String strDate = sdf.format(c.getTime());// 得到你想要得前n个月的月份
        return strDate;
    }

    /**
     * 得到当前日期的前month个月
     *
     * @param month
     * @return 格式2008-08
     */
    public static String beforeMonthByNum(int month) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = null;
        try {
            date = sdf.parse(getCurrentMonth());// 初始月份
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);// 设置日历时间
        c.add(Calendar.MONTH, -month);// 在日历的月份上增加6个月
        String strDate = sdf.format(c.getTime());// 得到你想要得前n个月的月份
        return strDate;
    }

    /**
     * 得到当前日期的前day天
     *
     * @param day
     * @return 格式08-04
     */
    public static String beforeDayByNum(int day) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date date = null;
        try {
            date = sdf.parse(curMonthDay());// 初始日期
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);// 设置日历时间
        c.add(Calendar.DAY_OF_MONTH, -day);
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    /**
     * 得到当前日期的前day天
     *
     * @param day
     * @return 格式08-04
     */
    public static String beforeDayByNumYMD(int day) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(curDate());// 初始日期
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);// 设置日历时间
        c.add(Calendar.DAY_OF_MONTH, -day);
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    /**
     * 得到当前日期 格式为：2009-08-01
     */
    public static String curDate() {
        // 分别根据日历时间提取当前年月日组合成字符串
        Calendar localTime = Calendar.getInstance();
        int x = localTime.get(Calendar.YEAR);
        int y = localTime.get(Calendar.MONTH) + 1;
        int z = localTime.get(Calendar.DAY_OF_MONTH);
        return x + "-" + y + "-" + z;
    }

    /**
     * 得到当前月份 格式为：08-01
     */
    public static String curMonthDay() {
        // 分别根据日历时间提取当前年月日组合成字符串
        Calendar localTime = Calendar.getInstance();
        int y = localTime.get(Calendar.MONTH) + 1;
        int z = localTime.get(Calendar.DAY_OF_MONTH);
        return y + "-" + z;
    }

    /**
     * 得到当前天 格式为 01
     */
    public static String curMonth() {
        // 分别根据日历时间提取当前年月日组合成字符串
        Calendar localTime = Calendar.getInstance();
        int y = localTime.get(Calendar.MONTH) + 1;
        return y + "";
    }

    /**
     * 得到当前年 格式为 01
     */
    public static String curYear() {
        // 分别根据日历时间提取当前年月日组合成字符串
        Calendar localTime = Calendar.getInstance();
        int y = localTime.get(Calendar.YEAR);
        return y + "";
    }

    /**
     * 得到当前年 格式为 01
     */
    public static String nextYear() {
        // 分别根据日历时间提取当前年月日组合成字符串
        Calendar localTime = Calendar.getInstance();
        int y = localTime.get(Calendar.YEAR) + 1;
        return y + "";
    }

    public static String lastYear() {
        // 分别根据日历时间提取当前年月日组合成字符串
        Calendar localTime = Calendar.getInstance();
        int y = localTime.get(Calendar.YEAR) - 1;
        return y + "";
    }

    /**
     * 得到当前天 格式为 08
     */
    public static String curDay() {
        // 分别根据日历时间提取当前年月日组合成字符串
        Calendar localTime = Calendar.getInstance();
        int z = localTime.get(Calendar.DAY_OF_MONTH);
        return z + "";
    }

    /**
     * 根据日期获取当前星期
     */
    public static String getWeek(String pTime) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "星期天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "星期一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "星期二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "星期三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "星期四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "星期五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "星期六";
        }
        return Week;
    }

    /**
     * 根据日期获取当前星期
     */
    public static String getWeek(long ms) {
        String pTime = date2String(ms, "yyyy-MM-dd");
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "周日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "周六";
        }
        return Week;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param day
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(int day) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date date = null;
        try {
            date = sdf.parse(curMonthDay());// 初始日期
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);// 设置日历时间
        c.add(Calendar.DAY_OF_MONTH, -day);

        try {
            date = sdf.parse(sdf.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] weekDays = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param day
     * @return 当前日期是星期几
     */
    public static String getWeekOfDateEnglish(int day) {
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date date = null;
        try {
            date = sdf.parse(curMonthDay());// 初始日期
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);// 设置日历时间
        c.add(Calendar.DAY_OF_MONTH, -day);

        try {
            date = sdf.parse(sdf.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] weekDays = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取当前小时
     */
    public static int curHour() {
        int hour = 0;
        if (getCreateTime().substring(11, 13).startsWith("0")) {
            hour = Integer.parseInt(getCreateTime().substring(12, 13));
        } else {
            hour = Integer.parseInt(getCreateTime().substring(11, 13));
        }
        return hour;
    }

    /**
     * 给定的日期加一个月 格式为：2009-08-01
     */
    public static String addMonth(String strdate) {
        Date date = new Date(); // 构造一个日期型中间变量
        String dateresult = null; // 返回的日期字符串
        // 创建格式化格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 加减日期所用
        GregorianCalendar gc = new GregorianCalendar();
        try {
            date = df.parse(strdate); // 将字符串格式化为日期型
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gc.setTime(date); // 得到gc格式的时间
        gc.add(Calendar.MONTH, 1); // 2表示月的加减，年代表1依次类推(周,天。。)
        // 把运算完的时间从新赋进对象
        gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
        // 在格式化回字符串时间
        dateresult = df.format(gc.getTime());
        return dateresult;
    }

    /**
     * 给定的日期减一个月 格式为：2009-08-01
     */
    public static String subMonth(String strdate) {

        Date date = new Date(); // 构造一个日期型中间变量

        String dateresult = null; // 返回的日期字符串
        // 创建格式化格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 加减日期所用
        GregorianCalendar gc = new GregorianCalendar();
        try {
            date = df.parse(strdate); // 将字符串格式化为日期型
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gc.setTime(date); // 得到gc格式的时间
        gc.add(Calendar.MONTH, -1); // 2表示月的加减，年代表1依次类推(周,天。。)
        // 把运算完的时间从新赋进对象
        gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
        // 在格式化回字符串时间
        dateresult = df.format(gc.getTime());
        return dateresult;
    }

    /**
     * 给定的日期减一天 格式为：2009-08-01
     */
    public static String subDay(String strdate) {
        Date date = new Date(); // 构造一个日期型中间变量
        String dateresult = null; // 返回的日期字符串
        // 创建格式化格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 加减日期所用
        GregorianCalendar gc = new GregorianCalendar();
        try {
            date = df.parse(strdate); // 将字符串格式化为日期型
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gc.setTime(date); // 得到gc格式的时间
        gc.add(5, -1); // 2表示月的加减，年代表1依次类推(３周....5天。。)
        // 把运算完的时间从新赋进对象
        gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
        // 在格式化回字符串时间
        dateresult = df.format(gc.getTime());
        return dateresult;
    }

    /**
     * 给定的日期加一天 格式为：2009-08-01
     */
    public static String addDay(String strdate) {
        Date date = new Date(); // 构造一个日期型中间变量
        String dateresult = null; // 返回的日期字符串
        // 创建格式化格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 加减日期所用
        GregorianCalendar gc = new GregorianCalendar();
        try {
            date = df.parse(strdate); // 将字符串格式化为日期型
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gc.setTime(date); // 得到gc格式的时间
        gc.add(5, 1); // 2表示月的加减，年代表1依次类推(３周....5天。。)
        // 把运算完的时间从新赋进对象
        gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
        // 在格式化回字符串时间
        dateresult = df.format(gc.getTime());
        return dateresult;
    }

    /**
     * 拆分给定字符串构造本月月初 格式为：2009-08-01
     */
    public static String giveMonthFist(String strdate) {
        // 以“－”为分隔符拆分字符串
        String strArray[] = strdate.split("-");

        String tempyear = strArray[0]; // 得到字符串中的年

        String tempmonth = strArray[1]; // 得到字符串中的月
        // 拼接成月首字符串
        return tempyear + "-" + tempmonth + "-01";
    }

    /**
     * 拆分给定字符串构造本月月末 格式为：2009-08-01
     */
    public static String giveMonthLast(String strdate) {
        // 先得到下个月的同一天
        String addmonth = DateUtil.addMonth(strdate);
        // 得到下个月的月初
        String monthfirst = DateUtil.giveMonthFist(addmonth);
        // 下个月月初减一天为本月月末
        String subday = DateUtil.subDay(monthfirst);
        return subday;
    }

    /**
     * 拆分给定字符串构造上个月月初 格式为：2009-08-01
     */
    public static String giveBeforeMonthFirst(String strdate) {
        // 调用得到上个月的函数
        String beforemonth = DateUtil.subMonth(strdate);
        // 调用构造月初的函数
        return DateUtil.giveMonthFist(beforemonth);
    }

    /**
     * 拆分给定字符串构造上个月月末 格式为：2009-08-01
     */
    public static String giveBeforeMonthLast(String strdate) {
        // 先调用函数得到本月月初
        String monthfirst = DateUtil.giveMonthFist(strdate);
        // 调用当前日期减一天方法得到上个月月末
        return DateUtil.subDay(monthfirst);
    }

    /**
     * 给定的日期得到年月 格式为：2009-08-01
     */
    public static String giveyrmo(String yrmoday) {
        // 以“－”为分隔符拆分字符串
        String strArray[] = yrmoday.split("-");
        String tempyear = strArray[0]; // 得到字符串中的年
        String tempmonth = strArray[1]; // 得到字符串中的月
        // 拼接成月首字符串
        return tempyear + "-" + tempmonth; // 最后组合成yyyy-mm形式字符串
    }

    /**
     * 两个日期做减法，返回相差天数
     *
     * @throws ParseException
     * @throws ParseException
     */
    public static long datesub(Date date1, Date date2) throws ParseException {
        @SuppressWarnings("unused")
        long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime()
                - date2.getTime() : date2.getTime() - date1.getTime();
        // 日期相减得到相差的日期
        long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1
                .getTime() - date2.getTime()) / (24 * 60 * 60 * 1000)
                : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        return day + 1;
    }

    /**
     * 今天与过去的日子相差几天
     */
    public static long datesub(String dateTwo) throws ParseException {
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = sdf.parse(curDate());// 初始当天日期
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            date2 = sdf.parse(dateTwo);// 初始当天日期
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 日期相减得到相差的日期
        long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1
                .getTime() - date2.getTime()) / (24 * 60 * 60 * 1000)
                : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        return day + 1;
    }

    /**
     * 根据给定的年月构造日期月首字符串
     */
    public static String giveMonthFist(Integer yr, Integer mo) {
        // 拼接成月首字符串
        if (mo >= 10) {
            return yr + "-" + mo + "-01";
        } else {
            return yr + "-" + "0" + mo + "-01";
        }
    }

    /**
     * 根据给定的年月构造年月字符串
     */
    public static String giveYrMo(Integer yr, Integer mo) {
        // 拼接成月首字符串
        if (mo >= 10) {
            return yr + "-" + mo;
        } else {
            return yr + "-" + "0" + mo;
        }
    }

    /**
     * 给定年月字串返回一个整型月份 格式为：2009-08-01
     */
    public static Integer retrunmo(String yrmoday) {
        // 以“－”为分隔符拆分字符串
        String strArray[] = yrmoday.split("-");
        String tempmonth = strArray[1]; // 得到字符串中的月
        return new Integer(tempmonth);
    }

    /**
     * 给定年月字串返回一个整型年份 格式为：2009-08-01
     */
    public static Integer retrunyr(String yrmoday) {
        // 以“－”为分隔符拆分字符串
        String strArray[] = yrmoday.split("-");
        String tempmonth = strArray[0]; // 得到字符串中的月
        return new Integer(tempmonth);
    }

    /**
     * 给定的两个日期作比较，返回bool的类型 格式为：2009-08-01
     *
     * @throws ParseException
     */
    public static boolean boolcompara(String startdate, String enddate) {
        Date start = null;
        Date end = null;
        try {
            start = DateFormat.getDateInstance().parse(startdate);
            end = DateFormat.getDateInstance().parse(enddate);
            LogUtils.e("boolcompara---", start.toString());
            LogUtils.e("boolcompara***", end.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (start == null || end == null)
            return false;
        if (start.compareTo(end) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @todo:判断时间date1是否在时间date2之前 时间格式 2008-08-08 16:16:34
     */
    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            return d1.before(d2);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }

    /**
     * 两个yyyyMMddHHmmss格式时间比较谁更前
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isLongDateBefore(String date1, String date2) {
        try {
            SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat newFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            Date da1 = oldFormat.parse(date1);
            Date da2 = oldFormat.parse(date2);
            Date d1 = newFormat.parse(newFormat.format(da1));
            Date d2 = newFormat.parse(newFormat.format(da2));
            return d1.before(d2);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @todo:判断时间date1是否在时间date2之前 时间格式 2008-08-08 16:16:34
     */
    public static boolean isDateBefore(long date1, long date2) {

        try {
            Date d1 = new Date();
            d1.setTime(date1);
            Date d2 = new Date();
            d2.setTime(date2);
            return d1.before(d2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @todo:判断时间date1是否在时间date2之前 时间格式 2008-08-08
     */
    public static boolean isDateBeforeYMD(String date1, String date2) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            return d1.before(d2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断当前时间是否在时间date2之前
     *
     * @param date2 时间格式
     *              2005-4-21 16:16:34
     * @return
     */
    public static boolean isDateBefore(String date2) {
        try {
            Date date1 = new Date();
            DateFormat df = DateFormat.getDateTimeInstance();
            return date1.before(df.parse(date2));
        } catch (ParseException e) {
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }

    /**
     * 判端date1是否在date2之前；当date1的时间早于date2是返回true date1，date2的格式为：2009-08-01
     */
    public static boolean isDate10Before(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 当前时间增加间隔小时后的时间 时间格式 2008-08-08 16:16:34
     */
    public static String addHours(String startDate, int intHour) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            Date date = df.parse(startDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            long longMills = cal.getTimeInMillis() + intHour * 60 * 60 * 1000;
            cal.setTimeInMillis(longMills);

            // 返回日期
            return df.format(cal.getTime());
        } catch (Exception Exp) {
            return null;
        }
    }

    /**
     * 当前时间减去间隔小时后的时间 时间格式 2008-08-08 16:16:34
     */
    public static String delHours(String startDate, int intHour) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            Date date = df.parse(startDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            long longMills = cal.getTimeInMillis() - intHour * 60 * 60 * 1000;
            cal.setTimeInMillis(longMills);

            // 返回日期
            return df.format(cal.getTime());
        } catch (Exception Exp) {
            return null;
        }
    }

    /**
     * 得到当前日期 日期格式 2008-08-08
     */
    @SuppressWarnings("unused")
    public static String getCurrentDate() {
        try {
            long longCalendar = 0;
            // 获得当前日期
            Calendar cldCurrent = Calendar.getInstance();
            // 获得年月日
            String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
            String strMonth = String
                    .valueOf(cldCurrent.get(Calendar.MONTH) + 1);
            String strDate = String.valueOf(cldCurrent.get(Calendar.DATE));
            // 整理格式
            if (strMonth.length() < 2) {
                strMonth = "0" + strMonth;
            }
            if (strDate.length() < 2) {
                strDate = "0" + strDate;
            }
            // 组合结果
            longCalendar = Long.parseLong(strYear + strMonth + strDate);
            // 系统默认月份加一
            longCalendar += 100L;
            // 创建上初始化上下文环境并返回
            return String.valueOf(strYear + "-" + strMonth + "-" + strDate);
        } catch (Exception Exp) {
            return "2008-08-08";
        }
    }

    /**
     * @TODO：将给定的日期加一个月 参数类型“2009－03”
     */
    public static String getThisMonthLast(String strdate) {
        String thisStrDate = strdate + "-01";
        // 先得到下个月的同一天
        String addmonth = DateUtil.addMonth(thisStrDate);
        // 得到下个月的月初
        String monthfirst = DateUtil.giveMonthFist(addmonth);
        // 下个月月初减一天为本月月末
        String subday = DateUtil.subDay(monthfirst);
        return subday;
    }

    /**
     * 获得系统中使用长整形表示的日期 返回参数：long:表示的日期的8位长整形值 如：20090723
     */
    public static long getLongCalendar() {
        try {
            long longCalendar = 0;

            // 获得当前日期
            Calendar cldCurrent = Calendar.getInstance();

            // 获得年月日
            String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
            String strMonth = String.valueOf(cldCurrent.get(Calendar.MONTH));
            String strDate = String.valueOf(cldCurrent.get(Calendar.DATE));

            // 整理格式
            if (strMonth.length() < 2) {
                strMonth = "0" + strMonth;
            }
            if (strDate.length() < 2) {
                strDate = "0" + strDate;
            }
            // 组合结果
            longCalendar = Long.parseLong(strYear + strMonth + strDate);
            // 系统默认月份加一
            longCalendar += 100L;
            // 创建上初始化上下文环境并返回
            return longCalendar;
        } catch (Exception Exp) {
            return 0;
        }
    }

    /**
     * 返回字符串行日期
     *
     * @param canlendar 20090801或20090802080808
     * @return 2009-08-01或2009-08-01 08:08:08
     */
    public static String toString(String canlendar) {
        try {
            StringBuffer sbCalendar = new StringBuffer();
            sbCalendar.insert(0, canlendar);
            // 整理格式
            if (sbCalendar.length() == 8) {
                sbCalendar.insert(6, "-");
                sbCalendar.insert(4, "-");
            } else if (sbCalendar.length() == 14) {
                sbCalendar.insert(12, ":");
                sbCalendar.insert(10, ":");
                sbCalendar.insert(8, " ");
                sbCalendar.insert(6, "-");
                sbCalendar.insert(4, "-");
            } else {
                // 错误处理
                return null;
            }
            // 返回格式化好的整形日期的字符串格式
            return sbCalendar.toString();
        } catch (Exception Exp) {
            // 错误处理
            return null;
        }
    }

    /**
     * 返回字符串行日期
     *
     * @param canlendar 20090801或20090802080808
     * @return 2009/08/01或2009/08/01 08:08:08
     */
    public static String toString(long canlendar) {
        try {
            StringBuffer sbCalendar = new StringBuffer();
            sbCalendar.insert(0, canlendar);
            // 整理格式
            if (sbCalendar.length() == 8) {
                sbCalendar.insert(6, "-");
                sbCalendar.insert(4, "-");
            } else if (sbCalendar.length() == 14) {
                sbCalendar.insert(12, ":");
                sbCalendar.insert(10, ":");
                sbCalendar.insert(8, " ");
                sbCalendar.insert(6, "-");
                sbCalendar.insert(4, "-");
            } else {
                // 错误处理
                return null;
            }
            // 返回格式化好的整形日期的字符串格式
            return sbCalendar.toString();
        } catch (Exception Exp) {
            // 错误处理
            return null;
        }
    }

    /**
     * 长整形时间类组合Calender，封装Calender的数字接口和方法 返回 20090802080808 包含日期和时间
     */
    public static long getLongTime() {
        try {
            long longCalendar = 0;
            // 获得当前日期
            Calendar cldCurrent = Calendar.getInstance();
            // 获得年月日
            String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
            String strMonth = String
                    .valueOf(cldCurrent.get(Calendar.MONTH) + 1);
            String strDate = String.valueOf(cldCurrent.get(Calendar.DATE));
            String strHour = String.valueOf(cldCurrent.get(Calendar.HOUR));
            String strAM_PM = String.valueOf(cldCurrent.get(Calendar.AM_PM));
            String strMinute = String.valueOf(cldCurrent.get(Calendar.MINUTE));
            String strSecond = String.valueOf(cldCurrent.get(Calendar.SECOND));

            // 把时间转换为24小时制
            // strAM_PM=="1",表示当前时间是下午，所以strHour需要加12
            if ("1".equals(strAM_PM)) {
                strHour = String.valueOf(Long.parseLong(strHour) + 12);
            }
            // 整理格式
            if (strMonth.length() < 2) {
                strMonth = "0" + strMonth;
            }
            if (strDate.length() < 2) {
                strDate = "0" + strDate;
            }
            if (strHour.length() < 2) {
                strHour = "0" + strHour;
            }
            if (strMinute.length() < 2) {
                strMinute = "0" + strMinute;
            }
            if (strSecond.length() < 2) {
                strSecond = "0" + strSecond;
            }
            // 组合结果
            longCalendar = Long.parseLong(strYear + strMonth + strDate
                    + strHour + strMinute + strSecond);

            // 创建上初始化上下文环境并返回
            return longCalendar;
        } catch (Exception Exp) {
            return 0;
        }
    }

    /**
     * 由长整型时间变为字符 通过长整数变为时间格式,可以自动适应8位或16位 输入："20090808"或"20090808080808"
     * 返回："2009年08月08日"或"2009年08月08日 08:08:08"
     */
    public static String getDateStringByLongDatetime(long longCalendar) {
        try {
            String StrCalendar = String.valueOf(longCalendar);
            String StrCalendarResult = "";
            // 判断为日期型
            if (StrCalendar.length() == 8) {
                StrCalendarResult = StrCalendar.substring(0, 4) + "年"
                        + StrCalendar.substring(4, 6) + "月"
                        + StrCalendar.substring(6, 8) + "日";
                return StrCalendarResult;
            }
            // 判断为日期及时间型
            if (StrCalendar.length() == 14) {
                StrCalendarResult = StrCalendar.substring(0, 4) + "年"
                        + StrCalendar.substring(4, 6) + "月"
                        + StrCalendar.substring(6, 8) + "日";
                StrCalendarResult = StrCalendarResult + " "
                        + StrCalendar.substring(8, 10) + ":"
                        + StrCalendar.substring(10, 12) + ":"
                        + StrCalendar.substring(12, 14);
                return StrCalendarResult;
            }
            // 否则返回空字符
            return "";
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 由长整型时间变为字符 通过长整数变为时间格式,可以自动适应8位或16位 输入："20090808"或"20090808080808"
     * 返回："2009/08/08"或"2009/08/08 08:08:08"
     */
    public static String getDateStringByLongDatetimeForPage(long longCalendar) {
        try {
            String StrCalendar = String.valueOf(longCalendar);
            String StrCalendarResult = "";
            // 判断为日期型
            if (StrCalendar.length() == 8) {
                StrCalendarResult = StrCalendar.substring(0, 4) + "/"
                        + StrCalendar.substring(4, 6) + "/"
                        + StrCalendar.substring(6, 8);
                return StrCalendarResult;
            }
            // 判断为日期及时间型
            if (StrCalendar.length() == 14) {
                StrCalendarResult = StrCalendar.substring(0, 4) + "/"
                        + StrCalendar.substring(4, 6) + "/"
                        + StrCalendar.substring(6, 8);
                StrCalendarResult = StrCalendarResult + " "
                        + StrCalendar.substring(8, 10) + ":"
                        + StrCalendar.substring(10, 12);
                return StrCalendarResult;
            }
            // 否则返回空字符
            return "";
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 得到系统当前时间 返回参数：String:系统当前时间 格式：yyyy/mm/dd
     */
    public static String getCurrentDateTime() {
        return getDateStringByLongDatetimeForPage(getLongCalendar());
    }

    /**
     * 得到系统当前日期显示， 返回格式：yyyy/mm/dd
     */
    public static String getCurrentDateView() {
        // 获得当前日期
        Calendar cldCurrent = Calendar.getInstance();
        // 获得年月日
        String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
        String strMonth = String.valueOf(cldCurrent.get(Calendar.MONTH) + 1);
        String strDate = String.valueOf(cldCurrent.get(Calendar.DATE));
        // 整理格式
        if (strMonth.length() < 2) {
            strMonth = "0" + strMonth;
        }
        if (strDate.length() < 2) {
            strDate = "0" + strDate;
        }
        // 得出当天日期的字符串
        String StrCurrentCalendar = strYear + "/" + strMonth + "/" + strDate;
        return StrCurrentCalendar;
    }

    /**
     * 得到系统当前月份， 返回格式：yyyymm
     */
    public static String getCurrentMonth() {
        // 获得当前日期
        Calendar cldCurrent = Calendar.getInstance();
        // 获得年月日
        String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
        String strMonth = String.valueOf(cldCurrent.get(Calendar.MONTH) + 1);
        // 整理格式
        if (strMonth.length() < 2) {
            strMonth = "0" + strMonth;
        }
        return strYear + strMonth;
    }

    /**
     * 得到给定时间显示，格式：yyyy/mm/dd 参数格式："20090808"或"20090808080808"
     */
    public static String getCurrentDateView(long longCalendar) {
        if (longCalendar == 0) {
            return "";
        }
        String strDateView = String.valueOf(longCalendar);
        // 获得年月日
        String strYear = strDateView.substring(0, 4);
        String strMonth = strDateView.substring(4, 6);
        String strDate = strDateView.substring(6, 8);
        // 整理格式
        if (strMonth.length() < 2) {
            strMonth = "0" + strMonth;
        }
        if (strDate.length() < 2) {
            strDate = "0" + strDate;
        }
        // 得出当天日期的字符串
        String StrCurrentCalendar = strYear + "/" + strMonth + "/" + strDate;
        return StrCurrentCalendar;
    }

    /**
     * 由长整型时间变为字符 通过长整数变为时间格式,输入参数为6位的时间 如"123143" 返回格式：12点31分43秒
     */
    public static String getTimeStringByLongTime(long longCalendar) {
        try {
            String StrCalendar = String.valueOf(longCalendar);
            String StrCalendarResult = "";
            // 判断为时间型
            if (StrCalendar.length() == 6) {
                StrCalendarResult = StrCalendar.substring(0, 2) + "点"
                        + StrCalendar.substring(2, 4) + "分"
                        + StrCalendar.substring(4, 6) + "秒";
                return StrCalendarResult;
            }
            // 否则返回空字符
            return "";
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 由长整型时间变为字符 通过长整数变为时间格式,输入参数为6位的时间 如"123143" 返回格式：12:31:43
     */
    public static String getTimeStringByLongTimeForPage(long longCalendar) {
        try {
            String StrCalendar = String.valueOf(longCalendar);
            String StrCalendarResult = "";

            // 判断为时间型
            if (StrCalendar.length() == 6) {
                StrCalendarResult = StrCalendar.substring(0, 2) + ":"
                        + StrCalendar.substring(2, 4) + ":"
                        + StrCalendar.substring(4, 6);
                return StrCalendarResult;
            }
            // 否则返回空字符
            return "";
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 给指定的Calendar，返回常用的8位时间
     */
    public static long getLongCalendar(Calendar yourCalendar) {
        try {
            long longCalendar = 0;
            // 获得年月日
            String strYear = String.valueOf(yourCalendar.get(Calendar.YEAR));
            String strMonth = String.valueOf(yourCalendar.get(Calendar.MONTH));
            String strDate = String.valueOf(yourCalendar.get(Calendar.DATE));
            // 整理格式
            if (strMonth.length() < 2) {
                strMonth = "0" + strMonth;
            }
            if (strDate.length() < 2) {
                strDate = "0" + strDate;
            }
            // 组合结果
            longCalendar = Long.parseLong(strYear + strMonth + strDate);
            // 系统默认月份加一
            longCalendar += 100L;
            // 创建上初始化上下文环境并返回
            return longCalendar;
        } catch (Exception Exp) {
            return 0;
        }
    }

    /**
     * 给指定的Calendar，返回常用的１４位时间
     */
    public static long getLongTime(Calendar yourCalendar) {
        try {
            long longCalendar = 0;
            // 获得年月日
            String strYear = String.valueOf(yourCalendar.get(Calendar.YEAR));
            String strMonth = String
                    .valueOf(yourCalendar.get(Calendar.MONTH) + 1);
            String strDate = String.valueOf(yourCalendar.get(Calendar.DATE));
            String strHour = String.valueOf(yourCalendar.get(Calendar.HOUR));
            String strAM_PM = String.valueOf(yourCalendar.get(Calendar.AM_PM));
            String strMinute = String
                    .valueOf(yourCalendar.get(Calendar.MINUTE));
            String strSecond = String
                    .valueOf(yourCalendar.get(Calendar.SECOND));

            // 把时间转换为24小时制
            // strAM_PM=="1",表示当前时间是下午，所以strHour需要加12
            if ("1".equals(strAM_PM)) {
                strHour = String.valueOf(Long.parseLong(strHour) + 12);
            }
            // 整理格式
            if (strMonth.length() < 2) {
                strMonth = "0" + strMonth;
            }
            if (strDate.length() < 2) {
                strDate = "0" + strDate;
            }
            if (strHour.length() < 2) {
                strHour = "0" + strHour;
            }
            if (strMinute.length() < 2) {
                strMinute = "0" + strMinute;
            }
            if (strSecond.length() < 2) {
                strSecond = "0" + strSecond;
            }
            // 组合结果
            longCalendar = Long.parseLong(strYear + strMonth + strDate
                    + strHour + strMinute + strSecond);
            // 创建上初始化上下文环境并返回
            return longCalendar;
        } catch (Exception Exp) {
            return 0;
        }
    }

    /**
     * 将长整型日期转换为日历,自适应８位或１４位
     */
    public static Calendar getCalendar(long longCalendar) {
        long longNF = 0;
        long longYF = 0;
        long longRZ = 0;
        long longXS = 0;
        long longFZ = 0;
        long longM = 0;
        GregorianCalendar gc = null;
        // 判断是８位还是１４位
        if (String.valueOf(longCalendar).length() < 14) {
            longNF = Long.parseLong(String.valueOf(longCalendar)
                    .substring(0, 4));
            longYF = Long.parseLong(String.valueOf(longCalendar)
                    .substring(4, 6)) - 1;
            longRZ = Long.parseLong(String.valueOf(longCalendar).substring(6));
            gc = new GregorianCalendar((int) longNF, (int) longYF, (int) longRZ);
        } else {
            longNF = Long.parseLong(String.valueOf(longCalendar)
                    .substring(0, 4));
            longYF = Long.parseLong(String.valueOf(longCalendar)
                    .substring(4, 6)) - 1;
            longRZ = Long.parseLong(String.valueOf(longCalendar)
                    .substring(6, 8));
            longXS = Long.parseLong(String.valueOf(longCalendar).substring(8,
                    10));
            longFZ = Long.parseLong(String.valueOf(longCalendar).substring(10,
                    12));
            longM = Long.parseLong(String.valueOf(longCalendar).substring(12));

            gc = new GregorianCalendar((int) longNF, (int) longYF,
                    (int) longRZ, (int) longXS, (int) longFZ, (int) longM);
        }
        return gc;
    }

    /**
     * 当前时间增加间隔分钟后的时间
     */
    public static long addMinutes(long longCalendar, int intMin) {
        try {
            //
            long longDate = 0;
            Calendar cal = getCalendar(longCalendar);
            long longMills = cal.getTimeInMillis() + intMin * 60 * 1000;
            cal.setTimeInMillis(longMills);
            if (String.valueOf(longCalendar).length() < 14) {
                longDate = getLongCalendar(cal);
            } else {
                longDate = getLongTime(cal);
            }
            // 返回日期
            return longDate;
        } catch (Exception Exp) {
            return -1;
        }
    }

    /**
     * 当前时间增加间隔小时后的时间
     */
    public static long addHours(long longCalendar, int intHour) {
        try {
            //
            long longDate = 0;
            Calendar cal = getCalendar(longCalendar);
            long longMills = cal.getTimeInMillis() + intHour * 60 * 60 * 1000;
            cal.setTimeInMillis(longMills);

            if (String.valueOf(longCalendar).length() < 14) {
                longDate = getLongCalendar(cal);
            } else {
                longDate = getLongTime(cal);
            }
            // 返回日期
            return longDate;
        } catch (Exception Exp) {
            return -1;
        }
    }

    /**
     * 当前时间增加间隔天后的时间
     */
    public static long addDays(long longCalendar, int intDay) {
        try {
            //
            long longDate = 0;
            Calendar cal = getCalendar(longCalendar);
            long longMills = cal.getTimeInMillis() + intDay * 24 * 60 * 60
                    * 1000;
            cal.setTimeInMillis(longMills);
            if (String.valueOf(longCalendar).length() < 14) {
                longDate = getLongCalendar(cal);
            } else {
                longDate = getLongTime(cal);
            }

            // 返回日期
            return longDate;
        } catch (Exception Exp) {
            return -1;
        }
    }

    /**
     * 当前时间增加间隔星期后的时间
     */
    public static long addWeeks(long longCalendar, int intWeek) {
        try {
            long longDate = 0;
            Calendar cal = getCalendar(longCalendar);
            long longMills = cal.getTimeInMillis() + intWeek * 7 * 24 * 60 * 60
                    * 1000;
            cal.setTimeInMillis(longMills);
            if (String.valueOf(longCalendar).length() < 14) {
                longDate = getLongCalendar(cal);
            } else {
                longDate = getLongTime(cal);
            }
            // 返回日期
            return longDate;
        } catch (Exception Exp) {
            return -1;
        }
    }

    /**
     * 当前日期增加间隔月份后的时间
     */
    public static long addMonths(long longCalendar, int intMonth) {
        try {
            long longNF = 0;
            long longYF = 0;
            long longRZ = 0;
            long longDate = 0;
            long longNIAN = 0;
            String strYF = "";
            String strRZ = "";
            longNF = Long.parseLong(String.valueOf(longCalendar)
                    .substring(0, 4));
            longYF = Long.parseLong(String.valueOf(longCalendar)
                    .substring(4, 6));
            longRZ = Long.parseLong(String.valueOf(longCalendar)
                    .substring(6, 8));
            longYF = longYF + intMonth;

            if (longYF > 12) {
                longNIAN = (long) Math.floor(longYF / 12);
                longYF = longYF % 12;
                if (longYF == 0) {
                    longYF = 12;
                }
                longNF = longNF + longNIAN;
            }
            // 处理特殊日
            if (longRZ >= 28) {
                longRZ = getNormalDay(longNF, longYF, longRZ);
            }
            if (longYF < 10) {
                strYF = "0" + String.valueOf(longYF);
            } else {
                strYF = String.valueOf(longYF);
            }
            if (longRZ < 10) {
                strRZ = "0" + String.valueOf(longRZ);
            } else {
                strRZ = String.valueOf(longRZ);
            }

            // 判断是８位还是１４位
            if (String.valueOf(longCalendar).length() < 14) {
                longDate = Long.parseLong(String.valueOf(longNF) + strYF
                        + strRZ);
            } else {
                longDate = Long
                        .parseLong(String.valueOf(longNF) + strYF + strRZ
                                + String.valueOf(longCalendar).substring(8, 14));
            }
            // 返回日期
            return longDate;
        } catch (Exception Exp) {
            return -1;
        }
    }

    /**
     * 返回正常日－处理３０／３１／２８ 输入参数：long calendar 当前时间, int intWeek 间隔星期 返回值：
     * long:处理后的时间
     */
    public static long getNormalDay(long longNF, long longYF, long longRZ) {
        try {
            // 只有日为２８／２９／３０／３１才运行此函数
            // 处理２月份
            if (longYF == 2) {
                if ((longNF % 4) == 0) {
                    if (longRZ > 28) {
                        longRZ = 29;
                    }
                } else {
                    longRZ = 28;
                }
            }
            if (longRZ == 31) {
                if (longYF == 4 || longYF == 6 || longYF == 9 || longYF == 11) {
                    longRZ = 30;
                }
            }
            return longRZ;

        } catch (Exception Exp) {
            return -1;
        }
    }

    /**
     * 获得系统中使用长整形表示的日期加星期
     */
    public static String getStringCalendarAndWeek() {
        try {
            String strCalendar = "";

            // 获得当前日期
            Calendar cldCurrent = Calendar.getInstance();

            // 获得年月日
            String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
            String strMonth = String
                    .valueOf(cldCurrent.get(Calendar.MONTH) + 1);
            String strDate = String.valueOf(cldCurrent.get(Calendar.DATE));
            // 整理格式
            if (strMonth.length() < 2) {
                strMonth = "0" + strMonth;
            }
            if (strDate.length() < 2) {
                strDate = "0" + strDate;
            }
            // 组合结果
            strCalendar = strYear + "年" + strMonth + "月" + strDate + "日";
            int intWeek = cldCurrent.get(Calendar.DAY_OF_WEEK);
            String strWeek = "";
            switch (intWeek) {
                case 1:
                    strWeek = "星期日";
                    break;
                case 2:
                    strWeek = "星期一";
                    break;
                case 3:
                    strWeek = "星期二";
                    break;
                case 4:
                    strWeek = "星期三";
                    break;
                case 5:
                    strWeek = "星期四";
                    break;
                case 6:
                    strWeek = "星期五";
                    break;
                case 7:
                    strWeek = "星期六";
                    break;
            }
            strCalendar = strCalendar + " " + strWeek + " ";
            // 创建上初始化上下文环境并返回
            return strCalendar;
        } catch (Exception Exp) {
            return "";
        }
    }

    /**
     * 根据输入的日期得到特定格式的日期加星期几
     *
     * @param strDate 2013-10-23 08:00:00
     * @return 10/23 星期四
     */
    public static String getCalendarAndWeek(String strDate) {
        try {
            String strCalendar = "";
            Calendar c = Calendar.getInstance();// 获得一个日历的实例
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = sdf2.parse(strDate);
            c.setTime(date);// 设置日历时间

            int intWeek = c.get(Calendar.DAY_OF_WEEK);
            String strWeek = "";
            switch (intWeek) {
                case 1:
                    strWeek = "星期日";
                    break;
                case 2:
                    strWeek = "星期一";
                    break;
                case 3:
                    strWeek = "星期二";
                    break;
                case 4:
                    strWeek = "星期三";
                    break;
                case 5:
                    strWeek = "星期四";
                    break;
                case 6:
                    strWeek = "星期五";
                    break;
                case 7:
                    strWeek = "星期六";
                    break;
            }
            strCalendar = sdf.format(c.getTime()) + " " + strWeek;

            return strCalendar;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 字符串转HTML格式字符串
     *
     * @param text text
     * @return str
     */
    public static String Text2HtmlToPageContent(String text) {
        // 若原字符串为空，则返回空对象
        if (text == null) {
            return "";
        }
        // 保存原字符串，方法结束时此字符串被转换成HTML格式的字符串
        String strSource = text;
        // 转换字符串的临时空间
        StringBuffer sbTarget = new StringBuffer();
        // 纯文本中要转换的字符或字符串
        char[] charArraySource = {'<', '>', '&', '"', '\n'};
        // 纯文本中要转换的字符或字符串对应的HTML表达方式
        String[] strArrayTarget = {"&lt;", "&gt;", "&amp;", "&quot;", "<br>"};

        // 记录处理过特殊字符的位置
        int intStart = 0;

        // 依次检查每一个源字符串的字符或字符串
        for (int i = 0; i < strSource.length(); i++) {
            // 字符串中包含要转换的字符或字符串，则进行相应转换
            for (int j = 0; j < charArraySource.length; j++) {
                // 当前检查的字符是要转换的特殊字符，则进行处理
                if (strSource.charAt(i) == charArraySource[j]) {
                    sbTarget.append(strSource.substring(intStart, i));
                    sbTarget.append(strArrayTarget[j]);
                    intStart = i + 1;
                    continue;
                }
            }
        }
        // 将所有处理位置之后的字符串放入目标字符串中
        sbTarget.append(strSource.substring(intStart));

        // 转换完成，返回转换结果
        return sbTarget.toString();
    }

    /**
     * 给定Date返回字符串,格式:2008-08-01
     *
     * @param date date
     * @return str
     */
    public static String dateToString(Date date) {
        String dateresult = null; // 返回的日期字符串
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date); // 得到gc格式的时间
        gc.set(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DATE));
        dateresult = df.format(gc.getTime());
        return dateresult;
    }

    public static String getDateStringByLongDate(long longCalendar) {
        try {
            String StrCalendar = String.valueOf(longCalendar);
            String StrCalendarResult = "";
            StrCalendarResult = StrCalendar.substring(0, 4) + "年"
                    + StrCalendar.substring(4, 6) + "月"
                    + StrCalendar.substring(6, 8) + "日";
            return StrCalendarResult;
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return "";
        }
    }

    public static String getJi(String strYue) {
        int intYue = Integer.decode(strYue).intValue();
        if (intYue >= 1 && intYue <= 3) {
            return "1";
        } else if (intYue >= 4 && intYue <= 6) {
            return "2";
        } else if (intYue >= 7 && intYue <= 9) {
            return "3";
        } else {
            return "4";
        }
    }

    /*
     * 输入格式为yyyy-MM-dd格式的字符串返回格式为 MM-dd的日期字符串
     */
    public static String getFormateMonthDay(String formateDateString) {
        String dateString = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date s_date = (Date) sdf.parse(formateDateString);
            sdf = new SimpleDateFormat("MM-dd");
            dateString = sdf.format(s_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    /*
     * 输入格式为yyyy-MM-dd HH:mm:ss格式的字符串返回格式为 MM-dd的日期字符串
     */
    public static String getFormateDateString(String formateDateString) {
        String dateString = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date s_date = (Date) sdf.parse(formateDateString);
            sdf = new SimpleDateFormat("MM-dd");
            dateString = sdf.format(s_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    /*
     * 输入格式为yyyy-MM-dd HH:mm:ss格式的字符串返回格式为 HH:mm的时间字符串(24小时格式）
     */
    public static String getFormateTimeString(String formateDateString) {
        String dateString = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date s_date = (Date) sdf.parse(formateDateString);
            sdf = new SimpleDateFormat("HH:mm");
            dateString = sdf.format(s_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    /*
     * 输入格式为yyyy-MM-dd HH:mm:ss格式的字符串返回格式为 HH:mm的时间字符串(24小时格式）
     */
    public static String getFormateDateStringWithYear(String formateDateString) {
        String dateString = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date s_date = (Date) sdf.parse(formateDateString);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateString = sdf.format(s_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String getFormateDate(long time) {
        String dateString = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date s_date = new Date(time);
            dateString = sdf.format(s_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String getFormateYear(long time) {
        String dateString = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date s_date = new Date(time);
            dateString = sdf.format(s_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String getFormateMonth(long time) {
        String dateString = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date s_date = new Date(time);
            dateString = sdf.format(s_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String getFormateDay(long time) {
        String dateString = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date s_date = new Date(time);
            dateString = sdf.format(s_date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    /**
     * Date（long） 转换 String
     *
     * @param time
     * @param format
     * @return
     */
    public static String date2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(time);
        return s;
    }

    /**
     * long 去除 时分秒
     * 时分秒全部为0
     *
     * @param date long
     * @return long
     */
    public static long getYearMonthDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 将毫秒转化成固定格式的时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond 毫秒
     * @return str
     */
    public static String getDateTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond * 1000);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
