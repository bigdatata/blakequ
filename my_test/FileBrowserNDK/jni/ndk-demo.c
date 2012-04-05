#include<jni.h>
#include <string.h>
#include<android/log.h>
//JNIEnv指针是JVM创建的，用于Native的c/c++方法操纵Java执行栈中的数据
jstring JNICALL Java_com_hao_FileBrowser_fileMD5(JNIEnv *env, jobject thiz, jstring path)
{
  	 return (*env)->NewStringUTF(env, "Hello from JNI !");
}