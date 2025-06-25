package com.example.demo.service;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Campus;
import com.example.demo.entity.Course;
import com.example.demo.entity.Professor;
import com.example.demo.repository.CourseRepository;

@Service
public class CourseService {
    
    private final CourseRepository courseRepo;
    private final CampusService campService;

    @Autowired
    public CourseService(CourseRepository courseRepo, CampusService campService) {
        this.courseRepo = courseRepo;
        this.campService = campService;
    }

    //Read from excel file and save courses to the database
    public void loadCoursesFromExcel() {
        ClassPathResource resource = new ClassPathResource("import-data/Spring 2024 Class File.xlsx");
        try (InputStream in = resource.getInputStream()) 
        {
            Workbook workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheetAt(0); //Assuming the first sheet contains the course data

            //Excel sheet may contain multiple duplicates:
            //Check if CRN already exists, if so, check if professors are the same
            //Professor set can be empty. If empty, continue to next row
            for(Row row: sheet)
            {
                //skip header row
                if(row.getRowNum() == 0) 
                {
                    continue; 
                }
                
                //Create Course object
                Course course = new Course();

                //Check if CRN already exists in the database
                Integer crn = (int) row.getCell(1).getNumericCellValue();
                //if CRN exists, check if professors are the same
                //check if Professor Set is not empty
                if(crnExists(crn)) {
                    Course existingCourse = courseRepo.findById(crn).orElse(null);
                    //update 
                }
                else {
                    
                }

                //Create Campus first
                //only create Campus if it does not exist in the database
                String campusName = row.getCell(0).getStringCellValue();
                Campus campus = campService.getOrCreateCampus(campusName);
                

                //Create Professor next; if there's "," then there's multiple professors
                //Professor can have same name but different id depending on the course
                Set<Professor> professors = new HashSet<>();
                String[] profNames = row.getCell(7).getStringCellValue().split(",");
                for(String profName: profNames)
                {
                    //trim whitespace and create new Professor
                    String name = profName.trim();
                    Professor newProf = new Professor();
                    newProf.setProfName(name);
                    if(professors.stream().noneMatch(p -> p.getProfName().equals(name))) {
                        professors.add(newProf);
                    }
                }
                
                //Build room string
                String buildingRoom = row.getCell(5).getStringCellValue();
                if(buildingRoom.trim().equals("DL"))
                {
                    buildingRoom += "-WEB";
                }
                else if (buildingRoom.trim().equals("REMOTE"))
                {
                    continue; //skip remote courses
                }
                else
                {
                    buildingRoom += " " + row.getCell(6).getStringCellValue();
                }
            }
            

        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Check if CRN exists in the database
    public boolean crnExists(Integer crn) {
        return courseRepo.existsById(crn);
    }

    //Check if Professors are the same
    //If not the same, update the course by adding the new professors
    public Course updateCourseProfessors(Integer crn, Set<Professor> newProfessors) {
        Course course = courseRepo.findById(crn).orElse(null);
        if (course != null) {
            Set<Professor> existingProfessors = course.getProfessors();
            for (Professor newProf: newProfessors) {
                //Check if the professor already exists in the course
                if (existingProfessors.stream().noneMatch(p -> p.getProfName().equals(newProf.getProfName()))) {
                    existingProfessors.add(newProf);
                }
            }
            // Update the course with the new set of professors
            course.setProfessors(existingProfessors);
        }
        return courseRepo.save(course);
    }
    
    
}
