/*
 * matrix.h
 *
 *  Created on: 2012-5-29
 *      Author: Administrator
 */

#ifndef MATRIX_H_
#define MATRIX_H_

#include <vector>
using namespace std;

template <typename Object>
class matrix{
public:
	matrix(int rows, int cols) : array(rows)
	{
		for(int i=0; i<rows; i++){
			//将每个行代表的vectore重置尺寸
			array[i].resize(cols);
		}
	}
	//它返回的引用是不可改变的
	const vector<Object>& operator[](int row) const{
		return array[row];
	}
	//它返回的引用是可以改变的
	vector<Object>& operator[](int row){
		return array[row];
	}

	int numrows() const{
		return array.size();
	}

	int numcols() const{
		return numrows()? array[0].size():0;
	}

private:
	//使用vector构造matrix
	vector< vector<Object> > array;
}


#endif /* MATRIX_H_ */
