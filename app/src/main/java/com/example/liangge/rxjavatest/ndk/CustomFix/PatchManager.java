package com.example.liangge.rxjavatest.ndk.CustomFix;

import android.content.Context;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Created by guhongliang on 2017/11/18.
 * patch文件的管理类
 */

public class PatchManager {
    private Context mContext;
    private FixManager mFixManager;
    private File mFile;

    public PatchManager(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mFixManager = new FixManager(mContext);
    }

    public void loadPatch(String patchName) {
        mFile = new File(patchName);
        if (mFile != null) {
            Patch patch = new Patch(mContext, mFile);
            loadPatch(patch);
        }
    }

    private void loadPatch(Patch patch) {
        ClassLoader classLoader = mContext.getClassLoader();
        Set<String> patchName = patch.getPatchName();
        List<String> list;
        for (String name :
                patchName) {
            list=patch.getClasses(name);
            mFixManager.fix(mFile,classLoader,list);
        }
    }


}
