//============================================================================
// Name        : SJJG.cpp
// Author      : quhao
// Version     :
// Copyright   : v1.0
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include "IntCell.h"
using namespace std;

int main() {
	cout<<"--------------"<<endl;
	IntCell cell1, cell2(10), cell3(cell2);
	//在构造函数中使用了explicit关键字，避免了隐身转换，故而不能用下面的方式
//	cell1 = 2;
	cout << cell1.read() <<" "<< cell2.read() <<" "<< cell3.read()<<endl;
	cell1 = cell2;
	cell3.write(3);
	cout << cell1.read() <<" "<< cell2.read() <<" "<< cell3.read()<<endl;
	return 0;
}
