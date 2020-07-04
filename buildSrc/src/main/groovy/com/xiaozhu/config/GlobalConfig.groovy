package com.xiaozhu.config

import com.xiaozhu.bean.AutoClassFilter
import com.xiaozhu.bean.AutoSettingParams
import org.gradle.api.Project

/**
 * 类描述：全局配置类(build.gradle 添加属性)
 * 创建人：ghl
 * 创建时间：2019/4/2
 * @version v1.0
 */
class GlobalConfig {

    private static Project project
    private static List<AutoClassFilter> autoClassFilter
    private static HashSet<String> exclude
    private static HashSet<String> include

    static void setProject(Project project) {
        GlobalConfig.@project = project
    }

    static Project getProject() {
        return project
    }

    static AutoSettingParams getParams() {
        return project.xiaozhu
    }

    static void setAutoClassFilter(List<AutoClassFilter> filter) {
        autoClassFilter = filter
    }

    static List<AutoClassFilter> getAutoClassFilter() {
        return autoClassFilter
    }


    static boolean getIsOpenLogTrack() {
        return getParams().isOpenLogTrack
    }

    static HashSet<String> getExclude() {
        return exclude
    }

    static void setExclude(HashSet<String> exclude) {
        this.exclude = exclude
    }

    static HashSet<String> getInclude() {
        return include
    }

    static void setInclude(HashSet<String> include) {
        this.include = include
    }
}