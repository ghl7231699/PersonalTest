package com.xiaozhu.flow

import com.android.build.api.transform.*
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils

public class EachEveryone {


    static void each(TransformInvocation transformInvocation, Closure jarClosure, Closure dirFileClosure) {
        //常量值 4
        final def size = ".jar".length()
        transformInvocation.inputs.each { TransformInput input ->
            //jar 包遍历
            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - size)
                }
                //得到复制后的文件
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                jarClosure.call(jarInput, dest)
                //所有的transform 后的文件都需要进行输出
                FileUtils.copyFile(jarInput.file, dest)
            }
            // 文件夹遍历
            input.directoryInputs.each { DirectoryInput directoryInput ->
                //得到复制后FILE 实例
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                // 遍历文件夹中的所有文件
                directoryInput.file.eachFileRecurse { File file ->
                    dirFileClosure.call(file)
                }
                //所有的transform 后的文件都需要进行输出
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
    }

}