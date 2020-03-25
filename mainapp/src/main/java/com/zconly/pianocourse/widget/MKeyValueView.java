package com.zconly.pianocourse.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.callback.MClickCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * key-value
 * Created by dengbin on 17/4/17.
 */
public class MKeyValueView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.item_name_value_name)
    TextView nameTv;
    @BindView(R.id.item_name_value_value)
    TextView valueTv;
    @BindView(R.id.item_name_value_iv)
    ImageView arrowIv;

    private boolean arrowVisibility;
    private String name;
    private String value;
    private String hint;
    private MClickCallback clickCallback;

    public MKeyValueView(Context context) {
        super(context);
    }

    public MKeyValueView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public void setClickCallback(MClickCallback clickCallback) {
        this.clickCallback = clickCallback;
        valueTv.setOnClickListener(this);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.common_attrs);
        arrowVisibility = mTypedArray.getBoolean(R.styleable.common_attrs_arrow, true);
        name = mTypedArray.getString(R.styleable.common_attrs_name);
        value = mTypedArray.getString(R.styleable.common_attrs_value);
        hint = mTypedArray.getString(R.styleable.common_attrs_hint);
        int valueColor = mTypedArray.getColor(R.styleable.common_attrs_valueColor,
                context.getResources().getColor(R.color.color_text_black));
        int keyColor = mTypedArray.getColor(R.styleable.common_attrs_keyColor,
                context.getResources().getColor(R.color.color_text_black));

        setOrientation(HORIZONTAL);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.space_large);
        setPadding(padding, 0, padding, 0);

        View.inflate(context, R.layout.item_key_value, this);

        ButterKnife.bind(this, this);

        setArrow(arrowVisibility);
        valueTv.setTextColor(valueColor);
        nameTv.setTextColor(keyColor);

        if (!TextUtils.isEmpty(name))
            setName(name);

        if (!TextUtils.isEmpty(value))
            setValue(value);

        if (!TextUtils.isEmpty(hint))
            setValueHint(hint);

    }

    private void setValueHint(String hint) {
        valueTv.setHint(hint);
    }

    public void setName(String name) {
        nameTv.setText(name);
    }

    public void setValue(String value) {
        valueTv.setText(value);
    }

    public String getValue() {
        return valueTv.getText().toString();
    }

    public void setArrow(boolean arrow) {
        arrowIv.setVisibility(arrow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_name_value_value:
                if (clickCallback != null)
                    clickCallback.callback(MClickCallback.CLICK_SINGLE, this);
                break;
        }
    }
}
