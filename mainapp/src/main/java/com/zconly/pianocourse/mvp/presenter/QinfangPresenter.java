package com.zconly.pianocourse.mvp.presenter;

import android.text.TextUtils;

import com.mvp.exception.ApiException;
import com.mvp.observer.HttpRxObservable;
import com.mvp.observer.HttpRxObserver;
import com.mvp.utils.RetrofitUtils;
import com.zconly.pianocourse.activity.BookDetailActivity;
import com.zconly.pianocourse.activity.BookListActivity;
import com.zconly.pianocourse.activity.ExerciseListActivity;
import com.zconly.pianocourse.bean.BookBean;
import com.zconly.pianocourse.bean.ExerciseBean;
import com.zconly.pianocourse.bean.SheetBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.fragment.QinfangFragment;
import com.zconly.pianocourse.mvp.service.ApiService;
import com.zconly.pianocourse.mvp.view.QinfangView;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @Description: 琴房
 * @Author: dengbin
 * @CreateDate: 2020/5/2 02:24
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/2 02:24
 * @UpdateRemark: 更新说明
 */
public class QinfangPresenter extends BasePresenter<QinfangView> {

    public QinfangPresenter(QinfangView mView) {
        super(mView);
    }

    public void getBookList(int page, int id, String name, String author, String publish, int sort) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", page);
        params.put("pageSize", Constants.PAGE_COUNT);
        Map<String, Object> t = new HashMap<>();
        if (id > 0) t.put("id", id);
        if (!TextUtils.isEmpty(name)) t.put("name", name);
        if (!TextUtils.isEmpty(author)) t.put("author", author);
        if (!TextUtils.isEmpty(publish)) t.put("publish", publish);
        t.put("sort", sort);
        params.put("t", t);
        Observable<BookBean.BookListResult> o = RetrofitUtils.create(ApiService.class).getBookList(params);
        HttpRxObserver<BookBean.BookListResult> hro = new HttpRxObserver<BookBean.BookListResult>() {

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(BookBean.BookListResult response) {
                if (mView != null) {
                    mView.dismissLoading();
                    mView.getBookListSuccess(response);
                }
            }
        };
        if (mView instanceof QinfangFragment)
            HttpRxObservable.getObservableFragment(o, (QinfangFragment) mView).subscribe(hro);
        else if (mView instanceof BookListActivity)
            HttpRxObservable.getObservable(o, (BookListActivity) mView).subscribe(hro);
    }

    public void getSheetList(int page, int id, long bookId) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", page);
        params.put("pageSize", Constants.PAGE_COUNT);
        Map<String, Object> t = new HashMap<>();
        if (id > 0) t.put("id", id);
        if (bookId > 0) t.put("book_id", bookId);
        params.put("t", t);

        Observable<SheetBean.SheetListResult> o = RetrofitUtils.create(ApiService.class).getSheetList(params);
        HttpRxObserver<SheetBean.SheetListResult> hro = new HttpRxObserver<SheetBean.SheetListResult>() {

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(SheetBean.SheetListResult response) {
                if (mView != null) {
                    mView.dismissLoading();
                    mView.getSheetListSuccess(response);
                }
            }
        };
        if (mView instanceof QinfangFragment)
            HttpRxObservable.getObservableFragment(o, (QinfangFragment) mView).subscribe(hro);
        else if (mView instanceof BookDetailActivity)
            HttpRxObservable.getObservable(o, (BookDetailActivity) mView).subscribe(hro);
    }

    // 学生练习列表
    public void getExerciseList(int page, int id, long userId, long sheetId) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentPage", page);
        params.put("pageSize", Constants.PAGE_COUNT);
        Map<String, Object> t = new HashMap<>();
        if (id > 0) t.put("id", id);
        if (userId > 0) t.put("user_id", userId);
        if (sheetId > 0) t.put("sheet_id", sheetId);
        params.put("t", t);

        Observable<ExerciseBean.ExerciseListResult> o = RetrofitUtils.create(ApiService.class).getExerciseList(params);
        HttpRxObserver<ExerciseBean.ExerciseListResult> hro = new HttpRxObserver<ExerciseBean.ExerciseListResult>() {

            @Override
            protected void onError(ApiException e) {
                if (mView != null)
                    mView.onError(e);
            }

            @Override
            protected void onSuccess(ExerciseBean.ExerciseListResult response) {
                if (mView != null) {
                    mView.dismissLoading();
                    mView.getExerciseListSuccess(response);
                }
            }
        };
        if (mView instanceof QinfangFragment)
            HttpRxObservable.getObservableFragment(o, (QinfangFragment) mView).subscribe(hro);
        else if (mView instanceof ExerciseListActivity)
            HttpRxObservable.getObservable(o, (ExerciseListActivity) mView).subscribe(hro);
    }
}
