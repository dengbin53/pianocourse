package com.mvp.observer;

import com.mvp.base.MvpModel;
import com.mvp.function.HttpResultFunction;
import com.mvp.function.ServerResultFunction;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 适用Retrofit网络请求Observable(被监听者)
 */
public class HttpRxObservable {

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * 无管理生命周期,容易导致内存溢出
     */
    public static <T extends MvpModel> Observable<T> getObservable(Observable<T> apiObservable) {
        return apiObservable
                .map(new ServerResultFunction<T>())
                .onErrorResumeNext(new HttpResultFunction<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider自动管理生命周期,避免内存溢出
     * 备注:需要继承RxActivity.../RxFragment...
     */
    public static <T extends MvpModel> Observable<T> getObservable(Observable<T> apiObservable,
                                                                   LifecycleProvider<ActivityEvent> lifecycle) {
        if (lifecycle == null)
            return getObservable(apiObservable);
        // 随生命周期自动管理.eg:onCreate(start)->onStop(end)
        LifecycleTransformer<T> lt = lifecycle.bindToLifecycle();
        LifecycleTransformer<T> lt2 = lifecycle.bindUntilEvent(ActivityEvent.DESTROY);
        return apiObservable
                .map(new ServerResultFunction<T>())
                .onErrorResumeNext(new HttpResultFunction<T>())
                .subscribeOn(Schedulers.io())
                .compose(lt)
                .compose(lt2) // 需要在这个位置添加
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T extends MvpModel> Observable<T> getObservableFragment(Observable<T> apiObservable,
                                                                           LifecycleProvider<FragmentEvent> lifecycle) {
        if (lifecycle == null)
            return getObservable(apiObservable);
        // 随生命周期自动管理.eg:onCreate(start)->onStop(end)
        LifecycleTransformer<T> lt = lifecycle.bindToLifecycle();
        LifecycleTransformer<T> lt2 = lifecycle.bindUntilEvent(FragmentEvent.DESTROY);
        return apiObservable
                .onErrorResumeNext(new HttpResultFunction<T>())
                .subscribeOn(Schedulers.io())
                .compose(lt)
                .compose(lt2)//需要在这个位置添加
                .observeOn(AndroidSchedulers.mainThread());
    }

}
