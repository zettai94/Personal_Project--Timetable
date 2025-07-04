package com.example.demo.service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Arrays;
import java.util.Date;

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
    private final ProfessorService professorService;

    @Autowired
    public CourseService(CourseRepository courseRepo, CampusService campService, ProfessorService professorService) {
        this.courseRepo = courseRepo;
        this.campService = campService;
        this.professorService = professorService;
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
                
                Course course;

                //Check if CRN already exists in the database
                //if CRN exist, update the course with new professors
                //Otherwise, create a new course
                Integer crn = Integer.parseInt(row.getCell(3).getStringCellValue());
            
                if(crnExists(crn)) 
                {
                    //read from the database for the Professors in current row
                    //call on updateCourseProfessors to update the course with new professors
                    Set<Professor> newProfessors = Arrays.stream(row.getCell(6).getStringCellValue().split(","))
                                                    .map(String::trim)
                                                    .filter(name -> !name.isEmpty()) // filter out empty strings if any
                                                    .map(professorService::getOrCreateProfessor) // Use getOrCreateProfessor here
                                                    .collect(Collectors.toSet());
                    course = updateCourseProfessors(crn, newProfessors);
                    
                    //assuming the repeating crn is only added due to new professors
                    //so we do not need to update other fields
                }
                else 
                {
                    //Create a new course
                    course = new Course();

                    //set CRN
                    course.setCrn(crn);

                    //set course title
                    course.setTitle(row.getCell(2).getStringCellValue());

                    //set coreq, if not null, read from cell, otherwise set to null
                    if (row.getCell(14) != null && !row.getCell(14).getStringCellValue().isEmpty()) 
                    {
                        course.setCoreq(Integer.parseInt(row.getCell(14).getStringCellValue()));
                    } 
                    else 
                    {
                        course.setCoreq(null);
                    }

                    //Create Campus first
                    //only create Campus if it does not exist in the database
                    String campusName = row.getCell(0).getStringCellValue();
                    Campus campus = campService.getOrCreateCampus(campusName);
                    course.setCampus(campus);

                    //Create Professor next; if there's "," then there's multiple professors
                    //Professor can have same name but different id depending on the course
                    String[] profNames = row.getCell(6).getStringCellValue().split(",");
                    //because professors can be empty, we need to check if profNames is empty
                    //if empty, set to empty set
                    Set<Professor> professor;
                    if(profNames.length == 0 && profNames[0].trim().isEmpty())
                    {
                        professor = new HashSet<>();
                        course.setProfessors(professor);
                    } 
                    else 
                    {
                        professor = new HashSet<>();
                        for(String prof: profNames)
                        {
                            prof = prof.trim(); //trim whitespace
                            Professor newProf = professorService.getOrCreateProfessor(prof);
                            professor.add(newProf); //add the professor to the set
                        }
                        
                    }
                    course.setProfessors(professor); // Set the professors to the course
                     
                
                    //Build room string
                    String buildingRoom = row.getCell(4).getStringCellValue();
                    if(buildingRoom.trim().equals("DL"))
                    {
                        buildingRoom += "-WEB";
                    }
                    else if (buildingRoom.trim().equals("REMOTE"))
                    {
                        //do nothing, just keep the string as is
                    }
                    else
                    {
                        buildingRoom += " " + row.getCell(5).getStringCellValue();
                    }
                    course.setRoom(buildingRoom);

                    //Set days for the course
                    //Days can be empty, so check if the cell is not null
                    //if null, set to empty string
                    if(row.getCell(7) != null && !row.getCell(7).getStringCellValue().isEmpty()) 
                    {
                        course.setDays(row.getCell(7).getStringCellValue());
                    } 
                    else 
                    {
                        course.setDays(null);
                    }
                    
                    //date formatter for both start and end dates
                    Date excelDate = row.getCell(8).getDateCellValue();
                    LocalDate startDate = excelDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    course.setStartDate(startDate);

                    excelDate = row.getCell(9).getDateCellValue();
                    LocalDate endDate = excelDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    course.setEndDate(endDate);

                    //time formatter for start and end times
                    //could be null or empty, so check if the cell is not null
                    if (row.getCell(10) == null || row.getCell(10).getStringCellValue().isEmpty() ||
                        row.getCell(11) == null || row.getCell(11).getStringCellValue().isEmpty()) 
                    {
                        course.setStartTime(null);
                        course.setEndTime(null);
                    } 
                    else 
                    {
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
                        course.setStartTime(LocalTime.parse(row.getCell(10).getStringCellValue(), timeFormatter));
                        course.setEndTime(LocalTime.parse(row.getCell(11).getStringCellValue(), timeFormatter));
                    }
                    courseRepo.save(course); //Save the course to the database
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
    
    public Set<Professor> professorSet(String[] profNames) {
        //profNames could be empty, so return an empty set
        if (profNames == null || profNames.length == 0) 
        {
            return new HashSet<>();
        }
        else
        {
            Set<Professor> professors = new HashSet<>();
            for (String profName : profNames) 
            {
                Professor newProf = new Professor();
                newProf.setProfName(profName.trim());
                professors.add(newProf);
            }
            return professors;
        }
    }

    public Course getCourseByCrn(Integer crn) {
        return courseRepo.findById(crn).orElse(null);
    }
    
}
