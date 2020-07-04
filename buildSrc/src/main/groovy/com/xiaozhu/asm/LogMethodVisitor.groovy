package com.xiaozhu.asm

import com.xiaozhu.bean.LogMethodCell
import com.xiaozhu.config.LogHookConfig
import com.xiaozhu.util.LogAnalyticsUtil
import com.xiaozhu.util.Logger
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * 类描述：针对日志采集sdk埋点的方法进行修改
 * 创建人：ghl
 * 创建时间：2019/4/2
 * @version v1.0
 */
class LogMethodVisitor extends AdviceAdapter {

    public HashSet<String> visitedFragMethods
    public HashSet<String> sActivityMethods
    String methodName
    int access
    MethodVisitor methodVisitor
    String methodDesc
    String superName
    String className
    String[] interfaces
    boolean isAutoTrackViewOnClickAnnotation = false
    boolean isAutoTrackIgnoreTrackOnClick = false
    //是否已经对注解埋点
    boolean isHasInstrumented = false
    //是否已经埋点
    boolean isHasTracked = false

    LogMethodVisitor(MethodVisitor methodVisitor, int access, String name, String desc,
                     String superName, String className, String[] interfaces, HashSet<String>
                             visitedFragMethods, HashSet<String> sActivityMethods) {
        super(Opcodes.ASM5, methodVisitor, access, name, desc)
        this.methodName = name
        this.access = access
        this.methodVisitor = methodVisitor
        this.methodDesc = desc
        this.superName = superName
        this.className = className
        this.interfaces = interfaces
        this.visitedFragMethods = visitedFragMethods
        this.sActivityMethods = sActivityMethods
        Logger.info("||start scaning method：${Logger.accCode2String(access)} ${methodName}${desc}")
    }


    @Override
    void visitEnd() {
        super.visitEnd()
        if (isHasTracked) {
//            visitAnnotation("Lcom/mmc/lamandys/liba_datapick/AutoDataInstrumented;", false)
            Logger.info("||Hooked method------------->: ${methodName}${methodDesc}")
        }
//        Logger.info("||end scan method：${methodName}")
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()

        if (isAutoTrackIgnoreTrackOnClick) {
            return
        }

        /**
         * 在 android.gradle 的 3.2.1 版本中，针对 view 的 setOnClickListener 方法 的 lambda 表达式做特殊处理。
         */
        if (methodName.trim().startsWith('lambda$') && LogAnalyticsUtil.isPrivate(access) && LogAnalyticsUtil.isSynthetic(access)) {
            LogMethodCell logMethodCell = LogHookConfig.sLambdaMethods.get(desc)
            if (logMethodCell != null) {
                int paramStart = logMethodCell.paramsStart
                if (LogAnalyticsUtil.isStatic(access)) {
                    paramStart = paramStart - 1
                }
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE,
                        logMethodCell.agentName, logMethodCell.agentDesc,
                        paramStart, logMethodCell.paramsCount, logMethodCell.opcodes)
                isHasTracked = true
                return
            }
        }

        /**
         * 只处理public static protected 方法
         */
        if (!(LogAnalyticsUtil.isPublic(access) && !LogAnalyticsUtil.isStatic(access))
                && !LogAnalyticsUtil.isProtected(access)) {
            return
        }
        /**
         * 之前已经添加过埋点代码则返回
         */
        if (isHasInstrumented || isHasTracked) {
            return
        }

        /**
         * Method 描述信息
         */
        String methodNameDesc = methodName + methodDesc

        /**
         * Fragment
         * 目前支持 android/support/v4/app/ListFragment 和 android/support/v4/app/Fragment
         */
        if (LogAnalyticsUtil.isInstanceOfFragment(superName)) {
            LogMethodCell logMethodCell = LogHookConfig.sFragmentMethods.get(methodNameDesc)
            Logger.info("fragment:methodNameDesc:" + methodNameDesc)
            Logger.info("fragment:logMethodCell:" + logMethodCell)
            if (logMethodCell != null) {
                visitedFragMethods.add(methodNameDesc)
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, logMethodCell.agentName, logMethodCell.agentDesc, logMethodCell.paramsStart, logMethodCell.paramsCount, logMethodCell.opcodes)
                isHasTracked = true
                return
            }
        }

        /**
         * Activity
         */
        if (LogAnalyticsUtil.isInstanceOfActivity(superName)) {
            LogMethodCell activityMethod = LogHookConfig.sActivityMethods.get(methodNameDesc)
            if (activityMethod != null) {
                if (sActivityMethods != null) {
                    sActivityMethods.add(methodNameDesc)
                }
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, activityMethod.agentName, activityMethod.agentDesc, activityMethod.paramsStart, activityMethod.paramsCount, activityMethod.opcodes)
                isHasTracked = true
                return
            }
        }

        /**
         * Application
         */
        if (LogAnalyticsUtil.isInstanceOfApplication(superName)) {
            LogMethodCell appMethod = LogHookConfig.sApplicationMethods.get(methodNameDesc)
            if (appMethod != null) {
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, appMethod.agentName, appMethod.agentDesc, appMethod.paramsStart, appMethod.paramsCount, appMethod.opcodes)
                isHasTracked = true
                return
            }
        }

        /**
         * Menu
         * 目前支持 onContextItemSelected(MenuItem item)、onOptionsItemSelected(MenuItem item)
         */
        if (LogAnalyticsUtil.isTargetMenuMethodDesc(methodNameDesc)) {
            methodVisitor.visitVarInsn(ALOAD, 0)
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackMenuItem", "(Ljava/lang/Object;Landroid/view/MenuItem;)V", false)
            isHasTracked = true
            return
        }

        if (methodNameDesc == 'onDrawerOpened(Landroid/view/View;)V') {
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackDrawerOpened", "(Landroid/view/View;)V", false)
            isHasTracked = true
            return
        } else if (methodNameDesc == 'onDrawerClosed(Landroid/view/View;)V') {
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackDrawerClosed", "(Landroid/view/View;)V", false)
            isHasTracked = true
            return
        }

        if (className == 'android/databinding/generated/callback/OnClickListener') {
            if (methodNameDesc == 'onClick(Landroid/view/View;)V') {
                methodVisitor.visitVarInsn(ALOAD, 1)
                methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackViewOnClick", "(Landroid/view/View;)V", false)
                isHasTracked = true
                return
            }
        }

        if (className.startsWith('android') || className.startsWith('androidx')) {
            return
        }

        if (methodNameDesc == 'onItemSelected(Landroid/widget/AdapterView;Landroid/view/View;IJ)V' || methodNameDesc == "onListItemClick(Landroid/widget/ListView;Landroid/view/View;IJ)V") {
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitVarInsn(ALOAD, 2)
            methodVisitor.visitVarInsn(ILOAD, 3)
            methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackListView", "(Landroid/widget/AdapterView;Landroid/view/View;I)V", false)
            isHasTracked = true
            return
        }

        if (isAutoTrackViewOnClickAnnotation) {
            if (methodDesc == '(Landroid/view/View;)V') {
                methodVisitor.visitVarInsn(ALOAD, 1)
                methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackViewOnClick", "(Landroid/view/View;)V", false)
                isHasTracked = true
                return
            }
        }

        if (interfaces != null && interfaces.length > 0) {
            LogMethodCell logMethodCell = LogHookConfig.sInterfaceMethods.get(methodNameDesc)
            if (logMethodCell != null && interfaces.contains(logMethodCell.parent)) {
                LogAnalyticsUtil.visitMethodWithLoadedParams(methodVisitor, Opcodes.INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE
                        , logMethodCell.agentName, logMethodCell.agentDesc, logMethodCell.paramsStart, logMethodCell.paramsCount, logMethodCell.opcodes)
                isHasTracked = true
            }
        }

        if (!isHasTracked) {
            if (methodNameDesc == 'onClick(Landroid/view/View;)V') {
                methodVisitor.visitVarInsn(ALOAD, 1)
                methodVisitor.visitMethodInsn(INVOKESTATIC, LogHookConfig.LOG_ANALYTICS_BASE, "trackViewOnClick", "(Landroid/view/View;)V", false)
                isHasTracked = true
            }
        }
    }

    /**
     * 该方法是当扫描器扫描到类注解声明时进行调用
     * @param s 注解的类型。它使用的是（“L” + “类型路径” + “;”）形式表述
     * @param b 表示的是，该注解是否在 JVM 中可见
     * 1.RetentionPolicy.SOURCE：声明注解只保留在 Java 源程序中，在编译 Java 类时注解信息不会被写入到 Class。如果使用的是这个配置 ASM 也将无法探测到这个注解。
     * 2.RetentionPolicy.CLASS：声明注解仅保留在 Class 文件中，JVM 运行时并不会处理它，这意味着 ASM 可以在 visitAnnotation 时候探测到它，但是通过Class 反射无法获取到注解信息。
     * 3.RetentionPolicy.RUNTIME：这是最常用的一种声明，ASM 可以探测到这个注解，同时 Java 反射也可以取得注解的信息。所有用到反射获取的注解都会用到这个配置，就是这个原因。
     * @return
     */
    @Override
    AnnotationVisitor visitAnnotation(String s, boolean b) {
//        if (s == 'Lcom/xzdz/;') {
//            isAutoTrackViewOnClickAnnotation = true
//            Logger.info("||发现 ${methodName}${methodDesc} 有注解 @AutoTrackDataViewOnClick")
//        }
//
//        if (s == 'Lcom/mmc/lamandys/liba_datapick/AutoIgnoreTrackDataOnClick;') {
//            isAutoTrackIgnoreTrackOnClick = true
//            Logger.info("||发现 ${methodName}${methodDesc} 有注解 @AutoIgnoreTrackDataOnClick")
//        }
//
//        if (s == 'Lcom/mmc/lamandys/liba_datapick/AutoDataInstrumented;') {
//            isHasInstrumented = true
//        }

        return super.visitAnnotation(s, b)
    }
}