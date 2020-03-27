package com.zconly.pianocourse.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Base64;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.MainApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author DengBin
 */
public class StringTool {

    public static boolean isEmailOrPhone(Context mContext, String emailOrPhone) {
        if (!StringTool.isMobile(emailOrPhone)) {
            ToastUtil.toast(mContext.getString(R.string.toast_not_phone));
            return false;
        }
        return true;
    }

    public static String splitAndFilterString(String input, int length) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        String str = input.replaceAll("//&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "......";
        }
        return str;
    }

    /**
     * 字符串替换
     */
    public static String replace(String v, String string, String string2) {
        if (TextUtils.isEmpty(v)) {
            return null;
        }
        return v.replace(string, string2);
    }

    /**
     * String转int，异常返回-1
     */
    public static int String2Int(String s) {
        int i = -1;
        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * String转int，异常返回-1
     */
    public static double String2Double(String s) {
        double i = -1;
        try {
            i = Double.parseDouble(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 对象转String
     */
    public static String obj2String(Object obj) {
        String strRet = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            strRet = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strRet;
    }

    /**
     * String转对象
     */
    public static Object string2Obj(String ret) {
        Object obj = null;
        if (ret != null) {
            byte[] buffer = Base64.decode(ret.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                obj = ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 当前String在数组中的index，不在当前数组中则返回-1
     */
    public static int getStrIndex(String str, String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            if (TextUtils.equals(str, strs[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断字符串长度是否符合要求
     */
    public static boolean confirmStrLength(String str, int min, int max) {
        try {
            if (TextUtils.isEmpty(str))
                return false;
            str = str.trim();
            int l = str.getBytes("gb2312").length;
            if (l >= min && l <= max)
                return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断字符串长度是否符合要求
     */
    public static boolean confirmStrLength2(String str, int min, int max) {
        if (TextUtils.isEmpty(str))
            return false;
        str = str.trim();
        int l = str.length();
        if (l >= min && l <= max)
            return true;
        return false;
    }

    public static int calculateLength(String etstring) {
        int varlength = 0;
        try {
            varlength = etstring.getBytes("gb2312").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return varlength;
    }


    public static boolean isDigit(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        for (char ch : str.toCharArray()) {
            if (!Character.isDigit(ch))
                return false;
        }
        return true;
    }

    /**
     * 字母、数字
     */
    public static boolean confirmInputTypeDL(String str) {
        boolean ret0 = false;
        boolean ret1 = false;
        for (char ch : str.toCharArray()) {
            if (Character.isDigit(ch)) {
                // 数字
                ret0 = true;
            } else if (Character.isLetter(ch)) {
                // 字母
                ret1 = true;
            } else {
                // 其它字符
                return false;
            }
        }
        return ret0 && ret1;
    }

    /**
     * 字母、数字、汉字
     */
    public static boolean confirmInputType(String str) {
        for (char ch : str.toCharArray()) {
            if (Character.getType(ch) == Character.OTHER_LETTER) {
                // 汉字
            } else if (Character.isDigit(ch)) {
                // 数字
            } else if (Character.isLetter(ch)) {
                // 字母
            } else if (ch == '_') {

            } else {
                // 其它字符
                return false;
            }
        }
        return true;
    }

    /**
     * 判断第一个字符是否为"_"
     */
    public static boolean inputType_(String str) {
        String s = str.substring(0, 1);
        if (s.equals("_")) {
            return false;
        }
        return true;
    }

    // 去掉所有空格
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String replaceLine(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("([\n]+)");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("\n");
        }
        return dest;
    }

    public static String replaceSpace(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("([ ]+)");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest;
    }

    /**
     * 多个空格变一个 多个换行变
     */
    public static String replaceSuc(String str) {
        str = replaceLine(str);
        str = replaceSpace(str);
        return str;
    }

    /**
     * 换行符是否存在3个
     */
    public static boolean lineNum(String text) {
        if (text == null) {
            return false;
        }
        int linenum = 0;
        boolean isline = false;
        Pattern p = Pattern.compile("([\n])");
        Matcher m = p.matcher(text);
        while (m.find()) {
            linenum++;
        }
        if (linenum > 3) {
            isline = true;
        }
        return isline;
    }

    /**
     * 截取带中文的String
     */
    public static String subStr(String str, int subSLength) {
        if (str == null)
            return "";
        else {
            try {
                int tempSubLength = subSLength;// 截取字节数
                String subStr = str.substring(0, str.length() < subSLength ? str.length() : subSLength);// 截取的子串
                int subStrByetsL = subStr.getBytes("GBK").length;// 截取子串的字节长度
                // int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度
                // 说明截取的字符串中包含有汉字
                while (subStrByetsL > tempSubLength) {
                    int subSLengthTemp = --subSLength;
                    subStr = str.substring(0,
                            subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
                    subStrByetsL = subStr.getBytes("GBK").length;
                    // subStrByetsL = subStr.getBytes().length;
                }
                return subStr;
            } catch (Exception e) {
                e.printStackTrace();
                return str;
            }
        }
    }

    /**
     * 将InputStream转换成String
     */
    private final static int BUFFER_SIZE = 4096;

    public static String InputStreamTOString(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }

    public static int getIntFromKey(Map data, String key) {
        if (data == null)
            return 0;
        return (int) (data.get(key) == null ? 0 : data.get(key));
    }

    public static String formatPhone(String input) {
        int length = input.length();
        if (length == 4) {
            if (input.substring(3).equals(new String(" "))) {
                input = input.substring(0, 3);
            } else { // +  130 2
                input = input.substring(0, 3) + " " + input.substring(3);
            }
        } else if (length == 9) {
            if (input.substring(8).equals(new String(" "))) {
                input = input.substring(0, 8);
            } else {// +
                input = input.substring(0, 8) + " " + input.substring(8);
            }
        }
        return input;
    }

    /**
     * 手机号验证
     */
    public static boolean isMobile(String str) {
        str = replaceBlank(str);
        if (str.length() != 11)
            return false;
        Pattern p = Pattern.compile("^[1][2,3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证输入密码长度 (6-16位)
     */
    public static boolean isPassLength(String str) {
        int l = TextUtils.getTrimmedLength(str);
        return l >= 8 && l <= 16;
    }

    public static boolean isCodeLength(String str) {
        int l = TextUtils.getTrimmedLength(str);
        return l == 6;
    }


    /**
     * distans>1 保留2位小数点
     */
    public static String doubleTwo(double f) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(f);
    }

    /**
     * distans>1
     */
    public static String doubleO(double f) {
        DecimalFormat df = new DecimalFormat("0");
        return df.format(f);
    }

    /**
     * 获取一位小数的double
     */
    public static double doubleOne(double f) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean isAge(String text) {
        if (!isDigit(text))
            return false;
        int a = Integer.parseInt(text);
        if (a <= 0 || a > 128)
            return false;
        return true;
    }

    // 邮箱验证
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    // 身份证号码格式化（*替代）
    public static String formagtId(String cardId) {
        if (TextUtils.isEmpty(cardId) || cardId.length() < 14)
            return cardId;
        String str1 = cardId.substring(6, 14);
        String str = cardId.replace(str1, "********");
        return str;
    }

    /**
     * 将String类型的数组转化为逗号间隔字符串形式
     */
    public static String stringArray2String(String[] stringArray) {
        if (stringArray == null || stringArray.length <= 0) {
            return null;
        }
        return Arrays.toString(stringArray).replaceAll("^\\[| |\\]$", "");
    }

    public static String removeChinese(String str) {
        if (TextUtils.isEmpty(str))
            return null;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) {
                str = str.replace(c + "", "");
            }
        }
        if (TextUtils.isEmpty(str))
            return null;
        str = doubleTwo(Double.parseDouble(str));
        return str;
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 把null 转换 ""
     *
     * @param string
     * @return
     */
    public static String toNoNullStirng(String string) {
        if (string == null) {
            return "";
        } else {
            return string;
        }
    }

    public static String getString(int id) {
        Resources res = MainApplication.getInstance().getResources();
        return res.getString(id);
    }

    public static String getCommentCountStr(int commentcount) {
        Resources res = MainApplication.getInstance().getResources();
        String str = res.getString(R.string.content_comment_count);
        return String.format(str, commentcount);
    }
}

