#include<jni.h>
#include <string.h>
#include<android/log.h>
//JNIEnvָ����JVM�����ģ�����Native��c/c++��������Javaִ��ջ�е�����
jstring JNICALL Java_com_hao_FileBrowser_fileMD5(JNIEnv *env, jobject thiz, jstring path)
{
  	 return (*env)->NewStringUTF(env, "Hello from JNI !");
}