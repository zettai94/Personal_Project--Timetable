package com.example.demo.service;

import java.io.*;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
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
            Workbook workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet contains the course data

            for(Row row: sheet)
            {
                
            }
            
            // Create Campus first
            // only create Campus if it does not exist in the database

            //Create Professor next; if there's "," then there's multiple professors
            // Professor can have same name but different id depending on the course
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
