/*
 * IntCell.cpp
 *
 *  Created on: 2012-5-29
 *      Author: Administrator
 */
#include"IntCell.h"

IntCell::IntCell(int initValue){
	storeValue = new int(initValue);
}

IntCell::IntCell(const IntCell & rhs){
	storeValue = new int(*rhs.storeValue);
}

IntCell::~IntCell(){
	delete storeValue;
}

const IntCell& IntCell::operator=(const IntCell& rhs){
	if(this != &rhs)
		*storeValue = *rhs.storeValue;
	return *this;
}

int IntCell::read() const{
	return *storeValue;
}

void IntCell::write(int x){
	*storeValue = x;
}
