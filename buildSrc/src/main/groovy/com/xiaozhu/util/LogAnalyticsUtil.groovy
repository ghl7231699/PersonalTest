package com.xiaozhu.util

import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.MethodVisitor

/**
 * 类描述：日志分析工具类
 * 创建人：ghl
 * 创建时间：2019/4/2
 * @version v1.0
 */
class LogAnalyticsUtil implements Opcodes {
    private static final HashSet<String> targetFragmentClass = new HashSet()
    private static final HashSet<String> targetMenuMethodDesc = new HashSet()
    private static final HashSet<String> targetActivityMethodDesc = new HashSet()
    //基类
    private static final HashSet<String> targetBaseMethodDesc = new HashSet()
    private static final HashSet<String> targetApplicationMethodDesc = new HashSet<>()
    private static final HashSet<String> targetLoginActivityMethodDesc = new HashSet<>()

    static {
        /**
         * Menu
         */
        targetMenuMethodDesc.add("onContextItemSelected(Landroid/view/MenuItem;)Z")
        targetMenuMethodDesc.add("onOptionsItemSelected(Landroid/view/MenuItem;)Z")
        targetMenuMethodDesc.add("onNavigationItemSelected(Landroid/view/MenuItem;)Z")

        /**
         * Fragment
         */
        targetFragmentClass.add('android/support/v4/app/Fragment')
        targetFragmentClass.add('android/support/v4/app/ListFragment')
        targetFragmentClass.add('android/support/v4/app/DialogFragment')
        targetFragmentClass.add('com/xiaozhu/xzdz/publish/fragment/BaseLuPhotoFragment')
        targetFragmentClass.add('com/xiaozhu/xzdz/support/fragment/BaseFragment')

        /**
         * For AndroidX Fragment
         */
        targetFragmentClass.add('androidx/fragment/app/Fragment')
        targetFragmentClass.add('androidx/fragment/app/ListFragment')
        targetFragmentClass.add('androidx/fragment/app/DialogFragment')

        /**
         * Activity
         */
        targetActivityMethodDesc.add('android/app/Activity')
        targetActivityMethodDesc.add('android/support/v7/app/AppCompatActivity')
        targetActivityMethodDesc.add('com/xiaozhu/xzdz/support/activity/XZFKBaseActivity')
        /**
         * 基类
         */
        targetBaseMethodDesc.add('com/xiaozhu/xzdz/support/activity/XZFKBaseActivity')
        /**
         * Application
         */

        targetApplicationMethodDesc.add('android/app/Application')

        /**
         * 启动页
         */
        targetLoginActivityMethodDesc.add("com/xiaozhu/xzdz/splash/activity/XZSplashPageActivity")
    }

    static boolean isSynthetic(int access) {
        return (access & ACC_SYNTHETIC) != 0
    }

    static boolean isPrivate(int access) {
        return (access & ACC_PRIVATE) != 0
    }

    static boolean isPublic(int access) {
        return (access & ACC_PUBLIC) != 0
    }

    static boolean isProtected(int access) {
        return (access & ACC_PROTECTED) != 0
    }

    static boolean isStatic(int access) {
        return (access & ACC_STATIC) != 0
    }

    static boolean isTargetMenuMethodDesc(String nameDesc) {
        return targetMenuMethodDesc.contains(nameDesc)
    }

    static boolean isTargetFragmentClass(String className) {
        return targetFragmentClass.contains(className)
    }

    static boolean isInstanceOfFragment(String superName) {
        return targetFragmentClass.contains(superName)
    }

    static boolean isInstanceOfActivity(String superName) {
        return targetActivityMethodDesc.contains(superName) && !targetBaseMethodDesc.contains(superName)
    }

    static boolean isInstanceOfApplication(String superName) {
        return targetApplicationMethodDesc.contains(superName)
    }

    static boolean isLauncherActivity(String superName) {
        return targetLoginActivityMethodDesc.contains(superName)
    }

    static void visitMethodWithLoadedParams(MethodVisitor methodVisitor, int opcode, String owner, String methodName, String methodDesc, int start, int count, List<Integer> paramOpcodes) {
        for (int i = start; i < start + count; i++) {
            methodVisitor.visitVarInsn(paramOpcodes[i - start], i)
        }
        methodVisitor.visitMethodInsn(opcode, owner, methodName, methodDesc, false)
    }
}
