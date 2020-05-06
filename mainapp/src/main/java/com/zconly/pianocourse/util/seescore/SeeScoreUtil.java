package com.zconly.pianocourse.util.seescore;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import uk.co.dolphin_com.seescoreandroid.LicenceKeyInstance;
import uk.co.dolphin_com.sscore.LoadOptions;
import uk.co.dolphin_com.sscore.SScore;
import uk.co.dolphin_com.sscore.ex.ScoreException;
import uk.co.dolphin_com.sscore.ex.XMLValidationException;

/**
 * @Description: java类作用描述
 * @Author: dengbin
 * @CreateDate: 2020/5/5 13:54
 * @UpdateUser: dengbin
 * @UpdateDate: 2020/5/5 13:54
 * @UpdateRemark: 更新说明
 */
public class SeeScoreUtil {

    public static SScore loadMXLFile(File file) {
        if (!file.getName().endsWith(".mxl") || !file.exists())
            return null;
        InputStream is;
        try {
            is = new FileInputStream(file);
            try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is))) {
                ZipEntry ze;
                while ((ze = zis.getNextEntry()) != null) {
                    // ignore META-INF/ and container.xml
                    if (!ze.getName().startsWith("META-INF") && !ze.getName().equals("container.xml")) {
                        // read from Zip into buffer and copy into ByteArrayOutputStream which is converted
                        // to byte array of whole file
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int count;
                        while ((count = zis.read(buffer)) != -1) { // load in 1K chunks
                            os.write(buffer, 0, count);
                        }
                        try {
                            LoadOptions loadOptions = new LoadOptions(LicenceKeyInstance.SeeScoreLibKey, true);
                            return SScore.loadXMLData(os.toByteArray(), loadOptions);
                        } catch (XMLValidationException e) {
                            Log.w("sscore", "loadfile <" + file + "> xml validation error: " + e.getMessage());
                        } catch (ScoreException e) {
                            Log.w("sscore", "loadfile <" + file + "> error:" + e);
                        }
                    }
                }
            } catch (IOException e) {
                Log.w("Open", "file open error " + file, e);
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            Log.w("Open", "file not found error " + file, e1);
            e1.printStackTrace();
        }
        return null;
    }
}
