package top.qiudb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public final class RegexpUtils {
    /**
     * 匹配email地址
     * <p>
     * <p>
     * 格式: XXX@XXX.XXX.XX
     * <p>
     * 匹配 : foo@bar.com 或 foobar@foobar.com.au
     * <p>
     * 不匹配: foo@bar 或 $$$@bar.com
     */
    public static final String EMAIL_REGEXP = "^([a-zA-Z0-9]+[-|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";
    /**
     * 匹配并提取url
     * <p>
     * <p>
     * 格式: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
     * <p>
     * 匹配 : http://www.suncer.com 或news://www
     * <p>
     * 不匹配: c:/window
     */
    public static final String URL_REGEXP = "(//w+)://([^/:]+)(://d*)?([^#//s]*)";
    /**
     * 匹配并提取http
     * <p>
     * 格式: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 或 ftp://XXX.XXX.XXX 或 https://XXX
     * <p>
     * 匹配 : http://www.suncer.com:8080/index.html?login=true
     * <p>
     * 不匹配: news://www
     */
    public static final String HTTP_REGEXP = "(http|https|ftp)://([^/:]+)(://d*)?([^#//s]*)";
    /**
     * 匹配并提取http
     * <p>
     * 格式: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 或 ftp://XXX.XXX.XXX 或 https://XXX
     * <p>
     * 匹配 : http://www.suncer.com:8080/index.html?login=true
     * <p>
     * 不匹配: news://www
     */
    public static final String HTTP_REGEXP_BANNER = "((https|http|ftp|rtsp|mms)?://)([0-9a-z_!~*'()-]+\\.)*([a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(\\.[a-zA-Z]{1,4})(:[0-9]{1,4})?((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)\\.?$";
    /**
     * 匹配日期
     * <p>
     * <p>
     * 格式(首位不为0): XXXX-XX-XX或 XXXX-X-X
     * <p>
     * <p>
     * 范围:1900--2099
     * <p>
     * <p>
     * 匹配 : 2005-04-04
     * <p>
     * <p>
     * 不匹配: 01-01-01
     */
    public static final String DATE_BARS_REGEXP = "^((((19){1}|(20){1})\\d{2})|\\d{2})-[0,1]?\\d{1}-[0-3]?\\d{1}$";
    /**
     * wzw
     * <p>
     * <p>
     * 匹配格式 20160606
     */
    public static final String DATE_BARS_DAY_REGEXP = "^((((19){1}|(20){1})\\d{2})|\\d{2})[0,1]?\\d{1}[0-3]?\\d{1}$";
    /**
     * 匹配格式 20130101
     */
    public static final String DATE_BARS_REGEXP_SIMPLE =
            "([\\d]{4}(((0[13578]|1[02])((0[1-9])|([12][0-9])|(3[01])))|(((0[469])|11)((0[1-9])|([12][0-9])|30))|(02((0[1-9])|(1[0-9])|(2[0-8])))))|((((([02468][048])|([13579][26]))00)|([0-9]{2}(([02468][048])|([13579][26]))))(((0[13578]|1[02])((0[1-9])|([12][0-9])|(3[01])))|(((0[469])|11)((0[1-9])|([12][0-9])|30))|(02((0[1-9])|(1[0-9])|(2[0-9])))))";
    /**
     * 匹配格式 20130101
     */
    public static final String DATE_BARS_REGEXP_HOUR_FULL =
            "^\\d{4}-(?:0\\d|1[0-2])-(?:[0-2]\\d|3[01])( (?:[01]\\d|2[0-3])\\:[0-5]\\d)?$";
    /**
     * 匹配日期
     * <p>
     * <p>
     * 格式(首位不为0): XXXX-XX-XX或 XXXX-X-X
     * <p>
     * <p>
     * 范围:1900--2099
     * <p>
     * <p>
     * 匹配 : 2005-04-04 11:00:00
     * <p>
     * <p>
     * 不匹配: 01-01-01
     */
    public static final String DATE_BARS_REGEXP_HOUR =
            "^((((19){1}|(20){1})\\d{2})|\\d{2})-[0,1]?\\d{1}-[0-3]?\\d{1}\\s[0,2]?\\d{1}[0,9]?\\d{1}$";

    /**
     * 匹配 : 2005-04-04 11:00:00
     */
    public static final String DATE_BARS_REGEXP_HOUR_MIN_SS =
            "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$";
    /**
     * 匹配日期
     * <p>
     * <p>
     * 格式: XXXX.XX.XX
     * <p>
     * <p>
     * 范围:
     * <p>
     * <p>
     * 匹配 : 2005.04.04
     * <p>
     * <p>
     * 不匹配: 01.01.01
     */
    public static final String DATE_SLASH_REGEXP = "^[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}$";

    /**
     * 匹配电话
     * <p>
     * <p>
     * 格式为: 0XXX-XXXXXX(10-13位首位必须为0) 或0XXX XXXXXXX(10-13位首位必须为0) 或
     * <p>
     * (0XXX)XXXXXXXX(11-14位首位必须为0) 或 XXXXXXXX(6-8位首位不为0) 或 XXXXXXXXXXX(11位首位不为0)
     * <p>
     * <p>
     * 匹配 : 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或 010-12345678 或 12345678912
     * <p>
     * <p>
     * 不匹配: 1111-134355 或 0123456789
     */
    public static final String PHONE_REGEXP =
            "^(?:0[0-9]{2,3}[-//s]{1}|//(0[0-9]{2,4}//))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

    /**
     * 11位手机号格式验证
     */
    public static final String MOBILE_PHONE_REGEXP = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    /**
     * 20位联系方式格式验证
     */
    public static final String PHONE_20_REGEXP = "^[0-9\\-]{1,20}$";

    /**
     * 匹配身份证
     * <p>
     * 格式为: XXXXXXXXXX(10位) 或 XXXXXXXXXXXXX(13位) 或 XXXXXXXXXXXXXXX(15位) 或 XXXXXXXXXXXXXXXXXX(18位)
     * <p>
     * 匹配 : 0123456789123
     * <p>
     * 不匹配: 0123456
     */
    public static final String IDENTITY_CARD_REGEXP =
            "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    /**
     * 匹配邮编代码
     * <p>
     * 格式为: XXXXXX(6位)
     * <p>
     * 匹配 : 012345
     * <p>
     * 不匹配: 0123456
     */
    public static final String ZIP_REGEXP = "^[0-9]{6}$";

    /**
     * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠/ 即空格,制表符,回车符等 )
     * <p>
     * 格式为: x 或 一个一上的字符
     * <p>
     * 匹配 : 012345
     * <p>
     * 不匹配: 0123456 // ;,:-<>//s].+$";//
     */
    public static final String NON_SPECIAL_CHAR_REGEXP = "^[^'/";

    /**
     * 匹配一位数的0-1之间的整数
     */
    public static final String ZERO_TO_ONE_NUMBER = "[0-1]$";

    /**
     * 匹配一位数的0-2之间的整数
     */
    public static final String ZERO_TO_TWO_NUMBER = "[0-2]$";

    /**
     * 匹配一位数的0-3之间的整数
     */
    public static final String ZERO_TO_THREE_NUMBER = "[0-3]$";
    /**
     * 匹配0-18的正整数
     */
    public static final String ONE_TO_EIGHTEEN_NUMBER = "^(18|[0-9]|(1[0-8]))$";

    /**
     * 匹配1-99的正整数
     */
    public static final String ONE_TO_NINETYNINE_NUMBER = "^(99|[1-9]|([1-9][0-9]))$";

    /**
     * 匹配非负整数（正整数 + 0)
     */
    public static final String NON_NEGATIVE_INTEGERS_REGEXP = "^//d+$";
    /**
     * 匹配带1位小数且不超过2位的数
     */
    public static final String CAR_LENGTH_REGEXP = "^(\\d|\\d\\d)(\\.\\d)?$";
    /**
     * 匹配整数位最大3位且小数位最大2位的数
     */
    public static final String CAR_LOAD_REGEXP = "^(\\d{1,3})(\\.\\d{1,2})?$";
    /**
     * 匹配不包括零的非负整数（正整数 > 0)
     */
    public static final String NON_ZERO_NEGATIVE_INTEGERS_REGEXP = "^[1-9]+//d*$";
    /**
     * 匹配包括零的正整数
     */
    public static final String ZERO_NEGATIVE_INTEGERS_REGEXP = "^[0-9]*[0-9][0-9]*$";
    /**
     * 匹配正整数
     */
    public static final String POSITIVE_INTEGER_REGEXP = "^[0-9]*[1-9][0-9]*$";
    /**
     * 匹配非正整数（负整数 + 0）
     */
    public static final String NON_POSITIVE_INTEGERS_REGEXP = "^((-//d+)|(0+))$";
    /**
     * 匹配负整数
     */
    public static final String NEGATIVE_INTEGERS_REGEXP = "^-[0-9]*[1-9][0-9]*$";
    /**
     * 匹配整数
     */
    public static final String INTEGER_REGEXP = "^-?//d+$";
    /**
     * 匹配非负浮点数（正浮点数 + 0）
     */
    public static final String NON_NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^\\d+(\\.\\d+)?$";
    /**
     * 匹配正浮点数
     */
    public static final String POSITIVE_RATIONAL_NUMBERS_REGEXP = "^[0-9]+(.[0-9]{1})?$";
    /**
     * 匹配正浮点数(1-6位)
     */
    public static final String POSITIVE_ONE_TO_SIX_RATIONAL_NUMBERS_REGEXP = "^((?!0\\d)\\d+(\\.\\d{1,6}?))$";
    /**
     * 匹配非正浮点数（负浮点数 + 0）
     */
    public static final String NON_POSITIVE_RATIONAL_NUMBERS_REGEXP = "^((-//d+(//.//d+)?)|(0+(//.0+)?))$";
    /**
     * 匹配负浮点数
     */
    public static final String NEGATIVE_RATIONAL_NUMBERS_REGEXP =
            "^(-(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

    /**
     * 匹配浮点数
     */
    public static final String RATIONAL_NUMBERS_REGEXP = "^(-?//d+)(//.//d+)?$";
    /**
     * 匹配由26个英文字母组成的字符串
     */
    public static final String LETTER_REGEXP = "^[A-Za-z]+$";
    /**
     * 匹配由26个英文字母的大写组成的字符串
     */
    public static final String UPWARD_LETTER_REGEXP = "^[A-Z]+$";
    /**
     * 匹配由A-D英文字母的大写的字符
     */
    public static final String UPWARD_LETTER_A_D = "^[A-D]{1}";
    /**
     * 匹配由26个英文字母的大写的字符
     */
    public static final String UPWARD_LETTER_STR = "^[A-Z]{1}$";
    /**
     * 匹配由26个英文字母的小写组成的字符串
     */
    public static final String LOWER_LETTER_REGEXP = "^[a-z]+$";
    /**
     * 匹配由数字和26个英文字母组成的字符串
     */
    public static final String LETTER_NUMBER_REGEXP = "^[A-Za-z0-9]+$";
    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串
     */
    public static final String LETTER_NUMBER_UNDERLINE_REGEXP = "^//w+$";
    /**
     * 必须由数字和26个英文字母组合组成的字符串
     */
    public static final String LETTER_NUMBER_REGEXP_MUST = "[0-9]+[a-zA-Z]+[0-9a-zA-Z]*|[a-zA-Z]+[0-9]+[0-9a-zA-Z]*";
    /**
     * 匹配5位数字
     */
    public static final String FIVE_NUMBER_ONLY_REGEXP = "^[0-9]{5}$";
    /**
     * 匹配4位数字
     */
    public static final String FOUR_NUMBER_ONLY_REGEXP = "^[0-9]{4}$";
    /**
     * 匹配1位字母
     */
    public static final String ONE_LETTER = "^[a-zA-Z]{1}$";
    /**
     * 匹配19位数字
     */
    public static final String NINETEEN_NUMBER_ONLY_REGEXP = "^[0-9]{19}$";
    /**
     * 匹配8位数字
     */
    public static final String EIGHT_NUMBER_ONLY_REGEXP = "^[0-9]{8}$";
    /**
     * 匹配19位数字
     */
    public static final String TWENTY_NUMBER_ONLY_REGEXP = "^[0-9]{20}$";
    /**
     * 匹配1位数字
     */
    public static final String NUMBER_ONLY_ONE_REGEXP = "^\\d$";
    /**
     * 匹配日期
     * <p>
     * <p>
     * 格式: yyyymmdd
     * <p>
     * <p>
     * 范围:
     * <p>
     * <p>
     * 匹配 : 20050404
     * <p>
     * <p>
     * 不匹配: 010101
     */
    public static final String DATE_YYYYMMMDD_REGEXP = "("
            + "(^\\d{3}[1-9]|\\d{2}[1-9]\\d{1}|\\d{1}[1-9]\\d{2}|[1-9]\\d{3}" + "(10|12|0?[13578])"
            + "((3[01]|[12][0-9]|0?[1-9])?)" + "([\\s]?)" + "((([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9]))?))$" + "|"
            + "(^\\d{3}[1-9]|\\d{2}[1-9]\\d{1}|\\d{1}[1-9]\\d{2}|[1-9]\\d{3}" + "(11|0?[469])" + "(30|[12][0-9]|0?[1-9])"
            + "([\\s]?)" + "((([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9]))?))$" + "|"
            + "(^\\d{3}[1-9]|\\d{2}[1-9]\\d{1}|\\d{1}[1-9]\\d{2}|[1-9]\\d{3}" + "(0?2)" + "(2[0-8]|1[0-9]|0?[1-9])"
            + "([\\s]?)" + "((([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9]))?))$" + "|"
            + "(^((\\d{2})(0[48]|[2468][048]|[13579][26]))|((0[48]|[2468][048]|[13579][26])00)" + "(0?2)" + "(29)"
            + "([\\s]?)" + "((([0-1]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d))?))$" + ")";
    /**
     * 匹配日期 格式: yyyymm 匹配 : 200504 不匹配: 010101
     */
    public static final String DATE_YYYYMM_REGEXP = "^(\\d{4})(0\\d{1}|1[0-2])$";
    /**
     * 匹配日期 格式: yyyy-MM-dd 10位 匹配 : 2005-04-01 不匹配: 010101
     */
    public static final String DATE_YYYY_MM_DD_REGEXP = "^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
    /**
     * 匹配日期 格式: yyyy/MM/dd 10位 匹配 : 2005/04/01 不匹配: 010101
     */
    public static final String DATE_YYYYMMDD_REGEXP = "^((?:19|20)\\d\\d)/(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])$";
    /**
     * 匹配日期 格式: yyyyMMdd 8位 匹配 : 20050401 不匹配: 010101
     */
    public static final String DATE_YYYYMMDD_EIGHT_REGEXP =
            "^((?:19|20)\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$";
    /**
     * 匹配格式 2016-02-02 01:01
     */
    public static final String DATE_BARS_REGEXP_HOUR_MIN =
            "^\\d{4}-(?:0\\d|1[0-2])-(?:[0-2]\\d|3[01]) (?:[01]\\d|2[0-3])\\:[0-5]\\d?$";
    /**
     * 匹配格式 2016-02-02 01:01:01
     */
    public static final String DATE_BARS_REGEXP_HOUR_MIN_SECOND =
            "^\\d{4}[-]([0][1-9]|(1[0-2]))[-]([1-9]|([012]\\d)|(3[01]))([ \\t\\n\\x0B\\f\\r])(([0-1]{1}[0-9]{1})|([2]{1}[0-4]{1}))([:])(([0-5]{1}[0-9]{1}|[6]{1}[0]{1}))([:])((([0-5]{1}[0-9]{1}|[6]{1}[0]{1})))$";
    /**
     * 匹配整数或者空串
     */
    public static final String NUMBER_BLANK_REGEXP = "^[1-9]\\d*|0|^\\s*$";
    /**
     * 匹配正整数或者空串
     */
    public static final String POSITIVE_NUMBER_BLANK_REGEXP = "^[1-9]\\d*|^\\s*$";
    /**
     * 任意位数的非负整数
     */
    public static final String NATURAL_NUMBER = "\\d*$";
    /**
     * 数字加逗号
     */
    public static final String COMMA_NUMBER = "[\\d,]*$";
    /**
     * 匹配汉字
     */
    public static final String CHINESE_VAR = "[\\u4e00-\\u9fa5]*$";
    /**
     * 不允许包含"/"或"\"正则
     */
    public static final String BACK_SLANT_REGEXP = "^[^/^\\\\]+$";

    /**
     * 匹配日期 格式: yyyy-MM 7位 匹配 : 2005-04 不匹配: 0101
     */
    public static final String DATE_YYYY_MM_REGEXP = "^((?:19|20)\\d\\d)-(0[1-9]|1[012])$";


    private RegexpUtils() {
    }

    /**
     * 校验正则表达式结果
     *
     * @param content 校验内容
     * @param regex   正则表达式
     * @return 是否匹配
     */
    public static boolean validateInfo(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
}