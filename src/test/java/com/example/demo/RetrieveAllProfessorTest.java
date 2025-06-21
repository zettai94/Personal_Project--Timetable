package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.Professor;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.service.ProfessorService;

public class RetrieveAllProfessorTest {

    @Mock
    private ProfessorRepository mockProfRepo;

    @InjectMocks
    private ProfessorService profService;

    private AutoCloseable closeable;
    
    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testRetrieveAllProfessors() {
        List<Professor> expected = List.of(new Professor(1, "Dr. Smith"),
                                            new Professor(2, "Prof. Johnson"));

        when(mockProfRepo.findAll()).thenReturn(expected);

        List<Professor> result = profService.retrieveAllProfessors();

        assertEquals(2, result.size());
        assertEquals("Dr. Smith", result.get(0).getProfName());
        assertEquals("Prof. Johnson", result.get(1).getProfName());
        verify(mockProfRepo, times(1)).findAll();
    }
}