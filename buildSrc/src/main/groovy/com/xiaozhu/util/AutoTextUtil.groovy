package com.xiaozhu.util

/**
 * 类描述：路径转换工具
 * 创建人：ghl
 * 创建时间：2019/4/2
 * @version v1.0
 */
class AutoTextUtil {
    static boolean isEmpty(String text) {
        return text == null || text.trim().length() < 1
    }

    static String path2ClassName(String pathName) {
        pathName.replace(File.separator, ".").replace(".class", "")
    }

    /**
     * 全类名转换下符号，例如"xxx.xxx.xxx" -> "xxx/xxx/xxx"
     */
    static String changeClassNameSeparator(String classname) {
        return classname.replace('.', '/')
    }

}