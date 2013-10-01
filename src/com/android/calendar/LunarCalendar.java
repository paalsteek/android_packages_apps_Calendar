
package com.android.calendar;

import android.text.format.Time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 我的日历物件<br>
 * 这是一个由西历(java 来说即是GregorianCalendar)扩展出来物件<br>
 * 用户透过读取属性来得知相关资讯<br>
 * 参考自Sean Lin (林洵贤)先生的农历月历与世界时间DHTML程式(AD1900至AD2100)<br>
 * http://sean.o4u.com/2008/04/dhtml.html
 * 
 * @author Roy Tsang
 */
public class LunarCalendar extends GregorianCalendar
{
    static final long serialVersionUID = 555834590345353L;
    /**
     * 当天农历年份(用数字来表示)
     */
    public int lunarYear;
    /**
     * 当天农历月份(用数字来表示)
     */
    public int lunarMonth;
    /**
     * 当天农历日子(用数字来表示)
     */
    public int lunarDay;
    /**
     * 该年的闰月是那一个月
     */
    public int lunarLeapMonth = 0;

    private int lunarInfo[] = {
            0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0, 0x55d2,
            0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd295, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977,
            0x497f, 0xa4b0, 0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970,
            0x6566, 0xd4a0, 0xea50, 0x6a95, 0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f,
            0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2, 0xa950, 0xb557,
            0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0,
            0xaea6, 0xab50, 0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0,
            0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4, 0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6,
            0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46, 0xab60, 0x9570,
            0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58, 0x5ac0, 0xab60, 0x96d5, 0x92e0,
            0xc960, 0xd954, 0xd4a0, 0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5,
            0xa950, 0xb4a0, 0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930,
            0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260, 0xea65, 0xd530,
            0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520, 0xdd45,
            0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0,
            0x4b63, 0x937f, 0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef,
            0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55, 0x56a0, 0xa6d0, 0x55d4,
            0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50, 0x55a0, 0xaba4, 0xa5b0, 0x52b0,
            0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260,
            0xe968, 0xd520, 0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252,
            0xd520
    };
    /**
     * 本月是闰月吗?
     */
    public boolean isLunarLeapMonth = false;
    /**
     * 当天是假期吗?
     */
    public boolean isHoliday = false;
    /**
     * 当天年柱
     */
    public String chineseYearName = new String();
    /**
     * 当天月柱
     */
    public String chineseMonthName = new String();
    /**
     * 当天日柱
     */
    public String chineseDayName = new String();
    /**
     * 当天时柱
     */
    public String chineseHourName = new String();
    /**
     * 节气讯息
     */
    public String solarTermInfo = new String();
    /**
     * 该天农历节日讯息
     */
    public String lunarFestival = new String();
    /**
     * 该年生肖
     */
    public String animalOfYear = new String();
    /**
     * 当天农历日子(用四柱来表示)
     */
    public String chineseDateString = new String();
    private int solarYear, solarMonth, solarDay;
    private String nStr1[] = {
            "日", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"
    };
    private String nStr2[] = {
            "初", "十", "廿", "卅", "卌"
    };
    private String Zhi[] = {
            "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"
    };

    // ===========================================================================================================================
    // Constructor
    public LunarCalendar()
    {
        super();
        init();
    }

    public LunarCalendar(int year, int month, int date)
    {
        super();
        super.clear();
        super.set(Calendar.YEAR, year);
        super.set(Calendar.MONTH, month);
        super.set(Calendar.DAY_OF_MONTH, date);
        init();
    }

    public LunarCalendar(int year, int month, int date, int hour, int minute)
    {
        super();
        super.clear();
        super.set(Calendar.YEAR, year);
        super.set(Calendar.MONTH, month);
        super.set(Calendar.DAY_OF_MONTH, date);
        super.set(Calendar.HOUR_OF_DAY, hour);
        super.set(Calendar.MINUTE, minute);
        init();
    }

    public LunarCalendar(int year, int month, int date, int hour, int minute, int second)
    {
        super();
        super.clear();
        super.set(Calendar.YEAR, year);
        super.set(Calendar.MONTH, month);
        super.set(Calendar.DAY_OF_MONTH, date);
        super.set(Calendar.HOUR_OF_DAY, hour);
        super.set(Calendar.MINUTE, minute);
        super.set(Calendar.SECOND, second);
        init();
    }

    public LunarCalendar(Locale aLocale)
    {
        super(aLocale);
        init();
    }

    public LunarCalendar(long milliseconds, TimeZone timeZone)
    {
        super();
        this.setTimeZone(timeZone);
        this.setTimeInMillis(milliseconds);
        init();
    }

    public LunarCalendar(Time time, boolean hasTime)
    {
        super();
        super.clear();
        super.setTimeZone(TimeZone.getTimeZone(time.timezone));
        super.set(Calendar.YEAR, time.year);
        super.set(Calendar.MONTH, time.month);
        super.set(Calendar.DAY_OF_MONTH, time.monthDay);
        if (hasTime) {
            super.set(Calendar.HOUR_OF_DAY, time.hour);
            super.set(Calendar.MINUTE, time.minute);
            super.set(Calendar.SECOND, time.second);
        }
        init();
    }

    public LunarCalendar(TimeZone zone)
    {
        super(zone);
        init();
    }

    public LunarCalendar(TimeZone zone, Locale aLocale)
    {
        super(zone, aLocale);
        init();
    }

    // ===========================================================================================================================
    // public method
    public void set(int x, int y)
    {
        super.set(x, y);
        init();
    }

    /**
     * monthLunar 传回 西历当月天数
     * 
     * @param y 年份
     * @param m 月份
     * @return 西历当月天数
     */
    public int monthDayCount(int y, int m)
    {
        int solarMonthLength[] = {
                31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };
        if (m == 1)
            return (((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0)) ? 29 : 28);
        else
            return (solarMonthLength[m]);
    }

    /**
     * 传回中文日数字
     * 
     * @param d 传入数字日数
     * @return 传回中文日数字
     */
    public String dateNumToChineseDateNum(int d)
    {
        String s = new String();

        switch (d) {
            case 10:
                s = "初十";
                break;
            case 20:
                s = "二十";
                break;
            case 30:
                s = "三十";
                break;
            default:
                s = nStr2[(int) (d / 10)];
                s += nStr1[d % 10];
                break;
        }
        return (s);
    }

    /**
     * 传回时柱
     * 
     * @param h 传入hour
     * @return 传回时柱
     */
    public String getChineseHourName(int h)
    {
        int i = -1;
        if ((h == 23) || (h == 0))
        {
            i = 0;
        }
        else
        {
            double x = (double) h;
            i = (int) (Math.round(x / 2));
        }
        return Zhi[i];
    }

    /**
     * 传回中文数字
     * 
     * @param d 传入数字
     * @return 传回中文数字
     */
    public String numToChineseNum(int d)
    {
        String s = new String();
        if (d < 10)
            s = nStr1[d];
        else
        {
            s = nStr2[(int) (d / 10)];
            if ((d % 10) > 0)
                s += nStr1[d % 10];
        }
        return s;
    }

    /**
     * 传回该年的复活节(春分后第一次满月周后的第一主日)
     * 
     * @param y 年份
     * @return 传回该年复活节日期(春分后第一次满月周后的第一主日)
     */
    public GregorianCalendar getEasterDateByYeanumToChineseNumr(int y)
    {
        int lMlen, term2 = sTerm(y, 5); // 取得春分日期
        LunarCalendar ref = new LunarCalendar(y, 2, term2, 0, 0, 0);// 取得春分的国历日期物​​件(春分一定出现在3月)
        // 取得取得春分农历
        int day = ref.lunarDay;
        if (day < 15)// 取得下个月圆的相差天数
        {
            lMlen = 15 - day;
        }
        else
        {
            if (ref.isLunarLeapMonth)
            {
                lMlen = leapDays(y);// 农历y年闰月的天数
            }
            else
            {
                lMlen = lunarMonthDayCount(y, ref.lunarMonth);// 农历y年m月的总天数
            }
            lMlen = lMlen - ref.lunarDay + 15;
        }
        ref.add(Calendar.DAY_OF_WEEK, lMlen);
        if (ref.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            ref.add(Calendar.DAY_OF_MONTH, 1);

        while (ref.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)// 求出下个周日
        {
            ref.add(Calendar.DAY_OF_MONTH, 1);
        }

        return ref;
    }

    // ================================================
    // ==================================================
    // =========================
    // private method
    /**
     * 传回该年生肖
     * 
     * @param y 年份
     * @return 传回该年生肖
     */
    private String getAnimalOfYear(int y)
    {
        String Animals[] = {
                "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"
        };
        return Animals[(y - 4) % 12];
    }

    /**
     * 传回某年的第n个节气为几日(从0小寒起算)
     * 
     * @param y 年份
     * @param n 第几个
     * @return 某年的第n个节气为几日(从0小寒起算)
     */
    private int sTerm(int y, int n)
    {
        int result = 0, index, solarTermBase[] = {
                4, 19, 3, 18, 4, 19, 4, 19, 4, 20, 4, 20, 6, 22, 6, 22, 6, 22, 7, 22, 6, 21, 6, 21
        };
        byte temp;
        String solarTermIdx = "0123415341536789:;<9:=<>:=1>?012@015@015@015AB78CDE8CD=1FD01GH01GH01IH01IJ0KLMN;LMBEOPDQRST0RUH0RVH0RWH0RWM0XYMNZ[MB\\]PT^_ST`_WH`_WH`_WM`_WM`aYMbc[Mde]Sfe]gfh_gih_Wih_WjhaWjka[jkl[jmn]ope]qph_qrh_sth_W";
        String solarTermOS = "211122112122112121222211221122122222212222222221222122222232222222222222222233223232223232222222322222112122112121222211222122222222222222222222322222112122112121222111211122122222212221222221221122122222222222222222222223222232222232222222222222112122112121122111211122122122212221222221221122122222222222222221211122112122212221222211222122222232222232222222222222112122112121111111222222112121112121111111222222111121112121111111211122112122112121122111222212111121111121111111111122112122112121122111211122112122212221222221222211111121111121111111222111111121111111111111111122112121112121111111222111111111111111111111111122111121112121111111221122122222212221222221222111011111111111111111111122111121111121111111211122112122112121122211221111011111101111111111111112111121111121111111211122112122112221222211221111011111101111111110111111111121111111111111111122112121112121122111111011111121111111111111111011111111112111111111111011111111111111111111221111011111101110111110111011011111111111111111221111011011101110111110111011011111101111111111211111001011101110111110110011011111101111111111211111001011001010111110110011011111101111111110211111001011001010111100110011011011101110111110211111001011001010011100110011001011101110111110211111001010001010011000100011001011001010111110111111001010001010011000111111111111111111111111100011001011001010111100111111001010001010000000111111000010000010000000100011001011001010011100110011001011001110111110100011001010001010011000110011001011001010111110111100000010000000000000000011001010001010011000111100000000000000000000000011001010001010000000111000000000000000000000000011001010000010000000";
        // return(solarTermBase[n] + Math.floor( solarTermOS.charAt( (
        // Math.floor(solarTermIdx.charCodeAt(y-1900)) - 48) * 24 + n ) ) );
        index = y - 1900;
        temp = solarTermIdx.getBytes()[index];
        index = temp;
        index = (index - 48) * 24 + n;
        result = solarTermOS.getBytes()[index] - 48; // convert char to int
        result += solarTermBase[n];

        return result;
    }

    /**
     * 传回天干地支
     * 
     * @param num 传入 offset
     * @return 传回干支, 0=甲子
     */
    private String getCyclical(int num)
    {
        String Gan[] = {
                "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
        };

        return (Gan[num % 10] + Zhi[num % 12]);
    }

    /**
     * 传回农历 y年的总天数
     * 
     * @param y
     * @return 传回农历 y年的总天数
     */
    private int lYearDays(int y)
    {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1)
        {
            if ((lunarInfo[y - 1900] & i) > 0)
            {
                sum += 1;
            }
        }
        return (sum + leapDays(y));
    }

    /**
     * 传回农历 y年闰月的天数
     * 
     * @param y
     * @return 传回农历 y年闰月的天数
     */
    private int leapDays(int y)
    {
        if (getLunarLeapMonth(y) != 0)
        {
            return ((lunarInfo[y - 1899] & 0xf) == 0xf ? 30 : 29);
        }
        else
            return (0);
    }

    /**
     * 传回农历y年闰哪个月1-12 , 没闰传回 0
     * 
     * @param y
     * @return 传回农历y年闰哪个月1-12 , 没闰传回 0
     */
    private int getLunarLeapMonth(int y)
    {
        int lm = lunarInfo[y - 1900] & 0xf;
        return (lm == 0xf ? 0 : lm);
    }

    /**
     * 传回农历 y年m月的总天数
     * 
     * @param y 年份
     * @param m 月份
     * @return 传回农历 y年m月的总天数
     */
    private int lunarMonthDayCount(int y, int m)
    {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) > 0)
            return 30;
        else
            return 29;
    }

    /**
     * 计算农历日期
     */
    private void init()
    {
        long offset = 0L, temp = 0L;
        int i, firstNode, solarTermDate;
        int springStartDate; // 该年的立春的日子(用数字来表示,不包括年份和月份)
        String solarTerm[] = {
                "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑",
                "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"
        };

        solarYear = this.get(Calendar.YEAR);
        solarMonth = this.get(Calendar.MONTH);
        solarDay = this.get(Calendar.DAY_OF_MONTH);
        GregorianCalendar ref = new GregorianCalendar(1900, 0, 31);
        // 时柱
        this.chineseHourName = getChineseHourName(this.get(Calendar.HOUR_OF_DAY));
        offset = this.getTime().getTime();
        offset = (offset - ref.getTime().getTime()) / 86400000L;
        for (i = 1900; i < 2100 && offset > 0; i++)
        {
            temp = lYearDays(i);
            offset -= temp;
        }
        if (offset < 0)
        {
            offset += temp;
            i--;
        }
        lunarYear = i;
        lunarLeapMonth = getLunarLeapMonth(i);
        isLunarLeapMonth = false;
        for (i = 1; i < 13 && offset > 0; i++)
        {
            // 闰月
            if (lunarLeapMonth > 0 && i == (lunarLeapMonth + 1) && isLunarLeapMonth == false)
            {
                --i;
                this.isLunarLeapMonth = true;
                temp = leapDays(this.lunarYear);
            }
            else
            {
                temp = lunarMonthDayCount(this.lunarYear, i);
            }

            // 解除闰月
            if (this.isLunarLeapMonth == true && i == (lunarLeapMonth + 1))
                this.isLunarLeapMonth = false;

            offset -= temp;
        }

        if (offset == 0L && lunarLeapMonth > 0 && i == lunarLeapMonth + 1)
            if (this.isLunarLeapMonth)
            {
                this.isLunarLeapMonth = false;
            }
            else
            {
                this.isLunarLeapMonth = true;
                --i;
            }

        if (offset < 0L)
        {
            offset += temp;
            --i;
        }

        this.lunarMonth = i;
        this.lunarDay = (int) offset + 1;
        if (this.get(Calendar.HOUR_OF_DAY) == 23)
            this.lunarDay++;
        if (this.isLunarLeapMonth)
            this.chineseDateString = "闰" + numToChineseNum(this.lunarMonth) + "月"
                    + dateNumToChineseDateNum(this.lunarDay);
        else
            this.chineseDateString = numToChineseNum(this.lunarMonth) + "月"
                    + dateNumToChineseDateNum(this.lunarDay);
        // //////年柱1900年立春后为庚子年(60进制36)
        if (solarMonth < 2)
        {
            chineseYearName = getCyclical(solarYear - 1900 + 36 - 1);
            animalOfYear = getAnimalOfYear(solarYear - 1);
        }
        else
        {
            chineseYearName = getCyclical(solarYear - 1900 + 36);
            animalOfYear = this.getAnimalOfYear(solarYear);
        }

        springStartDate = sTerm(solarYear, 2); // 立春日期
        // 依节气调整二月分的年柱, 以立春为界
        if (solarMonth == 1 && (solarDay) >= springStartDate)
        {
            chineseYearName = getCyclical(solarYear - 1900 + 36);
            animalOfYear = getAnimalOfYear(solarYear);
        }
        this.chineseDateString = chineseYearName + "年" + this.chineseDateString;
        // //////月柱1900年1月小寒以前为丙子月(60进制12)
        firstNode = sTerm(solarYear, solarMonth * 2); // 传回当月「节」为几日开始

        // 依节气月柱, 以「节」为界
        if (solarDay >= firstNode)
            chineseMonthName = getCyclical((solarYear - 1900) * 12 + solarMonth + 13);
        else
            chineseMonthName = getCyclical((solarYear - 1900) * 12 + solarMonth + 12);
        long dayCyclical = new GregorianCalendar(solarYear, solarMonth, 1).getTime().getTime();
        dayCyclical = dayCyclical / 86400000L + 25567L + 10L;

        // 日柱
        chineseDayName = getCyclical((int) dayCyclical + solarDay);
        solarTermDate = sTerm(solarYear, solarMonth * 2);
        // 节气
        if (solarTermDate == solarDay)
            this.solarTermInfo = solarTerm[solarMonth * 2];
        else
        {
            solarTermDate = sTerm(solarYear, solarMonth * 2 + 1);
            if (solarTermDate == solarDay)
            {
                this.solarTermInfo = solarTerm[solarMonth * 2 + 1];
            }
        }
        ref = null;
        checkFestival();
    }

    // ================================================
    // ==================================================
    // =========================
    // protected method
    /**
     * 传回FestivalPattern 来检查节日
     * 
     * @param m 月份
     * @param d 日子
     * @return FestivalPattern
     */
    protected String getFestivalPatternByMonthDay(int m, int d)
    {
        String festivalPattern = new String();
        festivalPattern = String.valueOf(m);
        if (m < 10)
            festivalPattern = "0" + festivalPattern;
        if (d < 10)
            festivalPattern = festivalPattern + "0";
        festivalPattern = festivalPattern + String.valueOf(d);
        return festivalPattern;
    }

    /**
     * 检查节日
     */
    protected void checkFestival()
    {
        int lunarMonthLength;
        String searchPattern = new String();
        Hashtable<String, String> lunarFestivalList = new Hashtable<String, String>();
        lunarFestivalList.put("0101*", "大年初一");
        lunarFestivalList.put("0102*", "年初二");
        lunarFestivalList.put("0103*", "年初三");
        lunarFestivalList.put("0115", "元宵");
        lunarFestivalList.put("0505*", "端午节");
        lunarFestivalList.put("0701", "开鬼门");
        lunarFestivalList.put("0707", "七夕");
        lunarFestivalList.put("0714", "盂兰节");
        lunarFestivalList.put("0800", "关鬼门");
        lunarFestivalList.put("0815", "中秋节");
        lunarFestivalList.put("0909*", "重阳节");
        lunarFestivalList.put("0100", "除夕");
        if (!this.isLunarLeapMonth)
        {
            lunarMonthLength = lunarMonthDayCount(this.lunarYear, this.lunarMonth);
            if (lunarMonthLength == this.lunarDay)
            {
                searchPattern = getFestivalPatternByMonthDay((this.lunarMonth + 1) % 12, 0);
            }
            else
            {
                searchPattern = getFestivalPatternByMonthDay(this.lunarMonth, this.lunarDay);
            }

            if (lunarFestivalList.containsKey(searchPattern))
            {
                lunarFestival = (String) lunarFestivalList.get(searchPattern);
            }
            else
            {
                if (lunarFestivalList.containsKey(searchPattern + "*"))
                {
                    lunarFestival = (String) lunarFestivalList.get(searchPattern + "*");
                    this.isHoliday = true;
                }
            }
        }
        int solarTermDate = sTerm(solarYear, solarMonth * 2);
        if ((solarMonth == 3) && (solarTermDate == solarDay)) // 清明节
        {
            lunarFestival = "清明节";
            this.isHoliday = true;
        }
        lunarFestivalList = null;
    }

    private String getMonthViewDay() {
        String lunarDayOrSolarTerm;

        if (this.solarTermInfo != null && !this.solarTermInfo.isEmpty()) {
            lunarDayOrSolarTerm = this.solarTermInfo;
        } else {
            if (this.lunarDay == 1) {
                lunarDayOrSolarTerm = this.numToChineseNum(this.lunarMonth);
            } else {
                lunarDayOrSolarTerm = this.dateNumToChineseDateNum(this.lunarDay);
            }
        }
        
        if (this.lunarFestival != null && !this.lunarFestival.isEmpty()) {
            lunarDayOrSolarTerm += " " + this.lunarFestival;
        }

        return lunarDayOrSolarTerm;
    }

    public String getString(int type) {
        String ret = "";

        String str1 = " ";
        switch (type) {
            case 1:
                ret = this.dateNumToChineseDateNum(this.lunarDay);
                break;
            case 2:
                ret = str1 + this.solarTermInfo;
                break;
            case 3:
                ret = str1 + this.getMonthViewDay();
                break;
            case 4:
                ret = str1 + this.numToChineseNum(this.lunarMonth) + "月";
                break;
            case 5:
                ret = str1 + this.numToChineseNum(this.lunarMonth) + "月"
                        + this.dateNumToChineseDateNum(this.lunarDay);
                break;
            case 6:
                ret = str1 + this.numToChineseNum(this.lunarMonth) + "月" + this.solarTermInfo;
                break;
            case 7:
                ret = str1 + this.numToChineseNum(this.lunarMonth) + "月" + this.getMonthViewDay();
                break;
            case 8:
                ret = str1 + this.chineseYearName + "年"
                        + this.chineseMonthName + "月" + this.chineseDayName + "日";
                if (this.solarTermInfo != null && !this.solarTermInfo.isEmpty()) {
                    ret += ", " + this.solarTermInfo;
                }
                ret += ", 生肖: " + this.animalOfYear;
                break;
            case 9:
                ret = str1 + this.chineseYearName + "年"
                        + this.chineseMonthName + "月, 生肖: " + this.animalOfYear;
                break;
            default:
                ret = "";
                break;
        }
        return ret;
    }

    /**
     * For Testing only
     */
    public static void main(String[] args) throws Exception
    {
        LunarCalendar mc;
        if (args.length == 0)
            mc = new LunarCalendar();
        else
            mc = new LunarCalendar(Integer.valueOf(args[0]).intValue(), Integer.valueOf(args[1])
                    .intValue() - 1, Integer.valueOf(args[2]).intValue());

        System.out.println("Solar Date=" + mc.get(Calendar.YEAR) + "/"
                + (1 + mc.get(Calendar.MONTH)) + "/" + mc.get(DAY_OF_MONTH));
        System.out.println("Lunar Date=" + mc.lunarYear + "," + mc.lunarMonth + "," + mc.lunarDay);
        System.out.println("Lunar Date String=" + mc.chineseDateString);
        System.out.println("Lunar Date in Chinese=" + mc.chineseYearName + "年"
                + mc.chineseMonthName + "月" + mc.chineseDayName + "日" + mc.chineseHourName + "时");
        System.out.println("isLunarLeapMonth=" + mc.isLunarLeapMonth);
        System.out.println("leapMonth=" + mc.lunarLeapMonth);
        System.out.println("isHoliday=" + mc.isHoliday);
        System.out.println("solarTermInfo=" + mc.solarTermInfo);
        System.out.println("AnimalOfYear=" + mc.animalOfYear);
        System.out.println("Lunar Festival=" + mc.lunarFestival);
        mc = null;
    }
}
