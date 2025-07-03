package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.demo.service.CourseService;

import jakarta.transaction.Transactional;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@Commit // Ensures data is committed instead of rolled back
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
    }
}
