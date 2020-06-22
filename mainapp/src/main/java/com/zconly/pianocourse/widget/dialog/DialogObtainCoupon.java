package com.zconly.pianocourse.widget.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mvp.base.MvpDialog;
import com.mvp.exception.ApiException;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CouponBean;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.mvp.presenter.CouponPresenter;
import com.zconly.pianocourse.mvp.view.CouponView;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.util.ViewUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 领取优惠券
 * @Author: dengbin
 * @CreateDate: 2020/6/22 13:07
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/6/22 13:07
 * @UpdateRemark: 更新说明
 */
public class DialogObtainCoupon extends MvpDialog implements CouponView {

    @BindView(R.id.obtain_coupon_name_tv)
    TextView nameTv;
    @BindView(R.id.obtain_coupon_code_tv)
    TextView codeTv;

    private String name;
    private String code;

    public static DialogObtainCoupon getInstance(String name, String code) {
        DialogObtainCoupon dialog = new DialogObtainCoupon();
        Bundle bundle = new Bundle();
        bundle.putString(ExtraConstants.EXTRA_DATA, name);
        bundle.putString(ExtraConstants.EXTRA_KEY_WORDS, code);
        dialog.setArguments(bundle);
        return dialog;
    }

    @OnClick({R.id.obtain_coupon_cancel_iv, R.id.obtain_coupon_get_tv})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.obtain_coupon_cancel_iv:
                dismiss();
                break;
            case R.id.obtain_coupon_get_tv:
                CouponPresenter presenter = new CouponPresenter(this);
                presenter.obtainCoupon(code);
                break;
        }
    }

    @Override
    protected int getContentViewID() {
        return R.layout.dialog_obtain_coupon;
    }

    @Override
    protected int getStyle() {
        return R.style.dialog_sacle;
    }

    @Override
    protected void initView(Bundle bundle) {
        if (getArguments() != null) {
            name = getArguments().getString(ExtraConstants.EXTRA_DATA);
            code = getArguments().getString(ExtraConstants.EXTRA_KEY_WORDS);
        }

        setGravity(Gravity.CENTER);

        nameTv.setText(name);
        codeTv.setText(code);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void obtainCouponSuccess(BaseBean response) {
        ToastUtil.toast("领取成功");
        ViewUtil.clearClipboard(mContext);
        dismiss();
    }

    @Override
    public void onError(ApiException errorBean) {
        super.onError(errorBean);
        ToastUtil.toast(errorBean.getMsg());
        ViewUtil.clearClipboard(mContext);
        dismiss();
    }

    @Override
    public void getCouponListSuccess(CouponBean.CouponListResult result) {

    }

    @Override
    public void getBannerListSuccess(BannerBean.BannerListResult response) {

    }

}
