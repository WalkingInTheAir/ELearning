package com.elearning.domain;

public class Student extends User{
	
	private Clas clas;
	public Clas getClas() {
		return clas;
	}
	public void setClas(Clas clas) {
		this.clas = clas;
	}
}
