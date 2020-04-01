package com.zconly.pianocourse.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.callback.CommonCallback;
import com.zconly.pianocourse.util.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description: 页面头部视图
 * @Author: dengbin
 * @CreateDate: 2020-01-06 11:10
 * @UpdateUser: dengbin
 * @UpdateDate: 2020-01-06 11:10
 * @UpdateRemark: 更新说明
 */
public class TitleView extends RelativeLayout {

    public static final int LEFT_IV = 0x0;
    public static final int LEFT_TV = 0x1;
    public static final int RIGHT_IV0 = 0x2;
    public static final int RIGHT_IV1 = 0x3;
    public static final int RIGHT_TV = 0x4;
    public static final int CENTER_TV = 0x5;

    @BindView(R.id.title_left_iv)
    ImageView leftIv;
    @BindView(R.id.title_left_tv)
    TextView leftTv;
    @BindView(R.id.title_right_iv_0)
    ImageView rightIv0;
    @BindView(R.id.title_right_iv_1)
    ImageView rightIv1;
    @BindView(R.id.title_right_tv)
    TextView rightTv;
    @BindView(R.id.title_center_tv)
    TextView centerTv;

    @BindView(R.id.title_center_ll)
    LinearLayout centerLl;

    @BindView(R.id.title_message_point_view)
    View msgPointView;
    @BindView(R.id.title_message_tv)
    TextView msgTv;

    private final Context mContext;
    private CommonCallback mCallback;
    private String titleStr;
    private int titleColor;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.common_attrs);
        titleStr = typedArray.getString(R.styleable.common_attrs_title_text);
        titleColor = typedArray.getColor(R.styleable.common_attrs_title_color, 0);
        typedArray.recycle();

        initView(context);
    }

    private void setCenterTvPadding() {
        int leftPadding = 0;
        int rightPadding = 0;
        if (leftTv.getVisibility() == VISIBLE)
            leftPadding += DeviceUtils.dp2px(40f);
        if (leftIv.getVisibility() == VISIBLE)
            leftPadding += DeviceUtils.dp2px(40f);
        if (rightIv0.getVisibility() == VISIBLE)
            rightPadding += DeviceUtils.dp2px(40f);
        if (rightIv1.getVisibility() == VISIBLE)
            rightPadding += DeviceUtils.dp2px(40f);
        if (rightTv.getVisibility() == VISIBLE)
            rightPadding += DeviceUtils.dp2px(40f);

        centerTv.setPadding(leftPadding, 0, rightPadding, 0);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_title, this);
        ButterKnife.bind(this, this);

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(MarginLayoutParams.MATCH_PARENT,
                MarginLayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.title_height));

        setBackgroundResource(R.drawable.shape_white_border_bottom);

        setTitle(titleStr);
        setTitleColor(titleColor);
    }

    @OnClick({R.id.title_left_iv, R.id.title_left_tv, R.id.title_right_iv_0, R.id.title_right_iv_1,
            R.id.title_right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_iv:
                if (mContext instanceof Activity)
                    ((Activity) mContext).onBackPressed();
                if (mCallback != null)
                    mCallback.callback(LEFT_IV);
                break;
            case R.id.title_left_tv:
                if (mCallback != null)
                    mCallback.callback(LEFT_TV);
                break;
            case R.id.title_right_iv_0:
                if (mCallback != null)
                    mCallback.callback(RIGHT_IV0);
                break;
            case R.id.title_right_iv_1:
                if (mCallback != null)
                    mCallback.callback(RIGHT_IV1);
                break;
            case R.id.title_right_tv:
                if (mCallback != null)
                    mCallback.callback(RIGHT_TV);
                break;
            case R.id.title_center_tv:
                if (mCallback != null)
                    mCallback.callback(CENTER_TV);
                break;
            default:
                break;
        }
    }

    public TitleView setTitle(String text) {
        this.titleStr = text;
        centerTv.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
        centerLl.setVisibility(centerTv.getVisibility() == GONE ? VISIBLE : GONE);
        centerTv.setText(titleStr);
        return this;
    }

    public TitleView setTitleColor(int color) {
        this.titleColor = color;
        if (color > 0) {
            centerTv.setTextColor(titleColor);
        }
        return this;
    }

    public TitleView setLeftTvImg(int res) {
        leftTv.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
        leftTv.setVisibility(res > 0 ? VISIBLE : GONE);
        setCenterTvPadding();
        return this;
    }

    public TitleView setLeftTv(String text) {
        leftTv.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
        leftTv.setText(text);
        setCenterTvPadding();
        return this;
    }

    public TitleView setLeftIv(int res) {
        leftIv.setVisibility(res <= 0 ? GONE : VISIBLE);
        leftIv.setImageResource(res);
        setCenterTvPadding();
        return this;
    }

    public TitleView setRightIv0(int res) {
        rightIv0.setVisibility(res <= 0 ? GONE : VISIBLE);
        rightIv0.setImageResource(res);
        return this;
    }

    public TitleView setRightIv1(int res) {
        rightIv1.setVisibility(res <= 0 ? GONE : VISIBLE);
        rightIv1.setImageResource(res);
        setCenterTvPadding();
        return this;
    }

    public TitleView setRightTv(String text) {
        rightTv.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
        rightTv.setText(text);
        setCenterTvPadding();
        return this;
    }

    // 设置点击回调
    public TitleView setCallback(CommonCallback mCallback) {
        this.mCallback = mCallback;
        return this;
    }

    // title 自定义 center 视图
    public TitleView setCenterView(View view) {
        if (view == null) {
            centerLl.setVisibility(GONE);
            return this;
        }
        centerTv.setVisibility(GONE);
        centerLl.setVisibility(VISIBLE);
        centerLl.addView(view);
        return this;
    }

    public TitleView setRightTvColor(int color) {
        rightTv.setTextColor(color);
        return this;
    }

    public TitleView setLeftTvColor(int color) {
        leftTv.setTextColor(color);
        return this;
    }

    public TitleView setMsgPoint(boolean show) {
        if (show) {
            rightIv0.setVisibility(VISIBLE);
            msgPointView.setVisibility(VISIBLE);
            msgTv.setVisibility(GONE);
        } else {
            msgPointView.setVisibility(GONE);
        }
        return this;
    }

    public TitleView setMsgTv(String text) {
        if (TextUtils.isEmpty(text)) {
            msgTv.setVisibility(GONE);
        } else {
            rightIv0.setVisibility(VISIBLE);
            msgPointView.setVisibility(GONE);
            msgTv.setText(text);
            msgTv.setVisibility(VISIBLE);
        }
        return this;
    }

    public ImageView getRightIv0() {
        return rightIv0;
    }

    public ImageView getRightIv1() {
        return rightIv1;
    }

}
