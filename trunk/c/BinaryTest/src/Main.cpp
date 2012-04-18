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
 * 打印二进制
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
 * 统计二进制中1的个数
 */
void countBinary(){
	printf("二进制1的个数 --- by MoreWindows( http://blog.csdn.net/MoreWindows )  ---\n\n");
//	unsigned short a = 34520;
	unsigned short a = 34720;
	printfBinary(a);

	//第一步：每2位为一组，组内高低位相加:10 00 01 10  11 01 10 00-->01 00 01 01  10 01 01 00
	a = ((a & 0xAAAA)>>1) + (a & 0x5555);
	//第二步：每4位为一组，组内高低位相加:0100 0101 1001 0100-->0001 0010 0011 0001
	a = ((a & 0xCCCC)>>2) + (a & 0x3333);
	//第三步：每8位为一组，组内高低位相加:00010010 00110001-->00000011 00000100
	a = ((a & 0xF0F0)>>4) + (a & 0x0F0F);
	//第四步：每16位为一组，组内高低位相加:00000011 00000100-->00000000 00000111
	a = ((a & 0xFF00)>>8) + (a & 0x00FF);
	//这样最后得到的00000000 00000111即7即34520二进制中1的个数。
	printfBinary(a);
}

/**
 * 很多成对出现数字保存在磁盘文件中，注意成对的数字不一定是相邻的，
 * 如2, 3, 4, 3, 4, 2……，由于意外有一个数字消失了，如何尽快的找到是哪个数字消失了？
 * 由于有一个数字消失了，那必定有一个数只出现一次而且其它数字都出现了偶数次。
 * 用搜索来做就没必要了，利用异或运算的两个特性——
 * 1.自己与自己异或结果为0，
 * 2.异或满足交换律。
 * 因此我们将这些数字全异或一遍，结果就一定是那个仅出现一个的那个数
 */
void findLostNum(){
	printf("缺失的数字 6--- by MoreWindows( http://blog.csdn.net/MoreWindows )  ---\n\n");
	const int MAXN = 15;
	int a[MAXN] = {1, 347, 6, 9, 13, 65, 889, 712, 889, 347, 1, 9, 65, 13, 712};
	int lostNum = 0;
	for (int i = 0; i < MAXN; i++)
	     lostNum ^= a[i];
	printf("缺失的数字为:  %d\n", lostNum);
}

/**
 * 二进制逆序
 */
void reverseBinary(){
	printf("二进制逆序 --- by MoreWindows( http://blog.csdn.net/MoreWindows )  ---\n\n");
	printf("逆序前:    ");
	unsigned short a = 34520;
	printfBinary(a);

	printf("逆序后:    ");
	//第一步：每2位为一组，组内高低位交换:10 00 01 10  11 01 10 00-->01 00 10 01 11 10 01 00
	a = ((a & 0xAAAA)>>1) | ((a & 0x5555)<<1);
	//第二步：每4位为一组，组内高低位（高2位与低2位）交换:0100 1001 1110 0100-->0001 0110 1011 0001
	a = ((a & 0xCCCC)>>2) | ((a & 0x3333)<<2);
	//第三步：每8位为一组，组内高低位（高4位与低4位）交换:00010110 10110001-->01100001 00011011
	a = ((a & 0xF0F0)>>4) | ((a & 0x0F0F)<<4);
	//第四步：每16位为一组，组内高低位（高8位与低8位）交换:01100001 00011011-->00011011 01100001
    a = ((a & 0xFF00)>>8) | ((a & 0x00FF)<<8);
    printfBinary(a);
}

int main()
{
    printf("高低位交换 --- by MoreWindows( http://blog.csdn.net/MoreWindows )  ---\n\n");

    printf("交换前:    ");
    unsigned short a = 3344520;
    printfBinary(a);

    printf("交换后:    ");
    a = (a >> 8) | (a << 8);
    printfBinary(a);

    //逆序
    reverseBinary();
    countBinary();
    findLostNum();
    return 0;
}
