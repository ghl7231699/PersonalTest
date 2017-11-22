package com.example.liangge.rxjavatest.ndk.CustomFix;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * 类名称：Patch
 * 类描述：主要是对patch文件进行解析
 * 创建人：ghl
 * 创建时间：2017/11/18 下午10:17
 * 修改人：ghl
 * 修改时间：2017/11/18 下午10:17
 *
 * @version v1.0
 */

public class Patch {
    private File mFile;
    private Context mContext;
    private static final String PATCH_CLASSES = "Patch-Classes";
    private static final String ENTRY_NAME = "META-INF/PATCH.MF";
    Map<String, List<String>> mClsssMap;//保存修改的类的方法名称

    public Patch(Context context, File file) {
        mContext = context;
        mFile = file;
        init();
    }

    public List<String> getClasses(String name) {
        if (mClsssMap != null) {
            return mClsssMap.get(name);
        }
        return null;
    }

    public Set<String> getPatchName() {
        return mClsssMap == null ? null : mClsssMap.keySet();
    }

    /**
     * 作者：ghl
     * 描述：初始化，获取到patch文件内容
     * <p>
     * <p>
     * 在实际的使用中，patch文件中可能会有多种方法修改
     * 创建时间：2017/11/19 下午2:10
     *
     * @Params:
     * @Return:
     */
    private void init() {
        JarFile jarFile = null;
        InputStream is = null;
        mClsssMap = new HashMap<>();
        List<String> list;
        try {
            jarFile = new JarFile(mFile);
            JarEntry jarEntry = jarFile.getJarEntry(ENTRY_NAME);
            if (jarEntry != null) {//获取io流
                is = jarFile.getInputStream(jarEntry);
                //获取到PATCH.MF文件中的内容
                Manifest manifest = new Manifest(is);
                //通过key值，获取到需要修复的方法的属性
                Attributes attributes = manifest.getAttributes(PATCH_CLASSES);
                //通过遍历属性map，获取到name
                Attributes.Name attrName;
                Iterator<Object> iterator = attributes.keySet().iterator();
                for (attributes.keySet().iterator(); iterator.hasNext(); ) {
                    attrName = (Attributes.Name) iterator.next();
                    if (attrName != null) {
                        String name = attrName.toString();
                        //进行截取
                        if (name.endsWith("Classes")) {
                            list = Arrays.asList(attributes.getValue(name).split(","));
                            if (name.equalsIgnoreCase(PATCH_CLASSES)) {
                                mClsssMap.put(name, list);
                            } else {
                                //可能会出现PatchA-Classes这种情况
                                String substring = name.trim().substring(name.length() - 8, name.length());
                                mClsssMap.put(substring, list);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("patch", e.getMessage());
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
