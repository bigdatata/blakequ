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
			//��ÿ���д����vectore���óߴ�
			array[i].resize(cols);
		}
	}
	//�����ص������ǲ��ɸı��
	const vector<Object>& operator[](int row) const{
		return array[row];
	}
	//�����ص������ǿ��Ըı��
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
	//ʹ��vector����matrix
	vector< vector<Object> > array;
}


#endif /* MATRIX_H_ */
