package com.zconly.pianocourse.util;

import android.text.TextUtils;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.MainApplication;
import com.zconly.pianocourse.mvp.service.H5Service;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/18 10:48
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 10:48
 * @UpdateRemark: 更新说明
 */
public class DataUtil {

    public static final int NULL = 0;
    public static final int MALE = 1;
    public static final int FEMALE = 2;
    public static final int OTHER = 3;

    public static String getSexString(int sex) {
        if (sex == MALE)
            return MainApplication.getInstance().getString(R.string.unit_sex_male);
        else if (sex == FEMALE)
            return MainApplication.getInstance().getString(R.string.unit_sex_female);
        else
            return MainApplication.getInstance().getString(R.string.unit_sex_other);
    }

    public static String getAvatar(String avatar){
        if (TextUtils.isEmpty(avatar) || avatar.startsWith("http"))
            return avatar;
        return H5Service.FILE_HOST + avatar;
    }

}
