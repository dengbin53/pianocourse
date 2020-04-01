package com.zconly.pianocourse.mvp.presenter;

import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.bean.result.CourseListResult;
import com.zconly.pianocourse.mvp.view.CourseView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/30 20:26
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/30 20:26
 * @UpdateRemark: 更新说明
 */
public class CoursePresenter extends BasePresenter<CourseView> {

    public CoursePresenter(CourseView mView) {
        super(mView);
    }

    public void getCourseList(int type, int page) {

        CourseListResult result = new CourseListResult();
        List<CourseBean> data = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            CourseBean cb = new CourseBean();
            cb.setTitle("测试测试测试");
            cb.setType(i % 2);
            cb.setVideoUrl("https://media.w3.org/2010/05/sintel/trailer.mp4");
            cb.setImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585660781241&di=d7e499dc50dd9bd14ea1decafeb3b1ba&imgtype=0&src=http%3A%2F%2Fimage.xinli001.com%2F20150317%2F1732148496c98d90d07bd7.jpg");
            data.add(cb);
        }
        result.setData(data);
        mView.getCourseListSuccess(result);
//        Map<String, String> params = new HashMap<>();
//        params.put("page", page + "");
//        params.put("type", type + "");
//        Observable<CourseListResult> o = RetrofitUtils.create(ApiService.class).getCourseList(params);
//        HttpRxObservable.getObservableFragment(o, (LifecycleProvider<FragmentEvent>) mView)
//                .subscribe(new HttpRxObserver<CourseListResult>() {
//                    @Override
//                    protected void onError(ApiException e) {
//                        if (mView != null)
//                            mView.onError(e);
//                    }
//
//                    @Override
//                    protected void onSuccess(CourseListResult response) {
//                        if (mView != null)
//                            mView.getCourseListSuccess(response);
//                    }
//                });
    }

}
