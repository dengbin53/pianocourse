package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.widget.ContentLoadingProgressBar;

import com.mvp.base.MvpPresenter;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.widget.TitleView;

import butterknife.BindView;

/**
 * webView
 */
public class WebViewActivity extends BaseMvpActivity {
    public static final String TAG_DATA = "url";

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.loading)
    ContentLoadingProgressBar progressBar;

    private String title;
    private String url;

    private final String mimeType = "text/html";
    private final String encoding = "utf-8";

    public static void start(Context mContext, String title, String url) {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        mContext.startActivity(intent);
    }

    @Override
    public void initData() {
        // StringBuffer strUrl = new StringBuffer(ApiAddress.PUBLIC_WELFARE);
        // strUrl.append(content.getPwProjId());
        webView.loadUrl(url);
    }

    @Override
    protected int getContentView() {
        return R.layout.act_web_view;
    }

    @Override
    protected MvpPresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra(TAG_DATA);

        if (TextUtils.isEmpty(url)) {
            finish();
            return;
        }

        ((TitleView) mTitleView).setTitle(title);

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.color_green),
                PorterDuff.Mode.MULTIPLY);

        WebSettings ws = webView.getSettings();

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         *
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN : 适应屏幕，内容将自动缩放
         */
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 是否支持ViewPort的meta tag属性，如果页面有ViewPort meta tag 指定的宽度，则使用meta tag指定的值，否则默认使用宽屏的视图窗口
        ws.setUseWideViewPort(true);
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(true);
        // 适应屏幕大小
        // 是否启动概述模式浏览界面，当页面宽度超过WebView显示宽度时，缩小页面适应WebView
        ws.setLoadWithOverviewMode(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(false);
        ws.setDefaultTextEncodingName(encoding);
        // ws.setMinimumFontSize(42);
        // ws.setDefaultFontSize(42);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.hide();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    public static Intent getIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        return intent;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }
}
