# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
# http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
# -keepclassmembers class fqcn.of.javascript.interface.for.webview {
#    public *;
# }

# Uncomment this to preserve the line PHONE_NUMBER information for
# debugging stack traces.

# If you keep the line PHONE_NUMBER information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# shrink:将一些无效代码给移除，即没有被显示调用的代码
-dontshrink
# 不优化输入的类文件
-dontoptimize
# 忽略警告
#-ignorewarning
-ignorewarnings
# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepattributes Exceptions,EnclosingMethod,InnerClasses,Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 代码混淆压缩比，在0~7之间，默认为5，一般不需要改
-optimizationpasses 5
# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
# 不做预校验，可加快混淆速度
# preverify是proguard的4个步骤之一
# Android不需要preverify，去掉这一步可以加快混淆速度
-dontpreverify
# 混淆时生成日志文件，即映射文件
-verbose
#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 指定映射文件的名称
-printmapping proguard.map

# 除了项目目录，其他都不混淆
#-keep class !com.meishubao.** {*;}
#-dontwarn **

-keep class com.chad.library.adapter.** {*;}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

# banner 的混淆代码
-keep class com.youth.banner.** {*;}

#沉浸式
-keep class com.gyf.barlibrary.* {*;}

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# glide end

# for DexGuard only
# -keep resourcexmlelements manifest/application/meta-data@value=GlideModule

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
# bugly end

# support
-dontwarn android.support.**
-dontwarn android.support.v4.**
-dontwarn android.support.v7.**                                             #去掉警告
-keep class android.support.** {*;}
-keep class android.support.v4.app.** {*;}
-keep interface android.support.v4.app.** {*;}
-keep class android.support.v8.renderscript.** {*;}
-keep class android.support.v4.** {*;}
-keep interface android.support.v4.** {*;}
-keep public class * extends android.support.v4.**
-keep class android.support.v7.** {*;}                                    #过滤android.support.v7
-keep interface android.support.v7.app.** {*;}
-keep public class * extends android.support.v7.**
# support end

# 高德地图定位 begin
# 3D 地图 V5.0.0之前：
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.amap.mapcore.*{*;}
-keep class com.amap.api.trace.**{*;}
# 3D 地图 V5.0.0之后：
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.**{*;}
-keep class com.amap.api.trace.**{*;}
# 定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
# 搜索
-keep class com.amap.api.services.**{*;}
# 2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
# 导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}
# 高德地图定位 end

# eventBus
-keep class org.greenrobot.eventbus.** {*;}
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# eventBus end

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留在Activity中的方法参数是View的方法
# 从而我们在layout里边编写onClick就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# 保留枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持自定义控件类不被混淆，指定格式的构造方法不去混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保持自定义控件类不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
    *** get*();
}

# greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# 分享
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-dontwarn com.tencent.**
-dontwarn com.kakao.**
-dontwarn android.net.http.SslError
-keep class com.umeng.socialize.impl.ImageImpl {*;}
-keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class android.net.http.SslError
-keep public class javax.**
-keep public class android.webkit.**
-keep enum com.facebook.**
-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public class com.umeng.socialize.* {*;}
-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.twitter.** { *; }
-keep class com.tencent.** {*;}
-keep class com.kakao.** {*;}
-keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
}
-keep class twitter4j.** { *; }
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.sina.** {*;}
-keep class  com.alipay.share.sdk.** {*;}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-dontwarn twitter4j.**
-dontwarn com.sina.**

# 友盟统计
-keep class com.umeng.commonsdk.** {*;}
-keep class com.umeng.** {*;}
-keep public class [com.zconly.pianocourse].R$*{
    public static final int *;
}
-keepclassmembers class * {
    public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 微信支付
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keepclassmembers class **.R$* { *; }
-keepclassmembers class * implements android.os.Parcelable { *;}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 对于带有回调函数onXXEvent()的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
}

# WebView
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-dontwarn android.webkit.WebViewClient
-keep public class android.webkit.WebViewClient
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
# WebView end

# 保留了继承自Activity、Application、Fragment这些类的子类
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.view.ViewPager
-keep public class * extends android.support.v4.app.FragmentManager
-keep public class * extends android.support.v4.app.PagerAdapter
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.os.Parcelable
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.app.web
-keep class org.apache.http.entity.mime.** {*;}
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}

# fastjson
-keep class com.alibaba.fastjson.** { *; }
-dontwarn com.alibaba.fastjson.**

# okhttp
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# Retrofit
-keep class retrofit2.** { *; }
-keep class com.smart.novel.net.** { *; }
-dontwarn retrofit2.**

# Retrolambda
-dontwarn java.lang.invoke.*

# RxJava RxAndroid
# 解决使用Retrofit+rxJava联网时，在6.0系统出现java.lang.InternalError奔溃的问题:
# http://blog.csdn.net/mp624183768/article/details/79242147
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# rxandroid-1.2.1
-keepclassmembers class rx.android.**{*;}

# Gson
-keep class org.xz_sale.entity.**{*;}
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

# 保留JS方法不被混淆
-keepclassmembers class com.example.xxx.MainActivity$JSInterface1 {
    <methods>;
}

# okio
-keep class okio.** {*;}
-dontwarn okio.**

# ali
-keep class com.alibaba.** {*;}
-dontwarn com.alibaba.**

# ali oss
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

# ali hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
-dontwarn com.alibaba.sdk.android.utils.**

# baidu video
-keep class com.baidu.cloud.media.**{ *;}

# local
-keep class com.zconly.pianocourse.event.** { *; }
-keep class com.zconly.pianocourse.widget.** { *; }
-keep class * extends com.zconly.pianocourse.bean.BaseBean

# linkedme
-keep class com.microquation.linkedme.android.** { *; }

# jpush
-dontwarn cn.jpush.**
-dontwarn cn.jiguang.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-keep class cn.jiguang.** { *; }