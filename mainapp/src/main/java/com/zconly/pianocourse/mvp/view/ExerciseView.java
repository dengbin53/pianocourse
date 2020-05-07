package com.zconly.pianocourse.mvp.view;

import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.EvaluateBean;
import com.zconly.pianocourse.bean.ExerciseBean;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/5/6 12:11
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/6 12:11
 * @UpdateRemark: 更新说明
 */
public interface ExerciseView extends DownloadView, UploadView {

    void uploadExerciseSuccess(ExerciseBean.ExerciseResult response);

    void favoriteSuccess(BaseBean response);

    void getEvaluateListSuccess(EvaluateBean.EvaluateListResult response);
}
