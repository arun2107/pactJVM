package com.rahulshettyacademy.controller;

import org.springframework.stereotype.Component;

@Component
public class AllCourseDetails {

	private  int price;
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String course_name;
	private String category;
	private  String id;
	
	
	
}
