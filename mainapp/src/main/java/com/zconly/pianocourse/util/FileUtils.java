package com.zconly.pianocourse.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.zconly.pianocourse.base.Constants;

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

    public static String getPath(Context context, Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection,
                        null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");

                if (cursor.moveToFirst()) {
                    result = cursor.getString(column_index);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equals(uri.getScheme())) {
            return uri.getPath();
        }
        return result;
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

    /*
     * 如果存在外部存储卡,返回外部存储卡地址
     * 否则返回内部存储地址
     */
    public static String getExternalStoragePathIfExits() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (Constants.DOWNLOAD_SAVE_PATH == 1 && haveRemoveableSDCard()) {
            if (Build.VERSION.SDK_INT >= 19) {
                //Android 4.4，代号为KitKat
                //根据新版本的API改进，应用程序将不能直接往SD卡中写入文件 只能访问指定路径
                path = System.getenv("SECONDARY_STORAGE") + "/Android/data/com.meishubao.client/files";
            } else {
                //Android 4.4，代号为KitKat 以下的版本就放在根目录了
                path = System.getenv("SECONDARY_STORAGE");
            }
        }
        return path;
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
}
