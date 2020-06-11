package cn.minsin.core.tools.date;

import cn.minsin.core.tools.ListUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * 农历工具类 可以通过日期获取农历、节气、节日
 *
 * @author mintonzhang
 * @date 2019年2月15日
 * @since 0.3.4
 */
public class LunarUtil {

    private final static long[] lunarInfo = {0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0,
            0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
            0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
            0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
            0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573,
            0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950,
            0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
            0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970,
            0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50,
            0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
            0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
            0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
            0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};
    private final static String[] nStr1 = {"正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月"};
    private final static String[] gan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private final static String[] zhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    private final static String[] animals = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    private final static String[] solarTerms = {"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
            "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
    private final static String[] holidays = {"0101*元旦", "0214情人节", "0308妇女节", "0312植树节", "0315消费者权益日", "0401愚人节",
            "0501劳动节", "0504青年节", "0512护士节", "0601儿童节", "0701建党节", "0801建军节", "0808父亲节", "0909毛泽东逝世纪念", "0910教师节",
            "0928孔子诞辰", "1001*国庆节", "1006老人节", "1024联合国日", "1112孙中山诞辰", "1220澳门回归", "1225圣诞节", "1226毛泽东诞辰"};
    private final static String[] nlHoldays = {"0101*农历春节", "0115元宵节", "0505端午节", "0707七夕情人节", "0815中秋节", "0909重阳节",
            "1208腊八节", "1224小年", "0100*除夕"};
    private final static long[] sTermInfo = {0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072,
            240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758};
    private static Calendar offDate = Calendar.getInstance();

    protected LunarUtil() {
        //Allow subClass
    }

    /**
     * 传回农历 y年的总天数
     *
     * @param y
     */
    final private static int lYearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(y));
    }

    /**
     * 传回农历 y年闰月的天数
     *
     * @param y
     */
    final private static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    /**
     * 传回农历 y年闰哪个月 1-12 , 没闰传回 0
     *
     * @param y
     */
    final private static int leapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 0xf);
    }

    /**
     * 传回农历 y年m月的总天数
     *
     * @param y
     * @param m
     */
    final private static int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }

    /**
     * 传回农历 y年的生肖
     *
     * @param y
     */
    final private static String AnimalsYear(int y) {
        return animals[(y - 4) % 12];
    }

    /**
     * 传入 月日的offset 传回干支,0=甲子
     *
     * @param num
     */
    final private static String cyclicalm(int num) {
        return (gan[num % 10] + zhi[num % 12]);
    }

    /**
     * 传入 offset 传回干支, 0=甲子
     *
     * @param y
     */
    final private static String cyclical(int y) {
        int num = y - 1900 + 36;
        return (cyclicalm(num));
    }

    /**
     * 传出y年m月d日对应的农历.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
     *
     * @param y
     * @param m
     * @param d
     */
    final private static int[] calElement(int y, int m, int d) {
        int[] nongDate = new int[7];
        int i = 0, temp = 0, leap = 0;
        Date baseDate = new GregorianCalendar(0 + 1900, 0, 31).getTime();
        Date objDate = new GregorianCalendar(y, m - 1, d).getTime();
        int offset = (int) ((objDate.getTime() - baseDate.getTime()) / 86400000L);
        nongDate[5] = offset + 40;
        nongDate[4] = 14;
        for (i = 1900; i < 2050 && offset > 0; i++) {
            temp = lYearDays(i);
            offset -= temp;
            nongDate[4] += 12;
        }
        if (offset < 0) {
            offset += temp;
            i--;
            nongDate[4] -= 12;
        }
        nongDate[0] = i;
        nongDate[3] = i - 1864;
        leap = leapMonth(i); // 闰哪个月
        nongDate[6] = 0;
        for (i = 1; i < 13 && offset > 0; i++) {
            // 闰月
            if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
                --i;
                nongDate[6] = 1;
                temp = leapDays((int) nongDate[0]);
            } else {
                temp = monthDays((int) nongDate[0], i);
            }
            // 解除闰月
            if (nongDate[6] == 1 && i == (leap + 1))
                nongDate[6] = 0;
            offset -= temp;
            if (nongDate[6] == 0)
                nongDate[4]++;
        }
        if (offset == 0 && leap > 0 && i == leap + 1) {
            if (nongDate[6] == 1) {
                nongDate[6] = 0;
            } else {
                nongDate[6] = 1;
                --i;
                --nongDate[4];
            }
        }
        if (offset < 0) {
            offset += temp;
            --i;
            --nongDate[4];
        }
        nongDate[1] = i;
        nongDate[2] = offset + 1;
        return nongDate;
    }

    final private static String getChinaDate(int day) {
        String a = "";
        if (day == 10)
            return "初十";
        if (day == 20)
            return "二十";
        if (day == 30)
            return "三十";
        int two = (int) ((day) / 10);
        if (two == 0)
            a = "初";
        if (two == 1)
            a = "十";
        if (two == 2)
            a = "廿";
        if (two == 3)
            a = "三";
        int one = (int) (day % 10);
        switch (one) {
            case 1:
                a += "一";
                break;
            case 2:
                a += "二";
                break;
            case 3:
                a += "三";
                break;
            case 4:
                a += "四";
                break;
            case 5:
                a += "五";
                break;
            case 6:
                a += "六";
                break;
            case 7:
                a += "七";
                break;
            case 8:
                a += "八";
                break;
            case 9:
                a += "九";
                break;
        }
        return a;
    }

    /**
     * 计算节气
     *
     * @param y 年
     * @param m 月
     * @param d 日
     */
    final private static String getSolarTerms(Date date) {
        offDate.setTime(date);
        int y = offDate.get(Calendar.YEAR);
        int m = offDate.get(Calendar.MONTH) + 1;
        int d = offDate.get(Calendar.DATE);
        if (d == sTerm(y, (m - 1) * 2)) {
            return solarTerms[(m - 1) * 2];
        } else if (d == sTerm(y, (m - 1) * 2 + 1)) {
            return solarTerms[(m - 1) * 2 + 1];
        }
        return "";
    }

    /**
     * 某年的第n个节气为几日(从0小寒起算)
     *
     * @param y
     * @param n
     */
    final private static int sTerm(int y, int n) {

        offDate.set(1900, 0, 6, 2, 5, 0);
        long temp = offDate.getTime().getTime();
        offDate.setTime(new Date((long) ((31556925974.7 * (y - 1900) + sTermInfo[n] * 60000L) + temp)));

        return offDate.get(Calendar.DAY_OF_MONTH);
    }

    private static List<String> getHoliday(Date date, int[] l) {
        String date2String = DateUtil.date2String(date, () -> "MMdd");
        List<String> list = ListUtil.newArrayList();
        for (String string : holidays) {
            if (string.contains(date2String)) {
                list.add(string.replace(date2String, ""));
            }
        }
        for (String string : nlHoldays) {
            date2String = String.format("%02d", l[1]) + String.format("%02d", l[2]);
            if (string.contains(date2String)) {
                list.add(string.replace(date2String, ""));
            }
        }
        return list;
    }

    /**
     * 获取农历信息
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    public static LunarModel getLunarDate(int year, int month, int day) {
        Calendar today = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
        today.set(year, month - 1, day);
        int[] l = calElement(year, month, day);
        Date time = today.getTime();
        LunarModel model = new LunarModel();

        model.setLunarYearAnimal(AnimalsYear(year));
        model.setDate(time);
        model.setHoliday(getHoliday(time, l));
        model.setSolarStr(getSolarTerms(time));
        model.setLunarYear(cyclical(year));
        model.setLunarMonth(nStr1[(int) l[1]]);
        model.setLunarDate(getChinaDate((int) (l[2])));
        model.setWeek(DateUtil.date2String(time, () -> "E"));
        return model;
    }

    /**
     * 获取农历信息
     *
     * @param date 日期
     */
    public static LunarModel getLunarDate(Date date) {
        offDate.setTime(date);
        int y = offDate.get(Calendar.YEAR);
        int m = offDate.get(Calendar.MONTH) + 1;
        int d = offDate.get(Calendar.DATE);
        return getLunarDate(y, m, d);
    }

    public static class LunarModel {

        /**
         * 时间
         */
        private Date date;

        /**
         * 星期
         */
        private String week;

        /**
         * 农历年
         */
        private String lunarYear;

        /**
         * 农历月
         */
        private String lunarMonth;

        /**
         * 农历日期
         */
        private String lunarDate;

        /**
         * 节气
         */
        private String solarStr;

        /**
         * 节日
         */
        private List<String> holiday;

        /**
         * 农历年代表的动物
         */
        private String lunarYearAnimal;

        public String getLunarMonth() {
            return lunarMonth;
        }

        public void setLunarMonth(String lunarMonth) {
            this.lunarMonth = lunarMonth;
        }

        public String getLunarDate() {
            return lunarDate;
        }

        public void setLunarDate(String lunarDate) {
            this.lunarDate = lunarDate;
        }

        public String getLunarYear() {
            return lunarYear;
        }

        public void setLunarYear(String lunarYear) {
            this.lunarYear = lunarYear;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getSolarStr() {
            return solarStr;
        }

        public void setSolarStr(String solarStr) {
            this.solarStr = solarStr;
        }

        public List<String> getHoliday() {
            return holiday;
        }

        public void setHoliday(List<String> holiday) {
            this.holiday = holiday;
        }

        public String getLunarYearAnimal() {
            return lunarYearAnimal;
        }

        public void setLunarYearAnimal(String lunarYearAnimal) {
            this.lunarYearAnimal = lunarYearAnimal;
        }

        @Override
        public String toString() {
            return "[date=" + date + ", week=" + week + ", lunarYear=" + lunarYear + ", lunarMonth=" + lunarMonth
                    + ", lunarDate=" + lunarDate + ", solarStr=" + solarStr + ", holiday=" + holiday + ", animal="
                    + lunarYearAnimal + "]";
        }

    }
}
