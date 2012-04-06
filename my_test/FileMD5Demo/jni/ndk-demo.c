/*============================================================================
* 类名 ：nkd-demo.c
* 作者 ：quhao ,reference hehao
* 版本 ：0.1
* 说明 ：扫描计算指定目录所有文件的MD5值，实现环境winXP+cygwin
==============================================================================*/ 
#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <dirent.h>
#include <time.h>
#include<android/log.h>
#define F(x, y, z) (((x) & (y)) | ((~x) & (z)))
#define G(x, y, z) (((x) & (z)) | ((y) & (~z)))
#define H(x, y, z) ((x) ^ (y) ^ (z))
#define I(x, y, z) ((y) ^ ((x) | (~z)))
#define RL(x, y) (((x) << (y)) | ((x) >> (32 - (y)))) //x向左循环移y位
#define PP(x) (x<<24)|((x<<8)&0xff0000)|((x>>8)&0xff00)|(x>>24) //将x高低位互换,例如PP(aabbccdd)=ddccbbaa

#define FF(a, b, c, d, x, s, ac) a = b + (RL((a + F(b,c,d) + x + ac),s))
#define GG(a, b, c, d, x, s, ac) a = b + (RL((a + G(b,c,d) + x + ac),s))
#define HH(a, b, c, d, x, s, ac) a = b + (RL((a + H(b,c,d) + x + ac),s))
#define II(a, b, c, d, x, s, ac) a = b + (RL((a + I(b,c,d) + x + ac),s))

unsigned A,B,C,D,a,b,c,d,i,len,flen[2],x[16];   //i临时变量,len文件长,flen[2]为64位二进制表示的文件初始长度

FILE *fp;
int count=0;//计算文件数
/*结构体用于存储文件名称和MD5值*/
typedef struct _FileInfo
{
	char paths[256];
	char MD5[256];
}FileInfo;

FileInfo g_FileInfo[2048];


/**************************************************************************
* 函数原型：md5()
* 函数功能：计算文件的MD5值
* 函数参数：无
* 返回值  ：无
***************************************************************************/ 
void md5()
{
    a=A,b=B,c=C,d=D;
    /* Round 1 */
    FF (a, b, c, d, x[ 0], 7, 0xd76aa478); /**//* 1 */
    FF (d, a, b, c, x[ 1], 12, 0xe8c7b756); /**//* 2 */
    FF (c, d, a, b, x[ 2], 17, 0x242070db); /**//* 3 */
    FF (b, c, d, a, x[ 3], 22, 0xc1bdceee); /**//* 4 */
    FF (a, b, c, d, x[ 4], 7, 0xf57c0faf); /**//* 5 */
    FF (d, a, b, c, x[ 5], 12, 0x4787c62a); /**//* 6 */
    FF (c, d, a, b, x[ 6], 17, 0xa8304613); /**//* 7 */
    FF (b, c, d, a, x[ 7], 22, 0xfd469501); /**//* 8 */
    FF (a, b, c, d, x[ 8], 7, 0x698098d8); /**//* 9 */
    FF (d, a, b, c, x[ 9], 12, 0x8b44f7af); /**//* 10 */
    FF (c, d, a, b, x[10], 17, 0xffff5bb1); /**//* 11 */
    FF (b, c, d, a, x[11], 22, 0x895cd7be); /**//* 12 */
    FF (a, b, c, d, x[12], 7, 0x6b901122); /**//* 13 */
    FF (d, a, b, c, x[13], 12, 0xfd987193); /**//* 14 */
    FF (c, d, a, b, x[14], 17, 0xa679438e); /**//* 15 */
    FF (b, c, d, a, x[15], 22, 0x49b40821); /**//* 16 */

    /* Round 2 */
    GG (a, b, c, d, x[ 1], 5, 0xf61e2562); /**//* 17 */
    GG (d, a, b, c, x[ 6], 9, 0xc040b340); /**//* 18 */
    GG (c, d, a, b, x[11], 14, 0x265e5a51); /**//* 19 */
    GG (b, c, d, a, x[ 0], 20, 0xe9b6c7aa); /**//* 20 */
    GG (a, b, c, d, x[ 5], 5, 0xd62f105d); /**//* 21 */
    GG (d, a, b, c, x[10], 9, 0x02441453); /**//* 22 */
    GG (c, d, a, b, x[15], 14, 0xd8a1e681); /**//* 23 */
    GG (b, c, d, a, x[ 4], 20, 0xe7d3fbc8); /**//* 24 */
    GG (a, b, c, d, x[ 9], 5, 0x21e1cde6); /**//* 25 */
    GG (d, a, b, c, x[14], 9, 0xc33707d6); /**//* 26 */
    GG (c, d, a, b, x[ 3], 14, 0xf4d50d87); /**//* 27 */
    GG (b, c, d, a, x[ 8], 20, 0x455a14ed); /**//* 28 */
    GG (a, b, c, d, x[13], 5, 0xa9e3e905); /**//* 29 */
    GG (d, a, b, c, x[ 2], 9, 0xfcefa3f8); /**//* 30 */
    GG (c, d, a, b, x[ 7], 14, 0x676f02d9); /**//* 31 */
    GG (b, c, d, a, x[12], 20, 0x8d2a4c8a); /**//* 32 */

    /* Round 3 */
    HH (a, b, c, d, x[ 5], 4, 0xfffa3942); /**//* 33 */
    HH (d, a, b, c, x[ 8], 11, 0x8771f681); /**//* 34 */
    HH (c, d, a, b, x[11], 16, 0x6d9d6122); /**//* 35 */
    HH (b, c, d, a, x[14], 23, 0xfde5380c); /**//* 36 */
    HH (a, b, c, d, x[ 1], 4, 0xa4beea44); /**//* 37 */
    HH (d, a, b, c, x[ 4], 11, 0x4bdecfa9); /**//* 38 */
    HH (c, d, a, b, x[ 7], 16, 0xf6bb4b60); /**//* 39 */
    HH (b, c, d, a, x[10], 23, 0xbebfbc70); /**//* 40 */
    HH (a, b, c, d, x[13], 4, 0x289b7ec6); /**//* 41 */
    HH (d, a, b, c, x[ 0], 11, 0xeaa127fa); /**//* 42 */
    HH (c, d, a, b, x[ 3], 16, 0xd4ef3085); /**//* 43 */
    HH (b, c, d, a, x[ 6], 23, 0x04881d05); /**//* 44 */
    HH (a, b, c, d, x[ 9], 4, 0xd9d4d039); /**//* 45 */
    HH (d, a, b, c, x[12], 11, 0xe6db99e5); /**//* 46 */
    HH (c, d, a, b, x[15], 16, 0x1fa27cf8); /**//* 47 */
    HH (b, c, d, a, x[ 2], 23, 0xc4ac5665); /**//* 48 */

    /* Round 4 */
    II (a, b, c, d, x[ 0], 6, 0xf4292244); /**//* 49 */
    II (d, a, b, c, x[ 7], 10, 0x432aff97); /**//* 50 */
    II (c, d, a, b, x[14], 15, 0xab9423a7); /**//* 51 */
    II (b, c, d, a, x[ 5], 21, 0xfc93a039); /**//* 52 */
    II (a, b, c, d, x[12], 6, 0x655b59c3); /**//* 53 */
    II (d, a, b, c, x[ 3], 10, 0x8f0ccc92); /**//* 54 */
    II (c, d, a, b, x[10], 15, 0xffeff47d); /**//* 55 */
    II (b, c, d, a, x[ 1], 21, 0x85845dd1); /**//* 56 */
    II (a, b, c, d, x[ 8], 6, 0x6fa87e4f); /**//* 57 */
    II (d, a, b, c, x[15], 10, 0xfe2ce6e0); /**//* 58 */
    II (c, d, a, b, x[ 6], 15, 0xa3014314); /**//* 59 */
    II (b, c, d, a, x[13], 21, 0x4e0811a1); /**//* 60 */
    II (a, b, c, d, x[ 4], 6, 0xf7537e82); /**//* 61 */
    II (d, a, b, c, x[11], 10, 0xbd3af235); /**//* 62 */
    II (c, d, a, b, x[ 2], 15, 0x2ad7d2bb); /**//* 63 */
    II (b, c, d, a, x[ 9], 21, 0xeb86d391); /**//* 64 */

    A += a;
    B += b;
    C += c;
    D += d;
}

	
/**************************************************************************
* 函数原型：md5_calc(char*, int)
* 函数功能：遍历文件并计算MD5值
* 函数参数：dir：遍历目录；depth：遍历深度
* 返回值  ：无
***************************************************************************/ 
void md5_calc(char *dir, int depth)
{
    DIR *dp;
    struct dirent *entry;
    struct stat statbuf;
		/*判断是否能打开文件*/
    if((dp = opendir(dir)) == NULL) {
        __android_log_write(ANDROID_LOG_INFO, "FileMD5Demo", "cannot open directory");
        return;
    }
    chdir(dir);
    while((entry = readdir(dp)) != NULL) {
        stat(entry->d_name,&statbuf);
        if(S_ISDIR(statbuf.st_mode)){
            /* 查找目录但是忽略 .和 .. */
            if(strcmp(".",entry->d_name) == 0 || strcmp("..",entry->d_name) == 0)
                continue;
            /* 递归遍历 */
            md5_calc(entry->d_name,depth+4);
        }else{
          
          	if (!(fp=fopen(entry->d_name,"rb"))) {
           		 __android_log_write(ANDROID_LOG_INFO, "FileMD5Demo", "cannot open file");
           		continue;
           	} 
           	
            fseek(fp, 0, SEEK_END); //文件指针转到文件末尾
            /*ftell函数返回long,最大为2GB,超出返回-1*/
            if((len=ftell(fp))==-1){
            	__android_log_write(ANDROID_LOG_INFO, "FileMD5Demo", "Can not calculate files which larger than 2 GB!");
            	fclose(fp);
            	continue;
            }
            
            rewind(fp); //文件指针复位到文件头
            A=0x67452301,B=0xefcdab89,C=0x98badcfe,D=0x10325476; //初始化链接变量
            flen[1]=len/0x20000000;     //flen单位是bit len是文件长度
            flen[0]=(len%0x20000000)*8;
            memset(x,0,64);   //初始化x数组为0
            fread(&x,4,16,fp); //以4字节为一组,读取16组数据
            for(i=0;i<len/64;i++){    //循环运算直至文件结束
                md5();
                memset(x,0,64);
                fread(&x,4,16,fp);
            }
            ((char*)x)[len%64]=128; //文件结束补1,补0操作,128二进制即10000000
            if(len%64>55) md5(),memset(x,0,64);
            memcpy(x+14,flen,8);    //文件末尾加入原文件的bit长度
            md5();
            fclose(fp);
            strcpy(g_FileInfo[count].paths, entry->d_name);
            sprintf(g_FileInfo[count].MD5, "%08x%08x%08x%08x", PP(A),PP(B),PP(C),PP(D));
            count++;//计算文件数量
            /*设置扫描文件最多300个*/
            if(count>300) break;
       }   
    }
    chdir("..");
    closedir(dp);
}


/**************************************************************************
* 函数原型：Java_com_tencent_FileMD5DemoActivity_fileMD5(JNIEnv *, jobject)
* 函数功能：计算文件的MD5值
* 函数参数：env：jni环境；thiz：java类(或对象)本身
* 返回值  ：包含文件的MD5信息的数组
***************************************************************************/ 
jobjectArray JNICALL Java_com_tencent_FileNative_fileMD5(JNIEnv *env, jobject thiz)
{
		struct stat sbuf;
 		char sdirect[1000] = "mnt/sdcard/";//默认存储卡目录
    DIR *dir_s;
    dir_s=opendir(sdirect);
    if(dir_s==NULL)
    {
       __android_log_write(ANDROID_LOG_INFO, "FileMD5Demo", "This directory don't exist");
       return NULL;
     }
     if(stat(sdirect,&sbuf)!=0)
     {
        __android_log_write(ANDROID_LOG_INFO, "FileMD5Demo", "Get status error");
        return NULL;
     }
		 md5_calc(sdirect,0); //调用函数
	   char sa[1024] = {0};
		 int i=0;
		 jstring jstr;
		 jclass objClass = (*env)->FindClass(env, "java/lang/String");
		 jobjectArray files = (*env)->NewObjectArray(env,count, objClass, 0);
		 for(; i<count; i++){
		 		/*构造文件md5信息*/
				strcpy(sa, g_FileInfo[i].paths);
				strcat(sa, ",");
				strcat(sa, g_FileInfo[i].MD5);
				jstr = (*env)->NewStringUTF(env, sa);
				(*env)->SetObjectArrayElement(env, files, i, jstr);//放入数组
		 }
  	 return files;
}
