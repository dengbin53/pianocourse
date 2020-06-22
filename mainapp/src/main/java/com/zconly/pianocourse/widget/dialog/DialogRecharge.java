package com.zconly.pianocourse.widget.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvp.base.MvpDialog;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.constants.ExtraConstants;
import com.zconly.pianocourse.util.DataUtil;

import java.io.Serializable;

import butterknife.BindView;

/**
 * 充值
 *
 * @author DengBin
 */
public class DialogRecharge extends MvpDialog {

    @BindView(R.id.recharge_rv)
    RecyclerView rechargeRv;

    private ClickListener onClick;

    public static DialogRecharge getInstance(ClickListener onClick) {
        DialogRecharge dialog = new DialogRecharge();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExtraConstants.EXTRA_LISTENER, onClick);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected int getContentViewID() {
        return R.layout.dialog_recharge;
    }

    @Override
    protected int getStyle() {
        return R.style.dialog_sacle;
    }

    @Override
    protected void initView(Bundle bundle) {
        if (getArguments() != null) {
            onClick = (ClickListener) getArguments().getSerializable(ExtraConstants.EXTRA_LISTENER);
        }
        setGravity(Gravity.CENTER);

        GridLayoutManager lm = new GridLayoutManager(mContext, 2);
        rechargeRv.setLayoutManager(lm);
        BaseQuickAdapter<RechargeBean, BaseViewHolder> mAdapter = new BaseQuickAdapter<RechargeBean,
                BaseViewHolder>(R.layout.item_rechagre, DataUtil.getRechargeList()) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, RechargeBean item) {
                ((TextView) (helper.itemView)).setText(item.getAmountStr());
            }
        };
        rechargeRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            dismiss();
            if (onClick != null)
                onClick.onConfirm(mAdapter.getItem(position));
        });

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public static interface ClickListener extends Serializable {
        void onConfirm(RechargeBean bean);
    }

    public static class RechargeBean extends BaseBean {

        private int amount;
        private String amountStr;

        public RechargeBean(int amount, String amountStr) {
            this.amount = amount;
            this.amountStr = amountStr;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getAmountStr() {
            return amountStr;
        }

        public void setAmountStr(String amountStr) {
            this.amountStr = amountStr;
        }
    }
}
