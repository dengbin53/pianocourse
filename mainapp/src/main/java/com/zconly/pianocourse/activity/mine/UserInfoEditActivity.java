package com.zconly.pianocourse.activity.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.RequestCode;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.base.callback.MClickCallback;
import com.zconly.pianocourse.bean.FileBean;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.event.UserUpdateEvent;
import com.zconly.pianocourse.mvp.presenter.BasePresenter;
import com.zconly.pianocourse.mvp.view.EditInfoView;
import com.zconly.pianocourse.util.CropType;
import com.zconly.pianocourse.util.DataUtil;
import com.zconly.pianocourse.util.DateUtils;
import com.zconly.pianocourse.util.FileUtils;
import com.zconly.pianocourse.util.ImageUtil;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.SysConfigTool;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.widget.MKeyValueEditView;
import com.zconly.pianocourse.widget.MKeyValueView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑我的资料
 */
public class UserInfoEditActivity extends BaseMvpActivity<BasePresenter<EditInfoView>> implements EditInfoView {

    @BindView(R.id.nick_name)
    MKeyValueEditView nicknameView;
    @BindView(R.id.signature)
    MKeyValueEditView signatureView;
    @BindView(R.id.sex)
    MKeyValueView sexView;
    @BindView(R.id.piano_time)
    MKeyValueView pianoTimeView;
    @BindView(R.id.birth)
    MKeyValueView birthView;
    @BindView(R.id.piano_level)
    MKeyValueView pianoLevelView;
    @BindView(R.id.avatar)
    ImageView avatarView;

    private UserBean user;
    private int mYear;
    private int mMonth;
    private int mDay;

    private File mCurrentPhotoFile;
    private Uri cropImageUri;
    private Map<String, String> params;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserInfoEditActivity.class);
        context.startActivity(intent);
    }

    private MClickCallback clickCallback = new MClickCallback() {
        @Override
        public void callback(int type, View view) {
            switch (view.getId()) {
                case R.id.sex:
                    PopupMenu popupMenu = new PopupMenu(mContext, view, Gravity.RIGHT);
                    popupMenu.setOnMenuItemClickListener(item -> {
                        sexView.setValue(item.getTitle().toString());
                        user.setSex(item.getOrder());
                        modifyInfo("sex", user.getSex() + "");
                        return false;
                    });
                    popupMenu.inflate(R.menu.menu_gender);
                    popupMenu.show();
                    break;
                case R.id.piano_time:
                    setPianoTime();
                    break;
                case R.id.birth:
                    showDatePickerDialog();
                    break;
                case R.id.piano_level:
                    popupMenu = new PopupMenu(mContext, view, Gravity.END);
                    popupMenu.setOnMenuItemClickListener(item -> {
                        pianoLevelView.setValue(item.getTitle().toString());
                        modifyInfo("examlevel", item.getOrder() + "");
                        return false;
                    });
                    popupMenu.inflate(R.menu.menu_piano_level);
                    popupMenu.show();
                    break;
            }
        }
    };
    TextView.OnEditorActionListener nicknameListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String name = nicknameView.getValue();
                if (!TextUtils.isEmpty(name)) {
                    modifyInfo("nickname", name);
                }
            }
            return true;
        }
    };
    TextView.OnEditorActionListener signatureListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String sig = signatureView.getValue();
                if (!TextUtils.isEmpty(sig)) {
                    modifyInfo("signature", sig);
                }
            }
            return true;
        }
    };

    private void setPianoTime() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(mContext, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            ca.set(mYear, mMonth, mDay);
            long time = ca.getTimeInMillis();
            pianoTimeView.setValue(DateUtils.formatYMD(time));
            modifyInfo("duration", time + "");
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void modifyInfo(String key, String value) {
        initParams();
        params.put(key, value);
        mPresenter.updateUser(params);
    }

    private Map<String, String> initParams() {
        if (params == null) {
            params = new HashMap<>();
            params.put("uid", user.getId() + "");
            params.put("avatar", user.getAvatar() + "");
            params.put("examlevel", user.getExamlevel() + "");
            params.put("duration", user.getDuration() + "");
            params.put("nickname", user.getNickname());
            params.put("birthday", user.getBirthday() + "");
            params.put("sex", user.getSex() + "");
            params.put("signature", user.getSignature());
            params.put("role_id", user.getRole_id() + ""); // 0未知 1管理员 2学生 3老师
        }
        return params;
    }

    private void showDatePickerDialog() {
        final Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(user.getBirthday() == 0 ? DateUtils.getCurrentTime() : user.getBirthday());

        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            ca.set(mYear, mMonth, mDay);
            long time = ca.getTimeInMillis();
            user.setBirthday(time);
            birthView.setValue(DateUtils.formatYMD(time));
            modifyInfo("birthday", time + "");
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void getImageToView() {
        if (null == cropImageUri)
            return;
        // 图片显示
        ImgLoader.showAvatar(FileUtils.getPath(mContext, cropImageUri), avatarView);
        // 上传头像
        mPresenter.uploadImage(cropImageUri);
    }

    // 去截图
    private void toCropAvatar(Uri uri) {
        File avatar = FileUtils.createImageFile();
        cropImageUri = Uri.fromFile(avatar);
        ImageUtil.crop(mContext, uri, cropImageUri, CropType.ICON);
    }

    @SuppressLint("CheckResult")
    @SingleClick
    @OnClick({R.id.avatar})
    public void onClick(View view) {
        new RxPermissions(mContext).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(aBoolean -> {
            mCurrentPhotoFile = FileUtils.createImageFile();
            ImageUtil.doPickPhotoAction(mContext, mCurrentPhotoFile);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case RequestCode.RC_LOCAL_GALLERY:
                if (data == null)
                    break;
                String str = FileUtils.getPath(mContext, data.getData());
                toCropAvatar(FileUtils.getFileUri(mContext, new File(str)));
                break;
            case RequestCode.RC_CAMERA_WITH_DATA: // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                if (mCurrentPhotoFile == null) {
                    ToastUtil.toast("拍照获取图片失败");
                    break;
                }
                FileUtils.save(Uri.fromFile(mCurrentPhotoFile), mContext);
                toCropAvatar(Uri.fromFile(mCurrentPhotoFile));
                break;
            case RequestCode.RC_CROP_IMG:
                getImageToView();
                break;
        }
    }

    @Override
    protected void initData() {
        user = SysConfigTool.getUser();

        ImgLoader.showAvatar(DataUtil.getImgUrl(user.getAvatar()), avatarView);
        nicknameView.setValue(user.getNickname());
        sexView.setValue(DataUtil.getSexString(user.getSex()));
        birthView.setValue(DateUtils.formatYMD(user.getBirthday()));
        signatureView.setValue(user.getSignature());
        pianoTimeView.setValue(DateUtils.formatYMD(user.getBirthday()));
        pianoLevelView.setValue(user.getExamlevel() + "级");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_info_edit;
    }

    @Override
    protected BasePresenter<EditInfoView> getPresenter() {
        return new BasePresenter<>(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle(getString(R.string.title_user_info_edit));
        sexView.setClickCallback(clickCallback);
        pianoTimeView.setClickCallback(clickCallback);
        birthView.setClickCallback(clickCallback);
        pianoLevelView.setClickCallback(clickCallback);
        nicknameView.setActionListener(nicknameListener);
        signatureView.setActionListener(signatureListener);
        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void uploadSuccess(FileBean response) {
        modifyInfo("avatar", response.getData());
    }

    @Override
    public void updateUserSuccess(UserBean.UserResult response) {
        SysConfigTool.setUser(user = response.getData());
        EventBus.getDefault().post(new UserUpdateEvent());
    }

    @Override
    public void getUserInfoSuccess(UserBean.UserResult response) {

    }
}
