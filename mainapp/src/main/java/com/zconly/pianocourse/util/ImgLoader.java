package com.zconly.pianocourse.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.zconly.pianocourse.R;
import com.zconly.pianocourse.base.MainApplication;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 图片加载器
 * 使用 glide 图片加载框架
 * Created by hubin on 16/7/1.
 * modify by dengbin on 19/12/18(待优化为builder模式)
 */
public class ImgLoader {

    private static int WIDTH_AVATAR = 128;
    private static int WIDTH_SMALL = WIDTH_AVATAR * 2;
    private static int WIDTH_MIDDLE = WIDTH_SMALL * 2;

    // 显示头像
    public static void showAvatar(String url, ImageView imageView) {
        showAvatar(url, imageView, R.mipmap.ic_avatar_default);
    }

    // 显示头像
    public static void showAvatar(String url, ImageView iv, int defImg) {
        showImgCircle(url, iv, defImg, WIDTH_AVATAR, WIDTH_AVATAR);
    }

    // 显示头像
    public static void showAvatar(Fragment fragment, String url, ImageView iv) {
        RequestManager rm = getRequestManager(fragment);
        doShowImg(rm, url, iv, R.mipmap.ic_avatar_default, 0, null, true, WIDTH_AVATAR,
                WIDTH_AVATAR, null);
    }

    // 圆形图片 rw/rw都传0则不做resize处理
    public static void showImgCircle(String url, ImageView iv, int defImg, int rw, int rh) {
        RequestManager rm = getRequestManager(iv.getContext());
        doShowImg(rm, url, iv, defImg, 0, null, true, rw, rh, null);
    }

    // 圆角图片（圆角8dp,不限宽高）
    public static void showImgRound(String url, ImageView iv) {
        showImgRound(url, iv, DeviceUtils.dp2px(4f));
    }

    // 圆角图片 round 圆角；rw 限制宽高
    public static void showImgRound(String url, ImageView iv, int round) {
        doShowImg(getRequestManager(iv.getContext()), url, iv, R.drawable.shape_gray_light, round, null,
                false, 0, 0, null);
    }

    // 圆角图片 round 圆角 ct 圆角类型
    public static void showImgRound(String url, ImageView iv, int round, RoundedCornersTransformation.CornerType ct) {
        doShowImg(getRequestManager(iv.getContext()), url, iv, R.drawable.shape_gray_light, round, ct, false,
                WIDTH_MIDDLE, WIDTH_MIDDLE, null);
    }

    // 设置图片Bitmap
    public static void showImg(Bitmap bitmap, ImageView iv) {
        getRequestManager(iv.getContext()).load(bitmap).into(iv);
    }

    public static void showImg(String url, ImageView iv) {
        showImg(url, iv, R.drawable.shape_gray_light);
    }

    public static void showImg(String url, ImageView iv, int defImg) {
        doShowImg(getRequestManager(iv.getContext()), url, iv, defImg, 0, null, false,
                WIDTH_MIDDLE * 2, WIDTH_MIDDLE * 4, null);
    }

    // 显示小图(一排3列或更多)
    public static void showImgSmall(String url, ImageView iv) {
        showImgResize(url, iv, WIDTH_SMALL, WIDTH_SMALL);
    }

    public static void showImgSmall(String url, ImageView iv, int defId) {
        showImgResize(url, iv, WIDTH_SMALL, WIDTH_SMALL, defId);
    }

    // 显示中图(一排两列)
    public static void showImgMiddle(String url, ImageView iv) {
        showImgResize(url, iv, WIDTH_MIDDLE, WIDTH_MIDDLE);
    }

    public static void showImgResize(String url, ImageView iv, int resizeWidth, int resizeHeight) {
        showImgResize(url, iv, resizeWidth, resizeHeight, R.drawable.shape_gray_light);
    }

    public static void showImgResize(String url, ImageView iv, int resizeWidth, int resizeHeight, int defImg) {
        doShowImg(getRequestManager(iv.getContext()), url, iv, defImg, 0, null, false,
                resizeWidth, resizeHeight, null);
    }

    // 有箭头的图片加载
    public static void showImgWithArrowAndListener(String url, ImageView iv, int w, int h,
                                                   DownloadImgListener<Drawable> listener) {
        if (TextUtils.isEmpty(url))
            return;
        doShowImg(getRequestManager(iv.getContext()), url, iv, R.drawable.shape_gray_light, 0, null,
                false, w, h, listener);
    }

    public static void showImgAndListener(String url, ImageView iv, DownloadImgListener<Drawable> listener) {
        showImgAndListener(iv.getContext(), url, iv, listener);
    }

    public static void showImgAndListener(Context context, String url, ImageView iv,
                                          DownloadImgListener<Drawable> listener) {
        doShowImg(getRequestManager(context), url, iv, R.drawable.shape_gray_light, 0, null, false,
                0, 0, listener);
    }

    public static void downloadDrawable(Context context, String url, int w, int h,
                                        DownloadImgListener<Drawable> listener) {
        RequestBuilder<Drawable> rb = getRequestManager(context)
                .asDrawable()
                .load(url);
        if (w > 0 && h > 0)
            rb = rb.override(w, h);

        CustomTarget<Drawable> ct = new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource,
                                        @Nullable Transition<? super Drawable> transition) {
                if (listener != null)
                    listener.OnDownloadFinish(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                if (listener != null)
                    listener.OnDownloadFailed();
            }
        };

        rb.into(ct);
    }

    public static void downloadBitmap(Context context, String url, int round, DownloadImgListener<Bitmap> listener) {
        RequestBuilder<Bitmap> rb = getRequestManager(context).asBitmap();
        rb = getRequestBuilder(rb, url);

        if (round > 0) { // 圆角图片
            RoundedCornersTransformation rctf = new RoundedCornersTransformation(round, 0,
                    RoundedCornersTransformation.CornerType.ALL);
            rb = rb.transform(rctf).dontAnimate();
        }

        CustomTarget<Bitmap> ct = new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap>
                    transition) {
                if (listener != null)
                    listener.OnDownloadFinish(resource);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                if (listener != null)
                    listener.OnDownloadFailed();
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        };
        rb.into(ct);
    }

    public static void downloadBitmap(Context context, String url, DownloadImgListener<Bitmap> listener) {
        downloadBitmap(context, url, 0, listener);
    }

    public static <T> void downloadOnly(String url, DownloadImgListener<T> listener) {
        downloadOnly(MainApplication.getInstance(), url, null, listener);
    }

    public static <T> void downloadOnly(String url, String path, DownloadImgListener<T> listener) {
        downloadOnly(MainApplication.getInstance(), url, path, listener);
    }

    // 构建glide不使整个项目，使用当前的
    public static <T> void downloadOnly(final Context context, final String url, final String path,
                                        final DownloadImgListener<T> listener) {
        CustomTarget<File> ct = new CustomTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                if (TextUtils.isEmpty(path)) {
                    if (listener != null)
                        listener.OnDownloadFinish((T) resource);
                    return;
                }
                boolean res = FileUtils.copyFile(resource.getAbsolutePath(), path);

                resource.delete();

                if (res) {
                    if (listener != null)
                        listener.OnDownloadFinish((T) path);
                } else {
                    onLoadFailed(null);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                if (listener != null)
                    listener.OnDownloadFailed();
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        };

        doDownload(context, url, ct);
    }

    public static void resume() {
        Glide.with(MainApplication.getInstance()).resumeRequests();
    }

    public static void pause() {
        Glide.with(MainApplication.getInstance()).pauseRequests();
    }

    public static void clear() {
        Glide.get(MainApplication.getInstance()).clearDiskCache();
    }

    private static void doDownload(Context context, String url, CustomTarget<File> ct) {
        getRequestManager(context).download(url).into(ct);
    }

    private static RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    private static RequestManager getRequestManager(Fragment fragment) {
        return Glide.with(fragment);
    }

    private static void doShowImg(RequestManager rm, String url, ImageView iv, int defImg, int round,
                                  RoundedCornersTransformation.CornerType ct, boolean circle, int resizeWidth,
                                  int resizeHeight, DownloadImgListener<Drawable> listener) {

        RequestBuilder<Drawable> rb = getRequestBuilder(rm, url, defImg);

        if (resizeWidth > 0 || resizeHeight > 0) {
            rb = rb.override(resizeWidth > 0 ? resizeWidth : DeviceUtils.getScreenWidth() / 2,
                    resizeHeight > 0 ? resizeHeight : DeviceUtils.getScreenWidth() / 2);
        }

        RequestListener<Drawable> rl = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                        boolean isFirstResource) {
                if (listener != null)
                    listener.OnDownloadFailed();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                           DataSource dataSource, boolean isFirstResource) {
                if (listener != null) {
                    listener.OnDownloadFinish(resource);
                }
                return false;
            }
        };

        rb = rb.listener(rl)
                .error(defImg);

        // glide 对 ImageView ImageView.ScaleType.CENTER_CROP 属性处理不对
//        ImageView.ScaleType scaleType = iv.getScaleType();
//        if (scaleType == ImageView.ScaleType.CENTER_CROP)
//            rb = rb.fitCenter();

        if (circle) { // 圆形图片
            rb = rb.transform(new CircleCrop())
                    .thumbnail(loadTransform(iv.getContext(), defImg))
                    .dontAnimate(); // 防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
        } else if (round > 0) { // 圆角图片
            RoundedCornersTransformation rctf = new RoundedCornersTransformation(round, 0,
                    ct == null ? RoundedCornersTransformation.CornerType.ALL : ct);
            rb = rb.transform(rctf)
                    .dontAnimate();
        } else {
            rb = rb.transition(DrawableTransitionOptions.withCrossFade(256));
        }

        rb.into(iv);
    }

    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId) {
        return Glide.with(context)
                .load(placeholderId)
                .apply(new RequestOptions().centerCrop().transform(new CircleCrop()));
    }

    private static RequestBuilder<Drawable> getRequestBuilder(RequestManager rm, String url, int defImg) {
        RequestBuilder<Drawable> rb;
        if (TextUtils.isEmpty(url) || url.toLowerCase().startsWith("http")) {
            rb = rm.load(url);
        } else {
            rb = rm.load(new File(url));
        }
        return rb.placeholder(defImg);
    }

    private static RequestBuilder<Bitmap> getRequestBuilder(RequestBuilder<Bitmap> rb, String url) {
        if (TextUtils.isEmpty(url) || url.toLowerCase().startsWith("http")) {
            rb = rb.load(url);
        } else {
            rb = rb.load(new File(url));
        }
        return rb.placeholder(R.drawable.shape_gray_light);
    }

    public interface DownloadImgListener<T> {
        void OnDownloadFinish(T resource);

        void OnDownloadFailed();
    }

}
