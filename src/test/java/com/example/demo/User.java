package com.example.demo;

public class User implements java.io.Serializable{

	String id;
	String name;
	String sex;
	int age;
	int total = 1;

	User(String id, String name, String sex, int age) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id : "+ id +",name : "+ name+",sex : "+ sex+",age : "+ age+",total : "+ total;
	}
}
