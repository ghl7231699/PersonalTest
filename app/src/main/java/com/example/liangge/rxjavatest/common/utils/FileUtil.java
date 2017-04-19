package com.example.liangge.rxjavatest.common.utils;

import java.io.File;

/**
 * Created by guhongliang on 2017/4/19.
 * 文件处理工具类
 */

public class FileUtil {
    /**
     * 新建文件夹
     *
     * @param s 文件名
     */
    public static void create(String s) {
        File file = new File(s);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 文件是否存在
     *
     * @param s
     * @return
     */
    public static boolean fileExits(String s) {
        try {
            File f = new File(s);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
