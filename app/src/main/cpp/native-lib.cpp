#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_module_ndk_ghl_ndkmodule_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_module_ndk_ghl_ndkmodule_MainActivity_updateNameFromC(JNIEnv *env, jobject instance) {
//jobject 代表的是 MainActivity 这个类的对象 首先获取jclass
    jclass _jclass = env->GetObjectClass(instance);
    //jfieldID GetFieldID(jclass clazz, const char* name, const char* sig)获取fieldID
    jfieldID _jfieldID = env->GetFieldID(_jclass, "name", "Ljava/lang/String;");
    //jobject obj, jfieldID fieldID
    jstring result = (jstring) env->GetObjectField(instance, _jfieldID);
    printf("%#x\n", result);
    //转换成java String类型
    char *str = (char *) env->GetStringUTFChars(result, NULL);

    char text[20] = "success";
    char * finalResult = strcat(str, text);
    return env->NewStringUTF(finalResult);
}