package com.zconly.pianocourse.mvp.view;

import com.zconly.pianocourse.bean.BookBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.SheetBean;

/**
 * @Description: 琴房
 * @Author: dengbin
 * @CreateDate: 2020/5/2 02:24
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/2 02:24
 * @UpdateRemark: 更新说明
 */
public interface QinfangView extends BaseView {

    void getBookListSuccess(BookBean.BookListResult response);

    void getSheetListSuccess(SheetBean.SheetListResult response);

    void getExerciseListSuccess(ExerciseBean.ExerciseListResult response);
}
