package com.example.mylibrary;

import android.content.Context;
import android.net.NetworkRequest;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guhongliang on 2017/9/25.
 */

public class FileUtils {
    private final static String FILE_NAME = "HaoZuLog.txt";

    /**
     * 保存数据到本地
     *
     * @param filePath
     * @param files
     * @param caches
     * @throws NullPointerException
     * @throws IOException
     */
    public static void saveDataLocal(String filePath, File files, String caches) throws NullPointerException, IOException {
        if (caches == null) {
            return;
        }
        if (!files.exists()) {
            files.mkdir();
        }
        //通过cache目录，创建文件对象
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        //文件输出流
        FileOutputStream fos;
        //存储的l字符串数据
        try {
            fos = new FileOutputStream(file, true);
            //写数据
            fos.write(caches.getBytes());
            //关闭文件流
            if (fos != null) {
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取本地数据
     *
     * @param context
     * @return
     */
    public static void readLocalData(Context context) throws IOException {
//        File file = new File(context.getCacheDir(), FILE_NAME);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Log", FILE_NAME);
        if (!file.exists()) {
            DLog.e("FileUtils", "file is not exist");
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            DLog.e("读取文件数据", new String(b));
            if (fis != null) {
                fis.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传成功后删除本地文件
     */
    public static boolean clearFile(File file) {
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    /**
     *
     * @param file
     */
    public  static void upLoadLog(File file,String request){



    }





}
