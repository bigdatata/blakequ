package com.duicky;

/**
 * 
 * 
 * @author luxiaofeng <454162034@qq.com>
 * 
 */
public class Person {

	private String name;
	private int age;

	public Person() {
		name = "";
		age = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

}
