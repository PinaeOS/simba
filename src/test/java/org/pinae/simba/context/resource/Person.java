package org.pinae.simba.context.resource;

import org.apache.log4j.Logger;

public class Person {
	
	private static Logger log = Logger.getLogger(Person.class);
	
	private Name name;
	private int age;
	private Address address;
	private String email;
	
	private boolean admin;

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public void create(){
		log.debug("Create Person Bean");
	}
	
	public void run(){
		this.age ++;
	}
	
	public void destroy(){
		log.debug("Destory Person Bean");
	}
	
}
