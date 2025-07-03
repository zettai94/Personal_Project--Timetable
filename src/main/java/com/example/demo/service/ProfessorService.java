package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.entity.Professor;

@Service
public class ProfessorService 
{
    private final ProfessorRepository professorRepo;
    
    @Autowired
    public ProfessorService(ProfessorRepository professorRepo) {
        this.professorRepo = professorRepo;
    }

    //Create a new Professor in the database
    public Professor getOrCreateProfessor(String name)
    {
        if(professorRepo.findByProfName(name) != null) {
            return professorRepo.findByProfName(name);
        }
        else
        {
            Professor newProf = new Professor();
            newProf.setProfName(name);
            return professorRepo.save(newProf);
        }
        
        //to be edited later; throw exception if name already exists
    }

    //retrieve all the professors in the database
    public List<Professor> retrieveAllProfessors() {
        return (List<Professor>) professorRepo.findAll();
    }
}
