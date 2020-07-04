package com.xiaozhu.util

import com.android.build.api.transform.JarInput

public class CheckUtils {
    //需要忽略的包
    public static Set<String> excludeJar = ["com.android.support", "android.arch.", "androidx."]

    /**
     * 对于相应的jar class 判断是否需要scan
     * @return true 需要
     */
    public static boolean isNeedScanJar(JarInput jarInput) {
        boolean isScanJar = true
        excludeJar.each {
            def name = jarInput.name
            def isScan = name.contains(it)
            if (isScan) {
                isScanJar = false
                return isScanJar
            }
        }
        return isScanJar
    }

    public static boolean isNeedScanFile(File targetFile) {
        return targetFile.isFile() && !(targetFile.absolutePath.contains("R.class") || targetFile.absolutePath.contains("R" + '$'))
    }
}