#include<jni.h>
#include<math.h>
#include<android/log.h>
//JNIEnv指针是JVM创建的，用于Native的c/c++方法操纵Java执行栈中的数据
jfloat JNICALL Java_com_hao_NDKDemoActivity_distance(JNIEnv *env, jobject thiz, jobject a, jobject b)
{
	//first
	jclass point_class = (*env)->FindClass(env, "com/hao/Point");
	if(point_class == NULL){
		//printf("class not found");
		__android_log_write(ANDROID_LOG_INFO, "MyNdkDemo", "class Point not found");
		return 0;	
	}else{
		__android_log_write(ANDROID_LOG_INFO, "MyNdkDemo", "class Point found");	
	}
	
	//second
	jfieldID field_x = (*env)->GetFieldID(env, point_class, "x", "F");
	jfieldID field_y = (*env)->GetFieldID(env, point_class, "y", "F");
	//third
	 jfloat ax = (*env)->GetFloatField(env, a, field_x);   
   jfloat ay = (*env)->GetFloatField(env, a, field_y);   
   jfloat bx = (*env)->GetFloatField(env, b, field_x);   
   jfloat by = (*env)->GetFloatField(env, b, field_y);  
   //four
   return sqrtf(powf(bx-ax, 2)+powf(by-ay, 2));
  	
}