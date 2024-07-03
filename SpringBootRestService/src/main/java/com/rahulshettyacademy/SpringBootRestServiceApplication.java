package com.rahulshettyacademy;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rahulshettyacademy.controller.Library;
import com.rahulshettyacademy.repository.LibraryRepository;



@SpringBootApplication
public class SpringBootRestServiceApplication {
	
	@Autowired
	LibraryRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestServiceApplication.class, args);
	}
	
//	@Override
//	public void run(String[] args)
//	{
//		Library lib= repository.findById("fdsefr343").get();
//		System.out.println(lib.getAuthor());
//		Library en =new Library();
//		en.setAisle(123);
//		en.setAuthor("Rahul");
//		en.setBook_name("Devops");
//		en.setIsbn("lkhs");
//		en.setId("lkhs123");
//		//repository.save(en);
//		List<Library> allrecords =repository.findAll();
//		//
//		for(Library item : allrecords)
//		{
//			System.out.println(item.getBook_name());
//		}
//		repository.delete(en);
//		
//		}
	
	
	
	   

}
