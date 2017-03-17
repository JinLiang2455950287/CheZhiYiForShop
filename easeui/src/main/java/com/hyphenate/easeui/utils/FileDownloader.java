package com.hyphenate.easeui.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Description:
 * author: zhangsan on 16/9/4 上午10:04.
 */
public class FileDownloader {
    private static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String path = SDPATH + "/chezhiyi/hxvoice/";
    /**
      * 下载文件 成功返回下载路径 失败返回空
      *@author zhangsan
      *@date   16/9/5 下午1:02
      */
    public static String downLoad(String downloadUrl, String fileName) {
        return getFilePathString(downloadUrl, getCacheVoicePath(fileName));
    }


    public static String getFilePathString(String downloadUrl, String cacheFilePath) {
        URL url;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                File file = new File(cacheFilePath);
                fos = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();
                return file.getPath();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {

                    is.close();

                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    // 创建文件夹
    public static void createFilePath(String path) {
        String filepath = path.substring(0, path.lastIndexOf("/"));
        File file = new File(path);
        File filesPath = new File(filepath);

        if (!filesPath.exists()) {
            createFilePath(filepath);
            file.mkdir();
        } else {
            file.mkdir();
        }
    }

    public static String getCacheVoicePath(String fileName){
        return getPicPath(fileName, ".amr");
    }

    public static String getCacheFilePath(String fileName){
        return getPicPath(fileName, ".jpeg");
    }

    private static String getPicPath(String fileName, String format) {
        StringBuilder sb = new StringBuilder(path);
        sb.append(fileName);
        sb.append(format);
        createFilePath(path);
        return sb.toString();
    }
}
