package com.xiaozhu.plugin

import com.android.build.api.transform.JarInput
import com.android.build.gradle.AppPlugin
import com.xiaozhu.PigTransform
import com.xiaozhu.flow.EachEveryone
import com.xiaozhu.flow.TargetCodeScanner
import com.xiaozhu.inject.InjectInfo
import com.xiaozhu.inject.RegistryCodeGenerator
import com.xiaozhu.util.CheckUtils
import org.gradle.api.Plugin
import org.gradle.api.Project


public class PigRouterPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "router ----- start "
        println "router ----- start "
        //加载相应的lib 与 apt
        def compile = "implementation"
        def apt = "annotationProcessor"
        // router lib
        Project routerProject = project.rootProject.findProject("router")
        project.dependencies.add(compile, routerProject)
        // router apt
        Project aptProject = project.rootProject.findProject("router:router_apt")
        project.dependencies.add(apt, aptProject)

        //开始为apt设置相应的项目数据
        def android = project.extensions.findByName("android")
        if (android) {
            //在android 的 闭包下才能设置
            String inputName = project.name.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+", "")
            inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1)
            android.defaultConfig.javaCompileOptions.annotationProcessorOptions.argument("project_name", inputName)
        }

        // 只有主项目才会加入transform 插入代码
        if (project.plugins.hasPlugin(AppPlugin)) {
            android.registerTransform(new PigTransform("PigRouter", { transformInvocation ->
                //常量
                final String targetClass = "com/xiaozhu/xzdz/router/lib/Router.class"
                final String interfaceClass = "com/xiaozhu/xzdz/router/lib/RouterClassProvider"
                final String invokingMethodName = "bindClassProvider"
                TargetCodeScanner mScanner = new TargetCodeScanner(targetClass, interfaceClass)
                EachEveryone.each(transformInvocation,
                        { jarInput, dest ->
                            if (isNeedScanJar(jarInput)) {
                                mScanner.scanJar(jarInput.file, dest)
                            }
                        },
                        { file ->
                            if (isNeedScanFile(file)) {
                                mScanner.scanClass(file.newInputStream(), file.absolutePath)
                            }
                        }
                )

                // info 生成 start
                InjectInfo info = new InjectInfo()
                //目标类 需要去掉.class
                info.targetClass = targetClass.substring(0, targetClass.lastIndexOf('.'))
                //插入代码的位置 静态代码块
                info.initMethodName = "<clinit>"
                //被实现的接口
                info.interfaceClass = interfaceClass
                // jar包
                info.targetFile = mScanner.fileHasClass
                // 所有类
                info.allInter.addAll(mScanner.allNeedInner)
                //注册方法
                info.invokingMethodName = invokingMethodName
                //父类
                // info 生成 end

                //生成代码
                if (info.targetClass && info.allInter.size() > 0) {
                    //文件存在 ，并且实现类存在时，才修改字节码
                    RegistryCodeGenerator.insertInitCodeTo(info)
                }
            }))
        }

        println "router ----- end "
        println "router ----- end "
    }

    /**
     * 对于一些系统包 ，是不需要更新解析的
     * @param jarInput jar 包
     * @return 是否需要解析
     */
    static boolean isNeedScanJar(JarInput jarInput) {
        return CheckUtils.isNeedScanJar(jarInput)
    }

    static boolean isNeedScanFile(File file) {
        return CheckUtils.isNeedScanFile(file)
    }
}