
package com.zconly.pianocourse.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图工具
 *
 * @author dengbin
 */
public class ViewTool {

    public static final void clipboardCopyText(Context context, CharSequence text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            cm.setText(text);
        }
    }

    /**
     * 启动软键盘
     *
     * @param editText
     */
    public static void edtFocusable(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    /**
     * 关闭软键盘
     *
     * @param editText
     */
    public static void edtFocusableHide(EditText editText) {
        editText.setHint("");
        InputMethodManager imm = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }

    /**
     * 复制到软键盘
     */
    public static void copyText(Context context, String content) {
        ClipboardManager clip = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        // 复制
        clip.setText(content);
    }

    /**
     * 输入
     */
    public static void insertToET(String str, EditText textContent) {
        // 获取光标所在位置
        int index = textContent.getSelectionStart();
        // 获取EditText的文字
        Editable edit = textContent.getEditableText();
        if (index < 0 || index >= edit.length()) {
            edit.append(str);
        } else {
            // 光标所在位置插入文字
            edit.insert(index, str);
        }
    }

    //显示密文或明文
    public static void showPass(EditText passTv, View passVisibilityView) {
        if (passVisibilityView.isSelected()) {
            //设置为密文显示
            passTv.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passVisibilityView.setSelected(false);
            passTv.setSelection(passTv.getText().length());
        } else {
            //设置为明文显示
            passTv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passVisibilityView.setSelected(true);
            passTv.setSelection(passTv.getText().length());
        }
    }

    // 限制两位小数
    public static void inPut2Decimal(EditText proPercentTv) {
        proPercentTv.setFilters(new InputFilter[]{new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - Constants.DECIMAL_DIGITS;
                    if (diff > 0) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            }
        }});
    }

    // 限制不大于100的两位小数
    public static void inPut2DecimalIn100(EditText proPercentTv) {
        proPercentTv.setFilters(new InputFilter[]{new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();

                double v = StringTool.String2Double(dValue + source.toString());
                if (v > 100) {
                    return "";
                }

                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - Constants.DECIMAL_DIGITS;
                    if (diff > 0) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            }
        }});
    }

    public static void textAddRedStar(TextView tv) {
        String str = tv.getText().toString() + "<font color='#C70000'> * </font>";
        tv.setText(Html.fromHtml(str));
    }

    /**
     * 去除TextView 上下左右添加的Drawable 图片
     *
     * @param views
     */
    public static void cleanTextViewDrawablesImageView(View[] views) {
        for (View view :
                views) {
            if (view instanceof TextView) {
                ((TextView) view).setCompoundDrawables(null, null, null, null);
            } else if (view instanceof EditText) {
                ((EditText) view).setCompoundDrawables(null, null, null, null);
            }
        }
    }

    public static void setSelection(EditText[] ets) {
        for (EditText et : ets)
            et.setSelection(et.getText().length());
    }

    /**
     * @note 获取该activity所有view
     * @author liuh
     */
    public static List<TextView> getAllChildTextViewFormActivity(Context context) {
        View view = ((Activity) context).getWindow().getDecorView();
        return getAllChildViews(view);
    }

    private static List<TextView> getAllChildViews(View view) {
        List<TextView> allchildren = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if (viewchild instanceof TextView) {
                    allchildren.add((TextView) viewchild);

                }
                allchildren.addAll(getAllChildViews(viewchild));

            }
        }
        return allchildren;
    }

    public static TextView getTagTv(Context mContext, String tag) {
        TextView tv = new TextView(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = 24;
        tv.setLayoutParams(lp);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        // tv.setPadding(0, 0, 4, 0);
        // tv.setBackgroundResource(R.drawable.shape_tag_bg);
        tv.setTextColor(mContext.getResources().getColor(R.color.color_text_gray));
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setText(tag);
        // tv.setSingleLine();
        return tv;
    }

}

