package com.zconly.pianocourse.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.CourseDetailActivity;
import com.zconly.pianocourse.activity.XiaoeActivity;
import com.zconly.pianocourse.adapter.viewholder.CommonBannerHolder;
import com.zconly.pianocourse.base.MainApplication;
import com.zconly.pianocourse.bean.BannerBean;
import com.zconly.pianocourse.bean.BaseBean;
import com.zconly.pianocourse.bean.CourseBean;
import com.zconly.pianocourse.constants.Constants;
import com.zconly.pianocourse.widget.dialog.DialogObtainCoupon;

import java.util.ArrayList;
import java.util.List;

public class ViewUtil {

    public static final void clearClipboard(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            try {
                cm.setPrimaryClip(cm.getPrimaryClip());
                cm.setPrimaryClip(ClipData.newPlainText("", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public static void setTextPaint(TextPaint ds) {
        ds.setColor(MainApplication.getInstance().getResources().getColor(R.color.color_red));
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }

    // ConvenientBanner
    public static <T extends BaseBean> void updateBanner(ConvenientBanner<T> bannerView, List<T> bannerData) {
        updateBanner(bannerView, bannerData, new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new CommonBannerHolder<T>(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_pager_image_large;
            }

        });
    }

    public static <T extends BaseBean> void updateBanner(ConvenientBanner<T> bannerView, List<T> bannerData,
                                                         CBViewHolderCreator creator) {
        if (bannerView == null)
            return;
        if (ArrayUtil.isEmpty(bannerData)) {
            bannerView.setVisibility(View.GONE);
            return;
        }
        bannerView.setVisibility(View.VISIBLE);
        bannerView.setPages(creator, bannerData)
                .setPageIndicator(new int[]{R.drawable.shape_circle_white_30_4, R.drawable.shape_circle_white_4dp})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .startTurning(3000)
                .setOnItemClickListener(position -> {

                    T data = bannerData.get(position);

                    if (data instanceof BannerBean) {
                        BannerBean bean = (BannerBean) data;
                        if (bean.getType() == Constants.TYPE_BANNER_COURSE) {
                            CourseDetailActivity.start(bannerView.getContext(), new CourseBean(bean.getLesson_id()));
                        } else {
                            XiaoeActivity.start(bannerView.getContext(), bean.getUrl());
                        }
                    }
                });
    }

    public static void setSelection(EditText... ets) {
        if (ets == null)
            return;
        for (EditText et : ets)
            et.setSelection(et.getText().length());
    }

    public static int getBannerHeight(int width, float type) {
        return (int) (width / type);
    }

    // 取消RecyclerView Animation
    public static void cancelRecyclerViewAnim(RecyclerView recyclerView) {
        DefaultItemAnimator animator = ((DefaultItemAnimator) recyclerView.getItemAnimator());
        if (animator != null)
            animator.setSupportsChangeAnimations(false);
    }

    // 解决item错乱的问题
    public static void parseStaggeredGrid(RecyclerView recyclerView) {
        if (!(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager))
            return;
        StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        lm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView.invalidateItemDecorations();
                }
            }
        });
    }

    public static void couponDialog(FragmentActivity context, String str) {
        if (context == null || TextUtils.isEmpty(str))
            return;
        // 【1元通用优惠券】，复制这句话¥PHB2BJZY¥后打开👉着调👈
        str = str.trim();
        if (!str.contains("\uD83D\uDC49着调\uD83D\uDC48"))
            return;
        String name = str.substring(str.indexOf("【") + 1, str.lastIndexOf("】"));
        String code = str.substring(str.indexOf("¥") + 1, str.lastIndexOf("¥"));
        DialogObtainCoupon dialog = DialogObtainCoupon.getInstance(name, code);
        dialog.show(context.getSupportFragmentManager(), null);
    }

}
