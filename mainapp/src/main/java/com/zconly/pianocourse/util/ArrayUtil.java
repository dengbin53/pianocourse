package com.zconly.pianocourse.util;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020-01-08 10:25
 * @UpdateUser: dengbin
 * @UpdateDate: 2020-01-08 10:25
 * @UpdateRemark: 更新说明
 */
public class ArrayUtil {
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    public static String[] toStringArray(List<String> strList) {
        String[] array = new String[strList.size()];
        strList.toArray(array);
        return array;
    }
}
