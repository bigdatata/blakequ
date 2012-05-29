/*
 * IntCell.h
 *
 *  Created on: 2012-5-29
 *      Author: Administrator
 */

#ifndef INTCELL_H_
#define INTCELL_H_

class IntCell{
	public:
		explicit IntCell(int initValue = 0);
		//复制构造函数
		IntCell(const IntCell & rhs);
		~IntCell();
		//重载运算符
		const IntCell& operator=(const IntCell& rhs);

		int read() const;
		void write(int x);
	private:
		int *storeValue;
};

#endif /* INTCELL_H_ */
