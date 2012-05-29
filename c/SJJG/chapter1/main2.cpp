/*
 * main2.cpp
 *模板的使用
 *  Created on: 2012-5-29
 *      Author: Administrator
 */
#include <iostream>
#include <string.h>
#include <vector>
using namespace std;


template <typename Object, typename Comparator>
const Object& findMax(const vector<Object> & arr, Comparator cmp){
	int max = 0;
	for(int i=0; i<arr.size(); i++){
		if(cmp.isLessThan(arr[max], arr[i])) max = i;
	}
	return arr[max];
}

class MyComparator{
public:
	bool isLessThan(const string& str1, const string& str2) const{
		return stricmp(str1.c_str(), str2.c_str());
	}
};

int main(){
	vector<string> arr(3);
	arr[0] = "SRERE"; arr[1] ="Dsted"; arr[2]="DEFDS";
	//在这里使用了类作为模板
	cout<<findMax(arr, MyComparator())<<endl;
	return 0;
}
