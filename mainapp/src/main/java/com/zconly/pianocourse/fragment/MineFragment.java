package com.zconly.pianocourse.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvp.base.MvpPresenter;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.activity.FavoriteActivity;
import com.zconly.pianocourse.activity.NoticeActivity;
import com.zconly.pianocourse.activity.SignInActivity;
import com.zconly.pianocourse.activity.mine.AccountActivity;
import com.zconly.pianocourse.activity.mine.BoughtActivity;
import com.zconly.pianocourse.activity.mine.FeedbackActivity;
import com.zconly.pianocourse.activity.mine.SettingActivity;
import com.zconly.pianocourse.activity.mine.UserInfoEditActivity;
import com.zconly.pianocourse.base.BaseMvpFragment;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.event.LogoutEvent;
import com.zconly.pianocourse.event.SignInEvent;
import com.zconly.pianocourse.event.UserUpdateEvent;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.SysConfigTool;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 我的
 * @Author: dengbin
 * @CreateDate: 2020/3/18 21:03
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/3/18 21:03
 * @UpdateRemark: 更新说明
 */
public class MineFragment extends BaseMvpFragment {

    @BindView(R.id.view_fra_mine_avatar)
    ImageView avatarView;
    @BindView(R.id.view_fra_mine_name)
    TextView nameTv;
    @BindView(R.id.view_fra_mine_signature)
    TextView signatureTv;

    @BindView(R.id.login_rl)
    View loginRl;

    private UserBean user;

    @OnClick({R.id.mine_user_rv, R.id.favorite, R.id.feedback, R.id.setting, R.id.notice_kv, R.id.empty_login_tv,
            R.id.account, R.id.account_bought_kvv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_user_rv:
                UserInfoEditActivity.start(mContext);
                break;
            case R.id.favorite: // 收藏
                FavoriteActivity.start(mContext, 0);
                break;
            case R.id.notice_kv: // 通知
                NoticeActivity.start(mContext);
                break;
            case R.id.feedback:
                FeedbackActivity.start(mContext);
                break;
            case R.id.setting:
                SettingActivity.start(mContext);
                break;
            case R.id.empty_login_tv:
                SignInActivity.start(mContext);
                break;
            case R.id.account: // 账户
                AccountActivity.start(mContext);
                break;
            case R.id.account_bought_kvv: // 已购
                BoughtActivity.start(mContext);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        user = SysConfigTool.getUser();
        if (user == null) {
            loginRl.setVisibility(View.VISIBLE);
        } else {
            loginRl.setVisibility(View.GONE);
            ImgLoader.showAvatar(this, DataUtil.getImgUrl(user.getAvatar()), avatarView);
            nameTv.setText(user.getNickname());
            signatureTv.setText(user.getSignature());
        }
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MvpPresenter getPresenter() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserUpdateEvent event) {
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SignInEvent event) {
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LogoutEvent event) {
        initData();
    }

}
