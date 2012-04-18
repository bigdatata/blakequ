//============================================================================
// Name        : HelloCpp.cpp
// Author      :
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <stdio.h>
#include <string.h>
using namespace std;

template <class T>
/**
 * ��ӡ������
 */
void printfBinary(T a)
{
    int i;
    for (i = sizeof(a) * 8 - 1; i >= 0; --i)
    {
        if ((a >> i) & 1)
            putchar('1');
        else
            putchar('0');
        if (i == 8)
            putchar(' ');
    }
    putchar('\n');
}

/**
 * ͳ�ƶ�������1�ĸ���
 */
void countBinary(){
	printf("������1�ĸ��� --- by MoreWindows( http://blog.csdn.net/MoreWindows )  ---\n\n");
//	unsigned short a = 34520;
	unsigned short a = 34720;
	printfBinary(a);

	//��һ����ÿ2λΪһ�飬���ڸߵ�λ���:10 00 01 10  11 01 10 00-->01 00 01 01  10 01 01 00
	a = ((a & 0xAAAA)>>1) + (a & 0x5555);
	//�ڶ�����ÿ4λΪһ�飬���ڸߵ�λ���:0100 0101 1001 0100-->0001 0010 0011 0001
	a = ((a & 0xCCCC)>>2) + (a & 0x3333);
	//��������ÿ8λΪһ�飬���ڸߵ�λ���:00010010 00110001-->00000011 00000100
	a = ((a & 0xF0F0)>>4) + (a & 0x0F0F);
	//���Ĳ���ÿ16λΪһ�飬���ڸߵ�λ���:00000011 00000100-->00000000 00000111
	a = ((a & 0xFF00)>>8) + (a & 0x00FF);
	//�������õ���00000000 00000111��7��34520��������1�ĸ�����
	printfBinary(a);
}

/**
 * �ܶ�ɶԳ������ֱ����ڴ����ļ��У�ע��ɶԵ����ֲ�һ�������ڵģ�
 * ��2, 3, 4, 3, 4, 2����������������һ��������ʧ�ˣ���ξ�����ҵ����ĸ�������ʧ�ˣ�
 * ������һ��������ʧ�ˣ��Ǳض���һ����ֻ����һ�ζ����������ֶ�������ż���Ρ�
 * ������������û��Ҫ�ˣ��������������������ԡ���
 * 1.�Լ����Լ������Ϊ0��
 * 2.������㽻���ɡ�
 * ������ǽ���Щ����ȫ���һ�飬�����һ�����Ǹ�������һ�����Ǹ���
 */
void findLostNum(){
	printf("ȱʧ������ 6--- by MoreWindows( http://blog.csdn.net/MoreWindows )  ---\n\n");
	const int MAXN = 15;
	int a[MAXN] = {1, 347, 6, 9, 13, 65, 889, 712, 889, 347, 1, 9, 65, 13, 712};
	int lostNum = 0;
	for (int i = 0; i < MAXN; i++)
	     lostNum ^= a[i];
	printf("ȱʧ������Ϊ:  %d\n", lostNum);
}

/**
 * ����������
 */
void reverseBinary(){
	printf("���������� --- by MoreWindows( http://blog.csdn.net/MoreWindows )  ---\n\n");
	printf("����ǰ:    ");
	unsigned short a = 34520;
	printfBinary(a);

	printf("�����:    ");
	//��һ����ÿ2λΪһ�飬���ڸߵ�λ����:10 00 01 10  11 01 10 00-->01 00 10 01 11 10 01 00
	a = ((a & 0xAAAA)>>1) | ((a & 0x5555)<<1);
	//�ڶ�����ÿ4λΪһ�飬���ڸߵ�λ����2λ���2λ������:0100 1001 1110 0100-->0001 0110 1011 0001
	a = ((a & 0xCCCC)>>2) | ((a & 0x3333)<<2);
	//��������ÿ8λΪһ�飬���ڸߵ�λ����4λ���4λ������:00010110 10110001-->01100001 00011011
	a = ((a & 0xF0F0)>>4) | ((a & 0x0F0F)<<4);
	//���Ĳ���ÿ16λΪһ�飬���ڸߵ�λ����8λ���8λ������:01100001 00011011-->00011011 01100001
    a = ((a & 0xFF00)>>8) | ((a & 0x00FF)<<8);
    printfBinary(a);
}

int main()
{
    printf("�ߵ�λ���� --- by MoreWindows( http://blog.csdn.net/MoreWindows )  ---\n\n");

    printf("����ǰ:    ");
    unsigned short a = 3344520;
    printfBinary(a);

    printf("������:    ");
    a = (a >> 8) | (a << 8);
    printfBinary(a);

    //����
    reverseBinary();
    countBinary();
    findLostNum();
    return 0;
}
