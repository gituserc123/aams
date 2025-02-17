package com.aier.cloud.aams.api.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * wangke工具类
 *
 * @Author: wangke
 * @Date: 2020/10/27 14:52
 */
public class CommonUtils {
    public static final SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");

    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat yyyy_MM_dd2 = new SimpleDateFormat("yyyy/MM/dd");

    public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss3 = new SimpleDateFormat("yyyyMMddhhmmss");

    //String date = "Thu Feb 02 00:00:00 WET 2012"
    public static final SimpleDateFormat EEE_MMM_dd_HH_mm_ss_zzz_yyyy = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss24 = new SimpleDateFormat("yyyyMMddHH24mmss");

    /**
     * 判断对象是否为空,为空返回true否则返回false
     *
     * @param obj 待判断对象
     * @return boolean
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Collection) {
            Collection<?> c = (Collection<?>) obj;
            return c.size() < 1;
        }

        if (obj instanceof String) {
            String s = (String) obj;
            return s.isEmpty();
        }
        if (obj instanceof StringBuilder) {
            StringBuilder s = (StringBuilder) obj;
            return s.toString().isEmpty();
        }
        if (obj.getClass().isArray()) {
            if (Array.getLength(obj) == 0) {
                return true;
            }
        }
        if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) obj;
            if (map.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否不为空,非空返回true否则返回false
     *
     * @param obj 待判断对象
     * @return boolean;
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }


    /**
     * 数字类型为空处理
     *
     * @param o
     * @return
     */
    public static String numberNullCast(String o) {
        if (isEmpty(o)) {
            return "0";
        }
        return o;
    }


    /**
     * 字符类型为空处理返回-
     *
     * @param o
     * @return
     */
    public static String StringNullCast(String o) {
        if (isEmpty(o)) {
            return "-";
        }
        return o;
    }

    /**
     * 字符类型为空处理返回""
     *
     * @param o
     * @return
     */
    public static String StringEmptyCast(String o) {
        if (isEmpty(o)) {
            return "";
        }
        return o;
    }

    /**
     * 字符类型为空处理返回-
     *
     * @param o
     * @return
     */
    public static String StringNullCast(String o, String returnValue) {
        if (isEmpty(o)) {
            return returnValue;
        }
        return o;
    }

    /**
     * 将Map转换为对象
     *
     * @param paramMap
     * @param cls
     * @return
     */
    public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> cls) {
        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
    }

    /**
     * 将Map转换为List对象
     *
     * @param paramMap
     * @param cls
     * @return
     */
    public static <T> T parseListObject(Map<String, Object> paramMap, String name, Class<T> cls) {
        return (T) JSON.parseArray(JSON.parseObject(JSONObject.toJSONString(paramMap)).getString(name), cls);
    }

    /**
     * 对象转为Map
     *
     * @param ob
     * @return
     */
    public static Map<String, Object> objectToMap(Object ob) {
        return BeanUtil.beanToMap(ob);
    }


    /**
     * 把日期格式转为福州需要的格式yyyyMMddHHmmss
     *
     * @param dt
     * @return
     */
    public static String transDateNew(String dt) {
        Date inpTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            inpTime = sdf.parse(dt);
        } catch (Exception e) {

        }
        return DateFormatUtils.format(inpTime, "yyyy-MM-dd HH:mm");
    }

    /**
     * 把日期格式转为需要的格式yyyyMMddHHmmss
     *
     * @param dt
     * @return
     */
    public static String transDateNew(String dt, String f1, String f2) {
        Date inpTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(f1);
        try {
            inpTime = sdf.parse(dt);
        } catch (Exception e) {

        }
        return DateFormatUtils.format(inpTime, f2);
    }

    /**
     * 工具
     * 只保留汉字
     */
    public static String getChineseWord(String oriText) {
        if (isNotEmpty(oriText)) {
            return oriText.replaceAll("[^\u4E00-\u9FA5]", "");
        }
        return oriText;
    }

    /**
     * 工具
     * 只保留数字
     */
    public static String getNumber(String oriText) {
        String REGEX = "[^(0-9)]";
        String ticket = Pattern.compile(REGEX).matcher(oriText).replaceAll("").trim();
        return ticket;
    }

    /**
     * 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
     */
    public static String getWordAndNumber(String oriText) {
        String REGEX = "[^a-zA-Z0-9]";
        String ticket = Pattern.compile(REGEX).matcher(oriText).replaceAll("").trim();
        return ticket;
    }


    /**
     * 根据年月日计算年龄【虚岁】
     *
     * @param birthTimeString 如:"1994-11-14"
     * @return
     */
    public static int getAgeFromBirthTime(String birthTimeString) {
        // 先截取到字符串中的年、月、日
        String[] strs = birthTimeString.trim().split("/");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;
        // 先大致赋值
        int age = yearMinus;
        // 选了未来的年份
        if (yearMinus < 0) {
            age = 0;
        }
        // 同年的，要么为1，要么为0
        else if (yearMinus == 0) {
            // 选了未来的月份
            if (monthMinus < 0) {
                age = 0;
            }
            // 同月份的
            else if (monthMinus == 0) {
                // 选了未来的日期
                if (dayMinus < 0) {
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        }
        // 往年的
        else if (yearMinus > 0) {
            // 当前月<生日月
            if (monthMinus < 0) {

            }
            // 同月份的，再根据日期计算年龄
            else if (monthMinus == 0) {
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age;
    }


    /**
     * 保留两位小数
     *
     * @param s
     * @return
     */
    public static Object getDoubleFormat(Object s) {
        if (s instanceof Double) {
            DecimalFormat df = new DecimalFormat("0.00");//会四舍五入
            s = df.format(s);
        } else if (s instanceof Float) {
            DecimalFormat df = new DecimalFormat("0.00");//会四舍五入
            s = df.format(s);
        } else if (s instanceof String) {
            BigDecimal temps = new BigDecimal(s + "");
            s = temps.setScale(2, RoundingMode.HALF_UP);
        } else if (s instanceof BigDecimal) {
            s = ((BigDecimal) s).setScale(2, RoundingMode.HALF_UP);
        }
        return s;
    }


    /**
     * 截取字符串后N位
     *
     * @param s
     * @param n
     * @return
     */
    public static String getStringByN(String s, int n) {
        if (isNotEmpty(s)) {
            if (s.length() > n) {
                return s.substring(s.length() - n);
            }
        }
        return s;
    }


    /**
     * 截取字符串前t位
     *
     * @param s
     * @param t
     * @return
     */
    public static String SubString(Object s, int t) {
        if (isNotEmpty(s)) {
//            if (s.toString().length() > t) {
//                return s.toString().substring(0, t);
//            }
            return StringUtils.substring(s.toString(), 0, t);
        }
        return s + "";
    }

    /**
     * 对List<map> 进行分组合并，按某个相同的key进行合并，并sum某个key，
     * 类似单表group by 功能
     */
    public static void summaryGroup() {
        Map<String, Object> m1 = new HashMap<>();
        Map<String, Object> m11 = new HashMap<>();
        Map<String, Object> m12 = new HashMap<>();

        List<Map<String, Object>> list = new ArrayList<>();

        List<Map<String, Object>> result = new ArrayList<>();

        m1.put("aa", 11);
        m1.put("bb", "xm");
        m1.put("cc", 122);
        m1.put("dd", 122);

        m11.put("aa", 12);
        m11.put("bb", "xm");
        m11.put("cc", 10);
        m11.put("dd", 122);


        m12.put("aa", 13);
        m12.put("bb", "zs");
        m12.put("cc", 31);
        m12.put("dd", 122);

        list.add(m1);
        list.add(m11);
        list.add(m12);

        //按bb进行分组统计


        Map<String, List<Map<String, Object>>> glist = list.stream().collect(Collectors.groupingBy(e -> e.get("bb").toString()));

        glist.forEach((k, slist) -> {
            Map<String, Object> nmap = new HashMap<>();
            IntSummaryStatistics sumcc = slist.stream().collect(Collectors.summarizingInt(e -> Integer.valueOf(e.get("cc").toString())));
            nmap.put("aa", slist.get(0).get("aa"));
            nmap.put("bb", slist.get(0).get("bb"));
            nmap.put("cc", sumcc.getSum());//求和
            nmap.put("counts", slist.size());//计算
            nmap.put("dd", slist.get(0).get("dd"));
            result.add(nmap);


        });
        System.out.println("--------summaryGroup-------------");
        result.forEach(x -> {
            System.out.println(x);
        });


    }


    /**
     * 按t补全数字前面0返回字符串
     *
     * @param s
     * @param t
     * @return
     */
    public static String bqNumber(String s, int t) {
        if (isNotEmpty(s)) {
            if (s.length() < t) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < t - s.length(); i++) {
                    sb.append("0");
                }
                sb.append(s);
                return sb.toString();
            }
        }
        return s;
    }


    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> listSplit(List<T> source, int n) {
        if (null == source || source.size() == 0 || n <= 0) {
            return null;
        }

        List<List<T>> result = new ArrayList<List<T>>();
        List<List<T>> fResult = new ArrayList<List<T>>();

        int sourceSize = source.size();
        int size = (source.size() / n) + 1;
        for (int i = 0; i < size; i++) {
            List<T> subset = new ArrayList<T>();
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        for (List<T> list : result) {
            if (list != null && list.size() > 0) {
                fResult.add(list);
            }
        }
        return fResult;
    }

    /**
     * 判断金额正负或0
     * //        if(bigDecimal.compareTo(zero) == -1){
     * //            System.out.println("a小于b");
     * //        }
     * //
     * //        if(bigDecimal.compareTo(zero) == 0){
     * //            System.out.println("a等于b");
     * //        }
     * //
     * //        if(bigDecimal.compareTo(zero) == 1){
     * //            System.out.println("a大于b");
     * //        }
     */
    public static Integer isNegative(String s) {
        BigDecimal bigDecimal = new BigDecimal(s);
        BigDecimal zero = new BigDecimal("0");
        return bigDecimal.compareTo(zero);
    }


    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     */
    public static String StringFilter(String str) {
//        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        String regEx = "[`~!@#$^&*+=|{}';',\\[\\]<>/?~！@#￥……&*——+|{}【】‘；”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    /**
     * 判断费用是否符合， S3是误差范围
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public static boolean compareToTotal(String s1, String s2, String s3) {
        boolean bool = false;
        BigDecimal hisToal = new BigDecimal(s1);
        BigDecimal insureToal = new BigDecimal(s2);
        if (hisToal.subtract(insureToal).compareTo(new BigDecimal(s3)) > -1 ||
                hisToal.subtract(insureToal).compareTo(new BigDecimal("-" + s3)) < 1) {
            return true;
        }
        return bool;
    }

    //字符串某个字符出现位置
    public static Integer indexOfStr(String str, int index) {
        Pattern pattern = Pattern.compile("\\^");
        Matcher findMatcher = pattern.matcher(str);
        int number = 0;
        while (findMatcher.find()) {
            number++;
            if (number == index) {
                break;
            }
        }
        try {
            return findMatcher.start();
        } catch (Exception e) {
            return null;
        }
    }

    //    URL编码
    private String encodeUrl(String url) {
        String encodeUrl = "";
        try {
            encodeUrl = URLEncoder.encode(url, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return encodeUrl;
        }
    }

    //    URL解码
    private String decodeUrl(String url) {
        String decodeUrl = "";
        try {
            decodeUrl = URLDecoder.decode(url, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return decodeUrl;
        }
    }

    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 取范围日期的随机日期时间,不含边界
     *
     * @param startDay
     * @param endDay
     * @return
     */
    public static LocalDateTime randomLocalDateTime(int startDay, int endDay) {

        int plusMinus = 1;
        if (startDay < 0 && endDay > 0) {
            plusMinus = Math.random() > 0.5 ? 1 : -1;
            if (plusMinus > 0) {
                startDay = 0;
            } else {
                endDay = Math.abs(startDay);
                startDay = 0;
            }
        } else if (startDay < 0 && endDay < 0) {
            plusMinus = -1;

            //两个数交换
            startDay = startDay + endDay;
            endDay = startDay - endDay;
            startDay = startDay - endDay;

            //取绝对值
            startDay = Math.abs(startDay);
            endDay = Math.abs(endDay);

        }

        LocalDate day = LocalDate.now().plusDays(plusMinus * RandomUtils.nextInt(startDay, endDay));
        int hour = RandomUtils.nextInt(1, 24);
        int minute = RandomUtils.nextInt(0, 60);
        int second = RandomUtils.nextInt(0, 60);
        LocalTime time = LocalTime.of(hour, minute, second);
        return LocalDateTime.of(day, time);
    }

    /**
     * 取范围日期的随机日期时间,不含边界
     *
     * @param startDay
     * @param endDay
     * @return
     */
    public static Date randomDateTime(int startDay, int endDay) {
        LocalDateTime ldt = randomLocalDateTime(startDay, endDay);
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }


    /**
     * 获取随机时间  -1 1 为当天
     *
     * @param startDay  随机时间前量
     * @param endDay    随机时间后量
     * @param formatter 随机实际格式
     */
    public static String getRandomDateTime(int startDay, int endDay, String formatter) {
        return randomLocalDateTime(startDay, endDay).format(DateTimeFormatter.ofPattern(formatter));
    }


    /**
     * 判断时间类型
     * 正则表达式判断时间格式
     * str 传入的时间数据
     * format 目标时间格式
     */
    public static Boolean isFormatDate(String str, SimpleDateFormat format) {
        String rexp = "";
        if (format.equals(yyyyMMdd)) {
            rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        } else if (format.equals(yyyy_MM_dd)) {
            rexp = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)   \n" +
                    "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        } else {
            return false;
        }
        Pattern p = Pattern.compile(rexp);
        Matcher m = p.matcher(str);
        boolean b = m.matches();
        return b;
    }


    /**
     * 黔南
     * SHA-1  加密
     *
     * @param byteArray
     * @return
     */
    public static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigites = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigites[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigites[b & 0xf];
        }
        return new String(resultCharArray);
    }

    /**
     * 黔南
     * 计算消息摘要
     */
    public static String getMessageDigest(String str, String endName) {
        String stnString = null;
        byte[] digest = null;
        if (StringUtils.isBlank(endName)) {
            endName = "SHA-1";
        }
        try {
            MessageDigest md = MessageDigest.getInstance(endName);
            md.update(str.getBytes("utf-8"));
            digest = md.digest();
            stnString = byteArrayToHex(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stnString;
    }


    /**
     * 获取某个月第一天的开始时刻
     *
     * @param month
     * @return
     */
    public static String getFirstDayTimeOfMonth(int month) {
        Calendar cal = Calendar.getInstance();
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        // 格式化日期，获取开始时刻
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }


    /**
     * 获得某月的最后一天的最后时刻
     *
     * @param month 要获取的月份
     * @return
     */
    public static String getLastDayTimeOfMonth(int month) {
        Calendar cal = Calendar.getInstance();
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取月份的最大天数
        int lastDay = 0;
        //2月份每年的天数不固定
        if (month == 2) {
            lastDay = cal.getLeastMaximum(Calendar.DAY_OF_MONTH);
        } else {
            lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期，获取最后时刻
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }


    /**
     * List<Map<String, Object>> 到 List<T> 数据转换
     */
    public static <T> List<T> setList(final List<Map<String, Object>> srcList, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < srcList.size(); i++) {
            try {
                T t = clazz.newInstance();
                Field[] fields = t.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!"serialVersionUID".equals(field.getName())) {
                        //设置对象的访问权限，保证对private的属性的访问
                        field.setAccessible(true);
                        //读取配置转换字段名，并从map中取出数据
                        Object v = srcList.get(i).get(field.getName());
                        field.set(t, convert(v, field.getType()));
                    }
                }
                list.add(t);
            } catch (Exception ex) {
                ex.toString();
            }

        }
        ;
        return list;
    }

    /**
     * 字段类型转换
     */
    private static <T> T convert(Object obj, Class<T> type) {
        System.out.println("-convert-" + obj.toString() + ",-type-" + type.toString());
        if (obj != null && StringUtils.isNotBlank(obj.toString())) {
            if (type.equals(String.class)) {
                return (T) obj.toString();
            } else if (type.equals(BigDecimal.class)) {
                return (T) new BigDecimal(obj.toString());
            } else if (type.equals(Double.class)) {
                return (T) Double.valueOf(obj.toString());
            } else if (type.equals(Integer.class)) {
                return (T) Integer.valueOf(obj.toString());
            } else if (type.equals(Date.class)) {
                return (T) new Date(obj.toString());
            } else {
                //其他类型转换
                return (T) obj.toString();
            }

        }
        return null;
    }

    //获取字符串的字节数
    public static int getWordCount(String s) {
        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return length;
    }

    /**
     * 按字节数将string拆分
     * author:wangk
     *
     * @param strContent
     * @param maxLength  这是每个字符串的字节数
     * @return
     */
    public static String[] split1(String strContent, int maxLength) {
        if (StringUtils.isBlank(strContent)) return new String[]{};
        if (maxLength <= 0) return new String[]{strContent};
        char[] xxxx = strContent.toCharArray();
        int strlen = xxxx.length;//字符长度
        int startIndex = 0, endIndex = 0;//字符数组的截取下标
        List<String> list2 = new ArrayList();//用来存储截取后的字符串
        char[] ch = null;
        int b = 0;
        for (int i = 0; i < strlen; ) {
            //先按指定字节数截取一段字符，并转为串
            endIndex = startIndex + maxLength / 2;
            ch = ArrayUtils.subarray(xxxx, startIndex, endIndex);
            //计算这段字符串的字节数
            b = getWordCount(String.valueOf(ch));
            //若它的字节数还不到指定的字节数，则进入while(){}里面继续往后截取
            while ((maxLength - b) > 1) {
                endIndex = endIndex + (maxLength - b) / 2;
                if (endIndex >= strlen) {
                    endIndex = strlen;
                    break;
                }
                ch = ArrayUtils.subarray(xxxx, startIndex, endIndex);
                b = getWordCount(String.valueOf(ch));
            }
            //在while里面得到合理的截取下标后，则进行截取，并放入list2里面去。
            ch = ArrayUtils.subarray(xxxx, startIndex, endIndex);
//    		System.out.println("被截取的字符串为=="+String.valueOf(ch));
            list2.add(String.valueOf(ch));
            startIndex = endIndex + 1;
            i = startIndex;
            if (endIndex >= strlen) {
//                System.out.println("is over");
                break;
            }
        }
        return (String[]) list2.toArray(new String[list2.size()]);
    }

    /**
     * map结构String数据转回Map
     * mapStringToMap
     *
     * @param str
     * @return
     */
    public static Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0];
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday
     * @param endDate
     * @return
     */
    public static String getBirthAge(Long birthday, Date endDate) {
        if (birthday == null) {
            return "0";
        } else {
            Calendar now = Calendar.getInstance();
            if (endDate != null) {
                now.setTime(endDate);
            }

            int yearNow = now.get(1);
            int monthNow = now.get(2);
            int dayNow = now.get(5);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(birthday);
            int yearBirth = cal.get(1);
            int monthBirth = cal.get(2);
            int dayBirth = cal.get(5);
            if (now.before(cal)) {
                return "0";
            } else {
                int day = dayNow - dayBirth;
                int month = monthNow - monthBirth;
                int year = yearNow - yearBirth;
                if (day < 0) {
                    --month;
                    now.add(2, -1);
                    day += now.getActualMaximum(5);
                }

                if (month < 0) {
                    month = (month + 12) % 12;
                    --year;
                }

//                if (year > 0) {
//                    return year < 6 && month != 0 ? year + "岁" + month + "月" : year + "岁";
//                } else {
//                    return month > 0 ? month + "月" + day + "天" : day + "天";
//                }

                if (year > 0) {
                    return year + "";
                } else {
                    return month + "";
                }
            }
        }
    }

    /**
     * 相差天数是否大于等于days
     *
     * @param beginDate
     * @param endDate
     * @param days
     * @return
     */
    public static Boolean comDate(Date beginDate, Date endDate, Long days) {
        Boolean bool = false;
        Long starTime = beginDate.getTime();
        Long endTime = endDate.getTime();
        Long num = endTime - starTime;
        Long numDays = num / 24 / 60 / 60 / 1000;
        if (numDays >= days) {
            bool = true;
        }
        return bool;
    }

    /**
     * 计算百分比
     *
     * @param numerator
     * @param denominator
     * @param digit
     * @return
     */
    public static String percentage(BigDecimal numerator, BigDecimal denominator, Integer digit) {
//        // 进行除法运算
//        BigDecimal result = numerator.divide(denominator, digit, BigDecimal.ROUND_HALF_UP);
//
//        // 创建NumberFormat对象以显示百分比
//        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
//
//        // 设置小数位数（可选）
//        percentFormatter.setMinimumFractionDigits(digit); // 保留两位小数
//
//        // 将小数值格式化为百分比字符串
//        String percentage = percentFormatter.format(result);
        String result = "0%";
        if (denominator.compareTo(BigDecimal.ZERO) != 0) {
            double moreThanMax = numerator.divide(denominator, 4, BigDecimal.ROUND_DOWN).doubleValue();
            result = (moreThanMax * 100) + "%";
        }
        return result;
    }

    /**
     * 使用Stream API拆分List的方法
     *
     * @param list
     * @param batchSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitListWithStream(List<T> list, int batchSize) {
        return IntStream.range(0, (list.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> list.subList(i * batchSize, Math.min((i + 1) * batchSize, list.size())))
                .collect(Collectors.toList());
    }

    /**
     * 获取年份
     *
     * @param text
     * @return
     */
    public static String getYear(String text) {
        String pattern = "^\\d{4}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return m.group();  // 如果找到匹配项，则返回匹配的字符串
        } else {
            return null;  // 如果没有找到匹配项，则返回null
        }
    }
}
