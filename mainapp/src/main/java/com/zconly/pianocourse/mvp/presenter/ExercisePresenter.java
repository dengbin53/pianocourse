package com.zconly.pianocourse.mvp.presenter;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.observer.UploadObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.ExerciseView;
import com.zconly.pianocourse.mvp.view.UploadView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Description: 练琴
 * @Author: dengbin
 * @CreateDate: 2020/5/2 02:24
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/2 02:24
 * @UpdateRemark: 更新说明
 */
public class ExercisePresenter extends BasePresenter<ExerciseView> {

    public ExercisePresenter(ExerciseView mView) {
        super(mView);
    }

    public void uploadExercise(File file, long sheetId, int bpm, int staff, int mode, int duration) {
        if (!isNetConnect()) return;

        RequestBody fileRQ = RequestBody.create(MediaType.parse("audio/mpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);

        RequestBody body = new MultipartBody.Builder()
                //.addPart(part)
                .addFormDataPart("sheet_id", sheetId + "")
                .addFormDataPart("bpm", bpm + "")
                .addFormDataPart("staff", staff + "")
                .addFormDataPart("mode", mode + "")
                .addFormDataPart("duration", duration + "")
                .addFormDataPart("file", file.getName(), fileRQ)
                .setType(MultipartBody.FORM)
                .build();
        Observable<ExerciseBean.ExerciseResult> ob = RetrofitUtils.create(ApiService.class).uploadExercise(body);
        Observer<ExerciseBean.ExerciseResult> observer = new UploadObserver<ExerciseBean.ExerciseResult>() {
            @Override
            public void onProgress(int progress) {
                if (mView instanceof UploadView)
                    mView.onProgress(progress);
            }

            @Override
            protected void onStart(Disposable d) {
                if (mView != null)
                    mView.loading("上传中");
            }

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(ExerciseBean.ExerciseResult response) {
                mView.dismissLoading();
                mView.uploadExerciseSuccess(response);
            }
        };

        if (mView instanceof BaseMvpActivity) {
            HttpRxObservable.getObservable(ob, (BaseMvpActivity) mView).subscribe(observer);
        } else if (mView instanceof BaseMvpFragment) {
            HttpRxObservable.getObservableFragment(ob, (BaseMvpFragment) mView).subscribe(observer);
        } else {
            HttpRxObservable.getObservable(ob).subscribe(observer);
        }
    }

    public void favoriteSheet(long sheetId) {
        Observable<BaseBean> o = RetrofitUtils.create(ApiService.class).favoriteSheet(sheetId);
        HttpRxObservable.getObservable(o).subscribe(new HttpRxObserver<BaseBean>() {

            @Override
            protected void onStart(Disposable d) {
                mView.loading("");
            }

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(BaseBean response) {
                mView.dismissLoading();
                mView.favoriteSuccess(response);
            }
        });
    }

    public void addSheetWatch(long sheetId) {
        Observable<BaseBean> o = RetrofitUtils.create(ApiService.class).addSheetWatch(sheetId);
        HttpRxObservable.getObservable(o).subscribe(new HttpRxObserver<BaseBean>() {

            @Override
            protected void onError(ApiException e) {
            }

            @Override
            protected void onSuccess(BaseBean response) {
            }
        });
    }

}
