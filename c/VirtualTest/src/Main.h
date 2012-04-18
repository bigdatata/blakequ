/*
 * Main.h
 *
 *  Created on: 2012-4-18
 *      Author: Administrator
 */

#ifndef MAIN_H_
#define MAIN_H_
#include <iostream>
using namespace std;

class Main {
public:
	Main(){cout<<"main construct----"<<endl;};
	virtual ~Main(){cout<<"main destructor----"<<endl;};
	virtual void foo(){cout<<"main foo--------"<<endl;};
};

#endif /* MAIN_H_ */
