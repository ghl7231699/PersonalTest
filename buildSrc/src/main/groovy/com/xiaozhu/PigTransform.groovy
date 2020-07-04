package com.xiaozhu

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager

class PigTransform extends Transform {

    // plugin 所需要的name
    private String mName

    // transform
    private Closure<TransformInvocation> mTransformAction

    public PigTransform(String name, Closure transformInvocationAction) {
        this.mName = name
        this.mTransformAction = transformInvocationAction
    }

    @Override
    String getName() {
        return mName
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        //固定写法
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        //固定写法
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        // 当前不支持 instance run
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        this.mTransformAction.call(transformInvocation)
    }

}