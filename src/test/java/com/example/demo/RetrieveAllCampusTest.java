package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.Campus;
import com.example.demo.repository.CampusRepository;
import com.example.demo.service.CampusService;

public class RetrieveAllCampusTest {
    
    @Mock
    private CampusRepository mockCampusRepo;

    @InjectMocks
    private CampusService campusService;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp()
    {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        closeable.close();
    }

    @Test
    public void testRetrieveAllCampus()
    {
        List<Campus> expected = List.of(new Campus(1, "Rockville"),
                                        new Campus(2, "Distance Learning"));
        
        when(mockCampusRepo.findAll()).thenReturn(expected);

        List<Campus> result = campusService.retrieveAllCampuses();

        assertEquals(2, result.size());
        assertEquals("Rockville", result.get(0).getCampusName());
        assertEquals("Distance Learning", result.get(1).getCampusName());
        verify(mockCampusRepo, times(1)).findAll();
        
    }
}
