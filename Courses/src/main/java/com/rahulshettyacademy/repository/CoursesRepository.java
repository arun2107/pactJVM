package com.rahulshettyacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahulshettyacademy.controller.AllCourseData;

@Repository
public interface CoursesRepository extends JpaRepository<AllCourseData, String>{

}
