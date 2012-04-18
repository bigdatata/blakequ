/*
 * Dog.cpp
 *
 *  Created on: 2012-4-18
 *      Author: Administrator
 */

#ifndef DOG_H_
#define DOG_H_
#include <iostream>
#include "Main.h"
using namespace std;

class Dog:public Main{
	public:
		Dog(){cout<<"dog construct----"<<endl;};
		~Dog(){cout<<"dog destructor----"<<endl;};
		void foo(){cout<<"dog foo ---------"<<endl;};
};

#endif
