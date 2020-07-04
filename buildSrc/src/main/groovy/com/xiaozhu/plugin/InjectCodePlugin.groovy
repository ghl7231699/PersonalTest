package com.xiaozhu.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.xiaozhu.asm.AutoTransform
import com.xiaozhu.bean.AutoClassFilter
import com.xiaozhu.bean.AutoSettingParams
import com.xiaozhu.config.GlobalConfig
import com.xiaozhu.util.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 插件入口
 */
class InjectCodePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        /**
         * build.gradle文件闭包拓展（可添加）
         */
        project.extensions.create('xiaozhu', AutoSettingParams)
        GlobalConfig.setProject(project)
        println(GlobalConfig.getParams().name)

        //使用Transform实行遍历
        if (project.plugins.hasPlugin(AppPlugin)) {
            def android = project.getExtensions().findByType(AppExtension)
            android.registerTransform(new AutoTransform())
        }

        project.afterEvaluate {
            Logger.setDebug(project.xiaozhu.isDebug)
            // 用户配置解析
            analyzeUserConfig()
        }
    }

    /**
     * 对build.gradle配置参数及自定义内容进行解析
     */
    static void analyzeUserConfig() {

        List<Map<String, Object>> matchDataList = GlobalConfig.getParams().matchData
        List<AutoClassFilter> autoClassFilterList = new ArrayList<>()

        matchDataList.each {
            Map<String, Object> map ->
                AutoClassFilter classFilter = new AutoClassFilter()

                String className = map.get("ClassName")
                String interfaceName = map.get("InterfaceName")
                String methodName = map.get("MethodName")
                String methodDes = map.get("MethodDes")
                Closure methodVisitor = map.get("MethodVisitor")
                boolean isAnnotation = map.get("isAnnotation")
                // 类的全类名
                if (!AutoTextUtil.isEmpty(className)) {
                    className = AutoTextUtil.changeClassNameSeparator(className)
                }
                // 接口的全类名
                if (!AutoTextUtil.isEmpty(interfaceName)) {
                    interfaceName = AutoTextUtil.changeClassNameSeparator(interfaceName)
                }

                classFilter.setClassName(className)
                classFilter.setInterfaceName(interfaceName)
                classFilter.setMethodName(methodName)
                classFilter.setMethodDes(methodDes)
                classFilter.setMethodVisitor(methodVisitor)
                classFilter.setIsAnnotation(isAnnotation)

                autoClassFilterList.add(classFilter)
        }

        GlobalConfig.setAutoClassFilter(autoClassFilterList)

        // 需要手动添加的包
        List<String> includePackages = GlobalConfig.getParams().include
        HashSet<String> include = new HashSet<>()
        if (includePackages != null) {
            include.addAll(includePackages)
        }
        include.add("com.xiaozhu.xzdz")
        GlobalConfig.setInclude(include)

        // 添加需要过滤的包
        List<String> excludePackages = GlobalConfig.getParams().exclude
        HashSet<String> exclude = new HashSet<>()
        // 不对系统类进行操作，避免非预期错误
        exclude.add('android.support')
        exclude.add('androidx')
        exclude.add('android.arch.lifecycle')
        exclude.add('okhttp3')
        exclude.add('okio')
        exclude.add('com.bumptech.glide')
        exclude.add('com.google.gson')
        exclude.add('com.umeng.analytics')
        exclude.add('com.umeng.commonsdk')
        exclude.add('com.tencent.mm.opensdk')
        exclude.add('retrofit2')
        exclude.add('com.sina.weibo')
        exclude.add('com.growingio')
        exclude.add('com.fasterxml')
        exclude.add('com.networkbench')
        exclude.add('filter.pack')
        exclude.add('com.xiaozhu.xzdz.support.activity.XZFKBaseActivity')
        if (excludePackages != null) {
            exclude.addAll(excludePackages)
        }
        GlobalConfig.setExclude(exclude)
    }

}