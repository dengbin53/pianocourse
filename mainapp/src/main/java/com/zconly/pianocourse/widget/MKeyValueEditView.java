package com.zconly.pianocourse.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.callback.MTextWatcher;
import com.zconly.pianocourse.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * name-value
 * Created by dengbin on 17/4/17.
 */
public class MKeyValueEditView extends LinearLayout {

    @BindView(R.id.item_name_value_name)
    TextView nameTv;
    @BindView(R.id.item_name_value_value)
    EditText valueTv;
    @BindView(R.id.item_name_value_iv)
    ImageView arrowIv;

    private boolean arrowVisibility;
    private String name;
    private String value;
    private String hint;
    private int inputType;
    private int imeOptions;

    public MKeyValueEditView(Context context) {
        super(context);
    }

    public MKeyValueEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.common_attrs);
        arrowVisibility = mTypedArray.getBoolean(R.styleable.common_attrs_arrow, true);
        name = mTypedArray.getString(R.styleable.common_attrs_name);
        value = mTypedArray.getString(R.styleable.common_attrs_value);
        hint = mTypedArray.getString(R.styleable.common_attrs_hint);
        inputType = mTypedArray.getInt(R.styleable.common_attrs_inputType, 0);
        imeOptions = mTypedArray.getInt(R.styleable.common_attrs_imeOptions, -1);
        int keyColor = mTypedArray.getColor(R.styleable.common_attrs_keyColor,
                context.getResources().getColor(R.color.color_text_black));
        int valueColor = mTypedArray.getColor(R.styleable.common_attrs_valueColor,
                context.getResources().getColor(R.color.color_text_black));

        setOrientation(HORIZONTAL);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.space_large);
        setPadding(padding, 0, padding, 0);

        View.inflate(context, R.layout.item_key_value_edit, this);

        ButterKnife.bind(this, this);

        setArrow(arrowVisibility);

        if (!TextUtils.isEmpty(name))
            setName(name);

        if (!TextUtils.isEmpty(value))
            setValue(value);

        if (!TextUtils.isEmpty(hint))
            setValueHint(hint);

        nameTv.setTextColor(keyColor);
        valueTv.setTextColor(valueColor);

        valueTv.setInputType(inputType == 1 ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);

        if (imeOptions != -1) {
            valueTv.setImeOptions(imeOptions == 0 ? EditorInfo.IME_ACTION_NEXT : EditorInfo.IME_ACTION_DONE);
        }
    }


    public void setValueHint(String hint) {
        valueTv.setHint(hint);
    }

    public void setName(String name) {
        nameTv.setText(name);
    }

    public void setValue(String value) {
        valueTv.setText(value);
    }

    public void setActionListener(TextView.OnEditorActionListener value) {
        valueTv.setOnEditorActionListener(value);
    }

    public String getValue() {
        return valueTv.getText().toString();
    }

    public void setArrow(boolean arrow) {
        arrowIv.setVisibility(arrow ? View.VISIBLE : View.GONE);
    }

    public void setTextWatcher(MTextWatcher textWatcher) {
        this.valueTv.addTextChangedListener(textWatcher);
    }

    public void focus() {
        ViewUtil.edtFocusable(valueTv);
    }
}
