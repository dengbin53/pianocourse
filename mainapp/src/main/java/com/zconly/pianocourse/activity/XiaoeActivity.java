package com.zconly.pianocourse.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.xiaoe.shop.webcore.core.XEToken;
import com.xiaoe.shop.webcore.core.XiaoEWeb;
import com.xiaoe.shop.webcore.core.bridge.JsInteractType;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.bean.XiaoeTokenBean;
import com.zconly.pianocourse.mvp.presenter.XiaoePresenter;
import com.zconly.pianocourse.mvp.view.XiaoeView;
import com.zconly.pianocourse.util.Logger;
import com.zconly.pianocourse.util.SysConfigTool;

import butterknife.BindView;

/**
 * xiaoE
 */
public class XiaoeActivity extends BaseMvpActivity<XiaoePresenter> implements XiaoeView {
    public static final String TAG_DATA = "url";

    @BindView(R.id.web_content_rl)
    RelativeLayout webContentRl;

    private String url;
    private XiaoEWeb xiaoEWeb;

    public static void start(Context mContext, String url) {
        if (!SysConfigTool.isLogin(mContext, true))
            return;
        Intent intent = new Intent(mContext, XiaoeActivity.class);
        intent.putExtra("url", url);
        mContext.startActivity(intent);
    }

    private void initEvent() {
        xiaoEWeb.setJsBridgeListener((actionType, response) -> {
            switch (actionType) {
                case JsInteractType.LOGIN_ACTION:
                    //H5登录态请求 这里三方 APP 应该调用登陆接口，获取到token_key, token_value
                    //如果登录成功，通过XiaoEWeb.sync(XEToken)方法同步登录态到H5页面
                    //如果登录失败，通过XiaoEWeb.syncNot()清除登录态（注意用户需要实现自己的SDK的登录）
                    mPresenter.getXiaoeToken();
                    break;
                case JsInteractType.SHARE_ACTION:
                    //H5分享请求回调，通过 response.getResponseData() 获取分享的数据，这里三方 APP 自行分享登录方法
                    Logger.i("share:" + response.getResponseData());
                    break;
                case JsInteractType.TITLE_RECEIVE:
                    mTitleView.setTitle(response.getResponseData());
                    break;
                case JsInteractType.NOTICE_OUT_LINK_ACTION:
                    //sdk通知需要外部打开的链接回调，通过 response.getResponseData() 获取外链
                    //原来后台自定义链接:
                    //1.本来不带参数的  http://www.baidu.com
                    //2.本来带参数的  http://www.baidu.com?xxx=xxx
                    //需要sdk通知外部打开，拼装带参数needoutlink=1
                    //1.本来不带参数的 http://www.baidu.com?needoutlink=1
                    //2.本来带参数的  http://www.baidu.com?xxx=xxx&needoutlink=1
                    break;
            }
        });
    }

    @Override
    public void initData() {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        xiaoEWeb = XiaoEWeb.with(mContext)
                .setWebParent(webContentRl, lp)
                .useDefaultUI()
                .useDefaultTopIndicator(getResources().getColor(R.color.color_red))
                .buildWeb()
                .loadUrl(url);

        initEvent();
    }

    @Override
    public boolean initView() {
        url = getIntent().getStringExtra(TAG_DATA);

        if (TextUtils.isEmpty(url)) {
            finish();
            return false;
        }

        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.acttivity_xiao_e;
    }

    @Override
    protected XiaoePresenter getPresenter() {
        return new XiaoePresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (xiaoEWeb != null)
            xiaoEWeb.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (xiaoEWeb != null && xiaoEWeb.handlerKeyEvent(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xiaoEWeb != null)
            xiaoEWeb.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xiaoEWeb != null)
            xiaoEWeb.onPause();
    }

    @Override
    protected void onDestroy() {
        if (xiaoEWeb != null)
            xiaoEWeb.onDestroy();
        super.onDestroy();
    }

    @Override
    public void getXiaoeTokenSuccess(XiaoeTokenBean.XiaoeTokenResult response) {
        XiaoeTokenBean.XiaoeTokenData data = response.getDataBean();
        if (data == null || data.getData() == null)
            return;
        XiaoeTokenBean bean = data.getData();
        XEToken token = new XEToken(bean.getToken_key(), bean.getToken_value());
        if (xiaoEWeb != null) {
            xiaoEWeb.sync(token);
            xiaoEWeb.reload();
        }
    }
}
