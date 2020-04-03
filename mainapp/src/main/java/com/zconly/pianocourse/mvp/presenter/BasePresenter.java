package com.zconly.pianocourse.mvp.presenter;

import android.net.Uri;

import com.mvp.base.MvpPresenter;
import com.mvp.base.MvpView;
import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.observer.UploadObserver;
import com.mvp.utils.NetworkUtils;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.base.MainApplication;
import com.zconly.pianocourse.bean.FileBean;
import com.zconly.pianocourse.bean.result.UserResult;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.UploadView;
import com.zconly.pianocourse.mvp.view.UserView;
import com.zconly.pianocourse.util.FileUtils;
import com.zconly.pianocourse.util.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/3/23 21:56
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/23 21:56
 * @UpdateRemark: 更新说明
 */
public class BasePresenter<V extends MvpView> extends MvpPresenter<V> {

    public BasePresenter(V mView) {
        super(mView);
    }

    protected boolean isNetConnect() {
        if (!NetworkUtils.isConnect(MainApplication.getInstance())) {
            ToastUtil.toast(R.string.toast_connect_failuer);
            return false;
        }
        return true;
    }

    public void uploadImage(Uri fileUri) {
        if (!isNetConnect()) return;
        String fileStr = FileUtils.getPath(MainApplication.getInstance(), fileUri);
        File file = new File(fileStr);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);
        Observable<FileBean> ob = RetrofitUtils.create(ApiService.class).uploadFile(part);
        Observer<FileBean> observer = new UploadObserver<FileBean>() {
            @Override
            public void onProgress(int progress) {
                if (mView instanceof UploadView)
                    ((UploadView) mView).onProgress(progress);
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
            protected void onSuccess(FileBean response) {
                if (mView instanceof UploadView) {
                    mView.dismissLoading();
                    ((UploadView) mView).uploadSuccess(response);
                }
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

    public void updateUser(Map<String, String> params) {
        if (!isNetConnect()) return;
        Observable<UserResult> ob = RetrofitUtils.create(ApiService.class).updateUser(params);
        Observer<UserResult> observer = new HttpRxObserver<UserResult>() {

            @Override
            protected void onStart(Disposable d) {
                if (mView != null)
                    mView.loading("");
            }

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(UserResult response) {
                if (mView instanceof UserView) {
                    mView.dismissLoading();
                    ((UserView) mView).updateUserSuccess(response);
                }
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

    public void getUserInfo(long id) {
        if (!isNetConnect()) return;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", id <= 0 ? "" : "" + id);
        Observable<UserResult> ob = RetrofitUtils.create(ApiService.class).userInfo(params);
        Observer<UserResult> observer = new HttpRxObserver<UserResult>() {

            @Override
            protected void onStart(Disposable d) {
                if (mView != null)
                    mView.loading("");
            }

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(UserResult response) {
                if (mView instanceof UserView) {
                    mView.dismissLoading();
                    ((UserView) mView).getUserInfoSuccess(response);
                }
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

}
