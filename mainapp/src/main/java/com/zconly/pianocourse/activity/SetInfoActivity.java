package com.zconly.pianocourse.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.BaseMvpActivity;
import com.zconly.pianocourse.base.Constants;
import com.zconly.pianocourse.base.RequestCode;
import com.zconly.pianocourse.base.SingleClick;
import com.zconly.pianocourse.base.callback.MClickCallback;
import com.zconly.pianocourse.bean.FileBean;
import com.zconly.pianocourse.bean.UserBean;
import com.zconly.pianocourse.bean.UserDataBean;
import com.zconly.pianocourse.event.SignInEvent;
import com.zconly.pianocourse.mvp.presenter.SetInfoPresenter;
import com.zconly.pianocourse.mvp.view.SetInfoView;
import com.zconly.pianocourse.util.ActionUtil;
import com.zconly.pianocourse.util.CropType;
import com.zconly.pianocourse.util.DateTool;
import com.zconly.pianocourse.util.FileUtils;
import com.zconly.pianocourse.util.ImageUtil;
import com.zconly.pianocourse.util.ImgLoader;
import com.zconly.pianocourse.util.Logger;
import com.zconly.pianocourse.util.SysConfigTool;
import com.zconly.pianocourse.util.ToastUtil;
import com.zconly.pianocourse.widget.MKeyValueEditView;
import com.zconly.pianocourse.widget.MKeyValueView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 完善资料
 * Created by dengbin
 */
public class SetInfoActivity extends BaseMvpActivity<SetInfoPresenter> implements SetInfoView {

    @BindView(R.id.nickname)
    MKeyValueEditView nicknameView;
    @BindView(R.id.sex)
    MKeyValueView sexView;
    @BindView(R.id.birth)
    MKeyValueView birthView;
    @BindView(R.id.signature)
    MKeyValueEditView signatureView;
    @BindView(R.id.piano_time)
    MKeyValueView pianoTimeView;
    @BindView(R.id.piano_level)
    MKeyValueView pianoLevelView;
    @BindView(R.id.avatar)
    ImageView avatarView;

    private int mYear;
    private int mMonth;
    private int mDay;

    private File mCurrentPhotoFile;
    private Uri cropedImageUri;

    private String mobile;
    private String code;
    private String pass;
    private int sex;
    private int examLevel;
    private long pianoTime;

    private MClickCallback clickCallback = new MClickCallback() {
        @Override
        public void callback(int type, View view) {
            switch (view.getId()) {
                case R.id.sex:
                    PopupMenu popupMenu = new PopupMenu(mContext, view, Gravity.RIGHT);
                    popupMenu.setOnMenuItemClickListener(item -> {
                        sexView.setValue(item.getTitle().toString());
                        sex = item.getOrder();
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
                        examLevel = item.getOrder();
                        return false;
                    });
                    popupMenu.inflate(R.menu.menu_piano_level);
                    popupMenu.show();
                    break;
            }
        }
    };

    private Map<String, String> params;

    private void signUp() {
        String nickname = nicknameView.getValue();
        if (TextUtils.isEmpty(nickname.trim())) {
            ToastUtil.toast(getString(R.string.toast_no_name));
            return;
        }

        params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", pass);
        params.put("examlevel", examLevel + "");
        params.put("duration", pianoTime + "");
        params.put("nickname", nickname);
        params.put("birthday", pianoTime + "");
        params.put("sex", sex + "");
        params.put("signature", signatureView.getValue());
        params.put("code", code + "");
        params.put("role_id", Constants.TYPE_ROLE_STUDENT + ""); // 0未知 1管理员 2学生 3老师

        mPresenter.completion(params);
    }

    private void uploadAvatar() {
        if (cropedImageUri != null) {
            Logger.i("uploadImage");
            mPresenter.uploadImage(cropedImageUri);
        } else {
            finishWork();
        }
    }

    private void finishWork() {
        Logger.i("finishWork");
        EventBus.getDefault().post(new SignInEvent());
        finish();
    }

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
            pianoTimeView.setValue(DateTool.formatYMD(time));
            pianoTime = time;
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void showDatePickerDialog() {
        final Calendar ca = Calendar.getInstance();
        ca.add(Calendar.YEAR, -30);

        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(mContext, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            ca.set(mYear, mMonth, mDay);
            long time = ca.getTimeInMillis();
            birthView.setValue(DateTool.formatYMD(time));
            pianoTime = time;
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void getImageToView() {
        if (null == cropedImageUri)
            return;
        ImgLoader.showAvatar(FileUtils.getPath(mContext, cropedImageUri), avatarView);
    }

    // 去截图
    private void toCropAvatar(Uri uri) {
        cropedImageUri = Uri.fromFile(FileUtils.createImageFile());
        ImageUtil.crop(mContext, uri, cropedImageUri, CropType.ICON);
    }

    @SingleClick
    @OnClick({R.id.finish, R.id.help, R.id.avatar})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar:
                new RxPermissions(mContext).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(aBoolean -> {
                    mCurrentPhotoFile = FileUtils.createImageFile();
                    ImageUtil.doPickPhotoAction(mContext, mCurrentPhotoFile);
                });
                break;
            case R.id.finish:
                signUp();
                break;
            case R.id.help:
                ActionUtil.startAct(mContext, ContactCSActivity.class);
                break;
        }
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
            case RequestCode.RC_SIGN_IN:
                setResult(resultCode);
                finish();
                break;
        }
    }

    @Override
    protected void initData() {
        mobile = getIntent().getStringExtra("emailOrPhone");
        code = getIntent().getStringExtra("code");
        pass = getIntent().getStringExtra("pass");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_set_info;
    }

    @Override
    protected SetInfoPresenter getPresenter() {
        return new SetInfoPresenter(this);
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected boolean initView() {
        mTitleView.setTitle(getString(R.string.title_set_info));

        sexView.setClickCallback(clickCallback);
        birthView.setClickCallback(clickCallback);
        pianoLevelView.setClickCallback(clickCallback);
        pianoTimeView.setClickCallback(clickCallback);

        return true;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    public void completionSuccess(UserDataBean.SetInfoResult response) {
        SysConfigTool.setUser(response.getData().getUser());
        SysConfigTool.saveToken(response.getData().getToken().getToken());
        Logger.i("completionSuccess");
        uploadAvatar();
    }

    @Override
    public void onProgress(int progress) {
        loading("" + progress);
    }

    @Override
    public void uploadSuccess(FileBean response) {
        params.remove("mobile");
        params.remove("password");
        params.remove("code");
        params.put("avatar", response.getData());
        mPresenter.updateUser(params);
    }

    @Override
    public void updateUserSuccess(UserBean.UserResult response) {
        SysConfigTool.setUser(response.getData());
        finishWork();
    }

    @Override
    public void getUserInfoSuccess(UserBean.UserResult response) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SignInEvent event) {
        finishDown();
    }
}
