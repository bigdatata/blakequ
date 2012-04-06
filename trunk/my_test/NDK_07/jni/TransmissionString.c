#include <string.h>
#include <jni.h>
#include <android/log.h>

JNIEnv* jniEnv;


//----------------------------------------------------------------
//----------------------------------------------------------------
//----------------------------------------------------------------


jstring
Java_com_duicky_Transmission_transferString( JNIEnv* env,jobject thiz,jstring msg )
{
	if(jniEnv == NULL) {
		jniEnv = env;
	}

	char data[128];
	memset(data, 0, sizeof(data));
	char *c_msg = NULL;
	c_msg = (char *)(*jniEnv)->GetStringUTFChars(jniEnv, msg, 0);

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "C JNI  ---- > %s",c_msg);

	return (*jniEnv)->NewStringUTF(jniEnv, "This is send by C JNI");
}














