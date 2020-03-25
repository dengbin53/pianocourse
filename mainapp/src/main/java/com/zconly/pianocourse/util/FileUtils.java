package com.zconly.pianocourse.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    //	private static final String FOLDER_NAME = "/Msb";
    private static String mDataRootPath = null;
    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();

    public FileUtils(Context paramContext) {
        mDataRootPath = paramContext.getCacheDir().getPath();
    }

    public static long fileSize(String paramString) {
        return new File(paramString).length();
    }

    private static String getExternalStorageBaseDir(Context paramContext) {
        String str1 = "/Android/data/" + paramContext.getPackageName();
        boolean bool = hasExternalStorage();
        String str2 = null;
        if (bool) {
            str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + str1;
            mkdirs(str2);
        }
        return str2;
    }

    public static String getStorageCacheDir(Context paramContext) {
        if (hasExternalStorage()) {
            String str = getExternalStorageBaseDir(paramContext) + "/cache";
            mkdirs(str);
            return str;
        }
        return paramContext.getCacheDir().getAbsolutePath();
    }

    public static File getStorageCacheFile(Context paramContext, String paramString) {
        String str = getStorageCacheDir(paramContext);
        return new File(str + File.separator + paramString);
    }

    public static boolean checkFile(File f) {
        return f != null && f.exists() && f.canRead() && (f.isDirectory() || (f.isFile() && f.length() > 0));
    }

    private String getStorageDirectory() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return mSdRootPath + "/Msb";
        }
        return mDataRootPath + "/Msb";
    }

    public static String getStorageFilesDir(Context paramContext) {
        if (hasExternalStorage()) {
            String str = getExternalStorageBaseDir(paramContext) + "/files";
            mkdirs(str);
            return str;
        }
        return paramContext.getFilesDir().getAbsolutePath();
    }

    private static void mkdirs(String paramString) {
        File localFile = new File(paramString);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
    }

    private static boolean hasExternalStorage() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String getPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) { // ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) { // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) { // MediaStore (and general)
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) { // File
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    /**
     * 删除文件夹下的所有文件
     *
     * @param file
     */
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;

        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < listFiles.length; i++) {
                delete(listFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * 删除文件或整个文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteFileOrDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();//递归删除目录中的子目录下
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteFileOrDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 将字节流存为文件
     *
     * @param bytes
     * @param fileName 文件地址及文件名。例："/sdcard/a.txt”
     */

    public static void saveFileByBytes(byte[] bytes, String fileName) {
        try {
            File saveFile = new File(fileName);
            FileOutputStream outStream = new FileOutputStream(saveFile);
            outStream.write(bytes);
            outStream.close();
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    /**
     * 以字节流方式读取文件
     *
     * @param fileName 文件地址及文件名。例："/sdcard/a.txt”
     * @return
     */
    public static byte[] loadFile2Bytes(String fileName) {
        FileInputStream inStream = null;
        ByteArrayOutputStream stream = null;
        try {

            File file = new File(fileName);
            inStream = new FileInputStream(file);
            stream = new ByteArrayOutputStream();
            long l = file.length();//最大值2147483647
            byte[] buffer = new byte[(int) l];
            int length;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            return buffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(inStream);
            close(stream);
        }
    }

    /**
     * 是否有可插拔的SD卡
     * Warning:有些机型即使插入SD卡也取不到路径,第三方厂商把地址修改过了,这里就不考虑这些情况了
     * Warning:有SD卡插孔的手机,即使没插内存卡System.getenv("SECONDARY_STORAGE")也返回路径,并且路径存在,
     * 用判断路径可用空间的大小来判断SD卡是否插入了
     *
     * @return
     */
    public static boolean haveRemoveableSDCard() {
        try {
            return !TextUtils.isEmpty(System.getenv("SECONDARY_STORAGE"))
                    && new File(System.getenv("SECONDARY_STORAGE")).exists()
                    && new File(System.getenv("SECONDARY_STORAGE")).getFreeSpace() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static File getCacheDir(Context context) {
        File sdDir = context.getExternalCacheDir();
        if (sdDir == null) {
            sdDir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Android/data/" + context.getPackageName() + "/cache");
            sdDir.mkdirs();
        }
        return sdDir;
    }

    // 拷贝文件
    public static boolean copyFile(String oldPath, String newPath) {
        InputStream inStream = null; // 读入原文件
        FileOutputStream fs = null;
        try {
            File oldfile = new File(oldPath);
            File newfile = new File(newPath);
            if (newfile.exists()) {
                newfile.delete();
            }

            newfile.createNewFile();

            // 文件存在时
            if (oldfile.exists()) {
                inStream = new FileInputStream(oldPath); //读入原文件
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[5 * 1024];
                int byteread;
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                // 拷贝完成
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(inStream);
            close(fs);
        }
        return false;
    }

    public static Uri getFileUri(Context context, File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    // 删除files里面的所有文件
    public static void deleteAllFiles(File files) {
        if (files == null || !files.exists()) {
            return;
        }
        if (files.isFile()) {
            //文件直接删除
            files.delete();
        } else if (files.isDirectory()) {
            //文件夹
            File[] innerFiles = files.listFiles();
            if (innerFiles != null) {
                for (File newFile : innerFiles) {
                    deleteAllFiles(newFile);
                }
            }
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
            }
        }
    }

    /**
     * @param folderPath 文件夹路径
     */
    public static void delFolder(String folderPath) {
        delAllFile(folderPath);
        String filePath = folderPath;
        File myFilePath = new File(filePath);
        myFilePath.delete();
    }

    public static File createImageFile() {
        File cropedDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File cropedImageFile = new File(cropedDir, "IMG_" + System.currentTimeMillis() + ".jpg");
        // 如果父目录没有存在，则创建父目录
        if (!cropedImageFile.getParentFile().exists())
            cropedImageFile.getParentFile().mkdirs();

        return cropedImageFile;
    }

    public static void save(Uri uri, Context context) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 提取照片的Exif信息 目前Android SDK定义的Tag有: TAG_DATETIME 时间日期 TAG_FLASH 闪光灯
     * TAG_GPS_LATITUDE 纬度 TAG_GPS_LATITUDE_REF 纬度参考 TAG_GPS_LONGITUDE 经度
     * TAG_GPS_LONGITUDE_REF 经度参考 TAG_IMAGE_LENGTH 图片长 TAG_IMAGE_WIDTH 图片宽
     * TAG_MAKE 设备制造商 TAG_MODEL 设备型号 TAG_ORIENTATION 方向 TAG_WHITE_BALANCE 白平衡
     */
    public static float[] getPicGps(String path) {
        float[] output = new float[2];
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            exifInterface.getLatLong(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * 打开相册intent
     */
    public static Intent getPhotoPickIntent() {
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    public static boolean isImagePath(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        return path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".jpeg");
    }
}
