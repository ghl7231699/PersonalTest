package com.xiaozhu.inject


public class InjectInfo {
    /**
     * 注入的目标文件
     * eg."com/xiaozhu/xzdz/router/lib/Router"
     */
    String targetClass

    /**
     * {@link #targetClass} 所在Jar包
     */
    File targetFile

    /**
     * 修改的方法
     * <clinit> 是静态代码块
     */
    String initMethodName

    /**
     * 被实现的接口
     * eg
     */
    String interfaceClass

    /**
     * 所有实现{@link #interfaceClass}接口的类
     */
    ArrayList<String> allInter = new ArrayList<>()

    /**
     * 实现的父类
     */
    String superClass

    /**
     * 被调用的方法
     * eg. bindService
     */
    String invokingMethodName



}