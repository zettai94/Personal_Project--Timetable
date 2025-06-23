package com.example.demo.service;

import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepo;

    @Autowired
    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    //Read from excel file and save courses to the database
    public void loadCoursesFromExcel() {
        ClassPathResource resource = new ClassPathResource("import-data/Spring 2024 Class File.xlsx");
        try (InputStream in = resource.getInputStream()) 
        {
            //to be implemented: read the Excel file and save courses to the database
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
