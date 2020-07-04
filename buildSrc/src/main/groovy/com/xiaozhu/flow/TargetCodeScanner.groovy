package com.xiaozhu.flow

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

import java.util.jar.JarEntry
import java.util.jar.JarFile


public class TargetCodeScanner {

/**
 * 用来扫描目标代码
 */

    /**
     * 需要注入代码的类
     */
    private final String TARGET_CLASS

    /**
     * 被找到的代码所实现的接品类
     */
    private final String TARGET_INTERFACE

    /**
     * 需要继承的父类
     */
    private final String TARGET_SUPER_CLASS

    /**
     * {@link #TARGET_CLASS} 所在jar包
     */
    File fileHasClass

    /**
     * 所有需要注入的类
     */
    ArrayList<String> allNeedInner = new ArrayList<>()

    TargetCodeScanner(String targetClass, String targetInterface) {
        this(targetClass, targetInterface, null)
    }

    TargetCodeScanner(String targetClass, String targetInterface, String superClass) {
        this.TARGET_CLASS = targetClass
        this.TARGET_INTERFACE = targetInterface
        this.TARGET_SUPER_CLASS = superClass
    }

    /**
     *
     * @param src 源文件
     * @param dest transform 后的jar 文件
     */
    void scanJar(File jarFile, File destFile) {
        if (!jarFile) {
            //不存在 return
            return
        }
//        def srcFilePath = jarFile.absolutePath
        def file = new JarFile(jarFile)

        //jar 内的属性
        Enumeration enumeration = file.entries()
        // 遍历jar内的文件
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement()
            String entryName = jarEntry.getName()
            if (entryName.startsWith("android/support") || !entryName.endsWith(".class")) {
                //support 不扫描
                break
            }
            if (TARGET_CLASS == entryName) {
                //找到需要注入代码的目标类
                fileHasClass = destFile
            } else {
                //如果不是目标类 则通过ASM来查找是否实现接口
                scanClass(file.getInputStream(jarEntry), jarFile.absolutePath)
            }
        }
    }

    /**
     * 通过ASM 完成相应类数据数据处理 查找父类与实现类
     * @param inputStream File级别的inputStream
     * @param filePath 相对路径
     * @return
     */
    void scanClass(InputStream inputStream, String filePath) {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        ScanClassVisitor cv = new ScanClassVisitor(Opcodes.ASM5, cw, filePath, TARGET_INTERFACE)
        cr.accept(cv, ClassReader.EXPAND_FRAMES)
        inputStream.close()
    }

    class ScanClassVisitor extends ClassVisitor {
        private String filePath
        String interfaceClass

        ScanClassVisitor(int api, ClassVisitor cv, String filePath, String interfaceClass) {
            super(api, cv)
            this.filePath = filePath
            this.interfaceClass = interfaceClass
        }

        boolean is(int access, int flag) {
            return (access & flag) == flag
        }


        void visit(int version, int access, String name, String signature,
                   String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces)
            //抽象类、接口、非public等类无法调用其无参构造方法
            if (is(access, Opcodes.ACC_ABSTRACT)
                    || is(access, Opcodes.ACC_INTERFACE)
                    || !is(access, Opcodes.ACC_PUBLIC)
            ) {
                return
            }
            if (name == TARGET_SUPER_CLASS) {
                //如果是目标父类，就不往下走了
                return
            }
            if (superName == TARGET_SUPER_CLASS) {
                allNeedInner.add(name)
            } else {
                //遍历所有接口，如果有则 目标接口，则把该类加入数组里面
                interfaces.each { it ->
                    if (it == interfaceClass) {
                        allNeedInner.add(name)
                    }

                }
            }
        }
    }
}


