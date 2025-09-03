package com.xinji.test;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: ChenYY
 * @Date: 2024/9/30 18:59
 * @Description: 文件管理类
 */
public class FileUtil {

    public static void moveAssets2File(String name, Context context) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getAssets().open(name);
            out = context.openFileOutput( name, Context.MODE_PRIVATE);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
