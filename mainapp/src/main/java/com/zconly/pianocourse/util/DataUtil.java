package com.zconly.pianocourse.util;

import android.text.TextUtils;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.MainApplication;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.FavoriteBean;
import com.zconly.pianocourse.bean.VideoBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.mvp.service.H5Service;
import com.zconly.pianocourse.widget.dialog.DialogExerciseSetting;
import com.zconly.pianocourse.widget.dialog.DialogRecharge;

import java.util.ArrayList;
import java.util.List;

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

    public static String getImgUrl(String serverUrl) {
        if (TextUtils.isEmpty(serverUrl) || serverUrl.toLowerCase().startsWith("http"))
            return serverUrl;
        return H5Service.FILE_HOST + serverUrl;
    }

    public static String getCategoryBannerImg(int category) {
        if (category == Constants.CATEGORY_PARENTS_COURSE)
            return H5Service.IMG_COURSE_PARENT_BANNER;
        if (category == Constants.CATEGORY_TEACHER_COURSE)
            return H5Service.IMG_COURSE_TEACHER_BANNER;
        if (category == Constants.CATEGORY_OTHER_COURSE)
            return H5Service.IMG_COURSE_TRACK_BANNER;
        return null;
    }

    public static String getCategory(int category) {
        if (category == Constants.CATEGORY_PARENTS_COURSE) return "家长课";
        if (category == Constants.CATEGORY_TEACHER_COURSE) return "老师课";
        if (category == Constants.CATEGORY_OTHER_COURSE) return "曲目精讲";
        return null;
    }

    public static String getSheetLevel(int sheetLevel) {
        if (sheetLevel == 1) return "一级";
        if (sheetLevel == 2) return "二级";
        if (sheetLevel == 3) return "三级";
        if (sheetLevel == 4) return "四级";
        if (sheetLevel == 5) return "五级";
        if (sheetLevel == 6) return "六级";
        if (sheetLevel == 7) return "七级";
        if (sheetLevel == 8) return "八级";
        if (sheetLevel == 101) return "入门级";
        if (sheetLevel == 102) return "基础";
        if (sheetLevel == 103) return "初级";
        if (sheetLevel == 104) return "中级";
        if (sheetLevel == 105) return "高级";
        return "无";
    }

    public static List<VideoBean> parseFavoriteVideo(List<FavoriteBean> beans) {
        List<VideoBean> data = new ArrayList<>();
        for (FavoriteBean bean : beans) {
            VideoBean vb = bean.getLessonVideo();
            data.add(vb);
        }
        return data;
    }

    public static List<CourseBean> parseFavoriteCourse(List<FavoriteBean> beans) {
        List<CourseBean> data = new ArrayList<>();
        for (FavoriteBean bean : beans) {
            CourseBean vb = bean.getLesson();
            data.add(vb);
        }
        return data;
    }

    public static String getStaffStr(int staff) {
        switch (staff) {
            case DialogExerciseSetting.ExerciseSettingBean.HAND_DOUBLE:
                return "双手";
            case DialogExerciseSetting.ExerciseSettingBean.HAND_LEFT:
                return "左手";
            case DialogExerciseSetting.ExerciseSettingBean.HAND_RIGHT:
                return "右手";
        }
        return null;
    }

    public static List<DialogRecharge.RechargeBean> getRechargeList() {
        List<DialogRecharge.RechargeBean> list = new ArrayList<>(6);
        list.add(new DialogRecharge.RechargeBean(12, "12元"));
        list.add(new DialogRecharge.RechargeBean(30, "30元"));
        list.add(new DialogRecharge.RechargeBean(50, "50元"));
        list.add(new DialogRecharge.RechargeBean(98, "98元"));
        list.add(new DialogRecharge.RechargeBean(168, "168元"));
        list.add(new DialogRecharge.RechargeBean(268, "268元"));
        return list;
    }
}
