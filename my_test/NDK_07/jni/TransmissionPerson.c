#include <string.h>
#include <jni.h>
#include <android/log.h>

extern JNIEnv* jniEnv;

jclass Person;
jobject mPerson;
jmethodID getName;
jmethodID setName;
jmethodID getAge;
jmethodID setAge;
jmethodID toString;

int InitPerson();
void ToString();
void GetName();
void GetAge();
void SetName();
void SetAge();
//----------------------------------------------------------------
//----------------------------------------------------------------
//----------------------------------------------------------------

jobject
Java_com_duicky_Transmission_transferPerson( JNIEnv* env,jobject thiz,jobject person )
{
	if(jniEnv == NULL) {
		jniEnv = env;
	}

	if (Person == NULL || getName == NULL || setName == NULL || getAge == NULL
			|| setAge == NULL || toString == NULL) {
		if (1 != InitPerson()) {
			return NULL;
		}
	}

	mPerson =  person;

	if(mPerson == NULL) {
		return NULL;
	}

	GetName();
	GetAge();
	ToString();
	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "Begin Modify mPerson  .... " );
	SetName();
	SetAge();
	ToString();

	return mPerson;
}


//----------------------------------------------------------------
//----------------------------------------------------------------
//----------------------------------------------------------------


/**
 * ��ʼ�� �ࡢ����
 */
int InitPerson() {

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "InitPerson Begin  " );

	if(jniEnv == NULL) {
		return 0;
	}

	if(Person == NULL) {
		Person = (*jniEnv)->FindClass(jniEnv,"com/duicky/Person");
		if(Person == NULL){
			return -1;
		}
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "InitPerson Begin  2 ok" );
	}

	if (getName == NULL) {
		getName = (*jniEnv)->GetMethodID(jniEnv, Person, "getName","()Ljava/lang/String;");
		if (getName == NULL) {
			(*jniEnv)->DeleteLocalRef(jniEnv, Person);
			return -2;
		}
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "InitPerson Begin  4 ok" );
	}

	if (setName == NULL) {
		setName = (*jniEnv)->GetMethodID(jniEnv, Person, "setName","(Ljava/lang/String;)V");
		if (setName == NULL) {
			(*jniEnv)->DeleteLocalRef(jniEnv, Person);
			(*jniEnv)->DeleteLocalRef(jniEnv, getName);
			return -2;
		}
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "InitPerson Begin  4 ok" );
	}


	if (getAge == NULL) {
		getAge = (*jniEnv)->GetMethodID(jniEnv, Person, "getAge","()I");
		if (getAge == NULL) {
			(*jniEnv)->DeleteLocalRef(jniEnv, Person);
			(*jniEnv)->DeleteLocalRef(jniEnv, getName);
			(*jniEnv)->DeleteLocalRef(jniEnv, setName);
			return -2;
		}
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "InitPerson Begin  4 ok" );
	}

	if (setAge == NULL) {
		setAge = (*jniEnv)->GetMethodID(jniEnv, Person, "setAge","(I)V");
		if (setAge == NULL) {
			(*jniEnv)->DeleteLocalRef(jniEnv, Person);
			(*jniEnv)->DeleteLocalRef(jniEnv, getName);
			(*jniEnv)->DeleteLocalRef(jniEnv, setName);
			(*jniEnv)->DeleteLocalRef(jniEnv, getAge);
			return -2;
		}
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "InitPerson Begin  4 ok" );
	}


	if (toString == NULL) {
		toString = (*jniEnv)->GetMethodID(jniEnv, Person, "toString","()Ljava/lang/String;");
		if (toString == NULL) {
			(*jniEnv)->DeleteLocalRef(jniEnv, Person);
			(*jniEnv)->DeleteLocalRef(jniEnv, getName);
			(*jniEnv)->DeleteLocalRef(jniEnv, setName);
			(*jniEnv)->DeleteLocalRef(jniEnv, getAge);
			(*jniEnv)->DeleteLocalRef(jniEnv, setAge);
			return -2;
		}
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "InitPerson Begin  4 ok" );
	}

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "InitPerson End" );
	return 1;
}



/**
 * GetName  ��ӦPerson��getName����
 */
void GetName() {

	if(Person == NULL || getName == NULL) {
		if(1 != InitPerson()){
			return;
		}
	}

	jstring jstr = NULL;
	char* cstr = NULL;
	//���÷���
	jstr = (*jniEnv)->CallObjectMethod(jniEnv, mPerson, getName);
	cstr = (char*) (*jniEnv)->GetStringUTFChars(jniEnv,jstr, 0);
	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "getName  ---- >  %s",cstr );
	//�ͷ���Դ
	(*jniEnv)->ReleaseStringUTFChars(jniEnv, jstr, cstr);
	(*jniEnv)->DeleteLocalRef(jniEnv, jstr);

}

/**
 * GetAge ��ӦPerson��getName����
 */
void GetAge() {

	if(Person == NULL || getName == NULL) {
		if(1 != InitPerson()){
			return;
		}
	}
	//���÷���
	jint age = (*jniEnv)->CallIntMethod(jniEnv, mPerson, getAge);
	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "getAge  ---- >  %d",age );

}

/**
 * SetName ��ӦPerson��setName����
 */
void SetName() {

	if(Person == NULL || setName == NULL) {
		if(1 != InitPerson()){
			return;
		}
	}

	jstring jstr = (*jniEnv)->NewStringUTF(jniEnv, "Modify Name");
	//���÷���
	(*jniEnv)->CallVoidMethod(jniEnv, mPerson, setName,jstr);
	(*jniEnv)->DeleteLocalRef(jniEnv, jstr);

}

int age = 20;
/**
 * SetAge ��ӦPerson��setAge����
 */
void SetAge() {

	if(Person == NULL || setAge == NULL) {
		if(1 != InitPerson()){
			return;
		}
	}
	//���÷���
	(*jniEnv)->CallVoidMethod(jniEnv, mPerson, setAge,age++);

}


/**
 * ToString ��Ӧ Person �� toString ���� , ��ӡ�������Ϣ
 */
void ToString() {

	if(Person == NULL || toString == NULL) {
		if(1 != InitPerson()){
			return;
		}
	}

	jstring jstr = NULL;
	char* cstr = NULL;
	//���÷���
	jstr = (*jniEnv)->CallObjectMethod(jniEnv, mPerson, toString);
	cstr = (char*) (*jniEnv)->GetStringUTFChars(jniEnv,jstr, 0);
	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "C JNI toString  ---- >  %s",cstr );
	(*jniEnv)->ReleaseStringUTFChars(jniEnv, jstr, cstr);
	(*jniEnv)->DeleteLocalRef(jniEnv, jstr);

}
