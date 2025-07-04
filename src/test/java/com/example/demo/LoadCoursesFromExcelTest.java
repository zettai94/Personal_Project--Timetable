package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.demo.entity.Course;
import com.example.demo.entity.Professor;
import com.example.demo.service.CourseService;

import jakarta.transaction.Transactional;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class LoadCoursesFromExcelTest {

    @Autowired
    private CourseService courseService;
    

    @Test
    //call on loadCoursesFromExcel() method
    public void testLoadCoursesFromExcel() {
        courseService.loadCoursesFromExcel();
        //verify with crnExists() method
        boolean result = courseService.crnExists(30001);
        assertEquals(true, result);
        Course course = courseService.getCourseByCrn(30001);
        assertEquals("INTRO TO SOCIOCULTURAL ANTHRO", course.getTitle());
        assertEquals("Distance Learning", course.getCampus().getCampusName());
        Set<Professor> profs = course.getProfessors();
        assertEquals(2, profs.size());
    }
}
