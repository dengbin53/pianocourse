package com.zconly.pianocourse.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.core.app.ActivityCompat;

import com.zconly.pianocourse.base.RequestCode;

import java.io.File;

public class ImageUtil {
    /**
     * 打开选图对话框
     */
    public static void doPickPhotoAction(final Activity act, final File mCurrentPhotoFile) {
        final Context dialogContext = new ContextThemeWrapper(act, android.R.style.Theme_Holo_Light);
        String cancel = "取消";
        String[] choices;
        choices = new String[2];
        choices[0] = "拍照"; // 拍照
        choices[1] = "相册"; // 从相册中选择
        ListAdapter adapter = new ArrayAdapter<>(dialogContext, android.R.layout.simple_list_item_1, choices);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogContext);
        builder.setTitle("选择头像");
        builder.setSingleChoiceItems(adapter, -1, (dialog, which) -> {
            dialog.dismiss();
            switch (which) {
                case 0: {
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) // 判断是否有SD卡
                        gotoCamera(act, mCurrentPhotoFile);// 用户点击了从照相机获取
                    else
                        ToastUtil.toast("SD卡");
                    break;
                }
                case 1:
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) // 判断是否有SD卡
                        doPickPhotoFromGallery(act);// 从相册中去获取
                    else
                        ToastUtil.toast("SD卡");
                    break;
            }
        });
        builder.setNegativeButton(cancel, (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    // 跳转相机
    public static void gotoCamera(Activity activity, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int granted = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
            if (granted != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.toast("请给应用拍照权限");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                        10101);
                return;
            }
        }

        Uri uri = FileUtils.getFileUri(activity, file);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, RequestCode.CAMERA_WITH_DATA);
    }

    // 请求Gallery程序
    public static void doPickPhotoFromGallery(Activity act) {
        Intent pictureView = new Intent(Intent.ACTION_GET_CONTENT);
        pictureView.setType("image/*");
        act.startActivityForResult(pictureView, RequestCode.LOCAL);
    }

    // 裁剪图片
    public static void crop(Activity mActivity, Uri sourceUri, Uri outUri, CropType type) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(sourceUri, "image/*");
        intent.putExtra("crop", "true"); // 可裁剪
        intent.putExtra("aspectX", type.aspectX); // 图片比例
        intent.putExtra("aspectY", type.aspectY); // 图片比例
        intent.putExtra("outputX", type.width); // 图片尺寸
        intent.putExtra("outputY", type.height); // 图片尺寸
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);//去黑边
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.putExtra("return-data", false); // 若为false则表示不返回数据
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

        mActivity.startActivityForResult(intent, RequestCode.RC_CROP_IMG);
    }
}
