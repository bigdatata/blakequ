#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include<android/log.h>
#define ARRAY_LENGTH  3

//JNIEnv指针是JVM创建的，用于Native的c/c++方法操纵Java执行栈中的数据
jobjectArray JNICALL Java_com_tencent_FileMD5DemoActivity_fileMD5(JNIEnv *env, jobject thiz, jstring path)
{
	jclass objClass = (*env)->FindClass(env, "java/lang/String");
	jobjectArray files = (*env)->NewObjectArray(env,(jsize)ARRAY_LENGTH, objClass, 0);
	jstring jstr;
	char* sa[] = {"你好","googd","张三"};
	int i=0;
	for(; i<ARRAY_LENGTH; i++){
		jstr = (*env)->NewStringUTF(env, sa[i]);
		(*env)->SetObjectArrayElement(env, files, i, jstr);
	}
	  char* p = js2c(env, path);
  	return files;
  //return (*env)->NewObjectArray(env,0, objClass, 0);
	/*
	char buf[128];
  const jbyte *str;
  str = (*env)->GetStringUTFChars(env,path,NULL);
  if(str==NULL){
		return NULL;
  }
  printf("%s",str);
  (*env)->ReleaseStringUTFChars(env,path,str);
  scanf("%127s", buf);
  return (*env)->NewStringUTF(env, buf);*/
  	 //return (*env)->NewStringUTF(env, "Hello from JNI !");
}