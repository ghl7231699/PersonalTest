#include <jni.h>
#include <string>
#include "stdlib.h"
#include "string.h"
#include "android/log.h"

#define TAG "ndk_jni" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型


/**
 * 首先将a强制声明为指向整数的指针，读取指针对应的整数
 * @param a
 * @param b
 * @return
 */
int compare(const void *a, const void *b) {
    //将a强转成int*类型的，通过* 获取值
    return (*(int *) a - *(int *) b);
}

extern "C" jstring
Java_com_example_liangge_rxjavatest_ndk_NdkActivity_updateNameFromC(JNIEnv *env,
                                                                    jobject instance) {
    //获取jclass
    jclass pJclass = env->GetObjectClass(instance);

    //获取fileId
    jfieldID fieldID = env->GetFieldID(pJclass, "name", "Ljava/lang/String;");

    //获取field
    jstring res = (jstring) env->GetObjectField(instance, fieldID);
    printf("%s\n", res);

    //转换成java String 类型
    char *string = (char *) env->GetStringUTFChars(res, NULL);
    char text[20] = "雪";

    char *result = strcat(string, text);
    return env->NewStringUTF(result);
}


extern "C" void
Java_com_example_liangge_rxjavatest_ndk_NdkActivity_getArray(JNIEnv *env, jobject instance,
                                                             jintArray arrays_) {
    jint *arrays = env->GetIntArrayElements(arrays_, NULL);
    //获取数组长度
    int length = env->GetArrayLength(arrays_);

    //(void*, size_t, size_t, int (*)(const void*, const void*))
    qsort(arrays, length, sizeof(int), compare);

    env->ReleaseIntArrayElements(arrays_, arrays, 0);
}


extern "C" jstring
Java_com_example_liangge_rxjavatest_ndk_NdkActivity_getMethod(JNIEnv *env, jobject instance) {

    //获取jclass
    jclass pJclass = env->GetObjectClass(instance);

    //获取fileId方法名称
    jmethodID jmethodID = env->GetMethodID(pJclass, "getName", "()Ljava/lang/String;");

    //jni调用java 方法
    jstring method = (jstring) env->CallObjectMethod(instance, jmethodID);

    //转换成java String 类型
    char *string = (char *) env->GetStringUTFChars(method, NULL);
    char text[20] = "success";

    char *result = strcat(string, text);
    return env->NewStringUTF(result);
}

//删除局部引用
extern "C" void
Java_com_example_liangge_rxjavatest_ndk_NdkActivity_getLocalReference(JNIEnv *env,
                                                                      jobject instance) {
    for (int i = 0; i < 100; i++) {
        jclass pJclass = env->FindClass("java/util/Date");
        jobject pJobject = env->NewObject(pJclass, env->GetMethodID(pJclass, "<init>", "(V)"));
        //对象操作
        env->DeleteLocalRef(pJobject);
    }
}


extern "C" void
Java_com_example_liangge_rxjavatest_ndk_NdkActivity_exception(JNIEnv *env, jobject instance) {
    //构建一个异常
    jclass pJclass = env->GetObjectClass(instance);
    jfieldID pJfieldID = env->GetFieldID(pJclass, "name2", "Ljava/lang/String;");
    //检测异常
    jthrowable pJthrowable = env->ExceptionOccurred();
    if (pJthrowable != NULL) {
        //为了保证java的代码能继续执行，清除异常
        env->ExceptionClear();
        pJfieldID = env->GetFieldID(pJclass, "name", "Ljava/lang/String;");
    }
    jstring _jstring = (jstring) env->GetObjectField(instance, pJfieldID);
    char *str = (char *) env->GetStringUTFChars(_jstring, NULL);
    //c++有异常，java层面如何try catch
    if (strcmp(str, "www") != 0) {
        //抛出java 异常
        jclass newThrow = env->FindClass("java/lang/IllegalArgumentException");

        env->ThrowNew(newThrow, "illegal argument,please check it");
    }

}

extern "C"
void
Java_com_example_liangge_rxjavatest_ndk_NdkActivity_cache(JNIEnv
                                                          *env,
                                                          jobject instance
) {

    jclass pJclass = env->GetObjectClass(instance);
//局部静态变量，访问内存空间
    static jfieldID jfieldID = NULL;
    if (jfieldID == NULL) {
        jfieldID = env->GetFieldID(pJclass, "name", "Ljava/lang/String;");
        LOGI(".........jfieldID.........\n");
    }

}

extern "C"
void
Java_com_example_liangge_rxjavatest_ndk_NdkActivity_init(JNIEnv
                                                         *env,
                                                         jclass type
) {

    static jfieldID jfieldID = NULL;
    if (jfieldID == NULL) {
        jfieldID = env->GetFieldID(type, "name", "Ljava/lang/String;");
        LOGI(".........jfieldID.........\n");
    }

}

extern "C"
void
Java_com_example_liangge_rxjavatest_ndk_CustomFix_HandlerNative_replaceMethod(JNIEnv
                                                                              *env,
                                                                              jclass type,
                                                                              jobject
                                                                              method,
                                                                              jobject dest
) {

// TODO

}

extern "C"
void
Java_com_example_liangge_rxjavatest_ndk_CustomFix_HandlerNative_init(JNIEnv
                                                                     *env,
                                                                     jclass type,
                                                                     jint
                                                                     apiCode) {

// TODO

}