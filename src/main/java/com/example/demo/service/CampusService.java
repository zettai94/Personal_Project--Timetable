package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CampusRepository;
import com.example.demo.entity.Campus;

@Service
public class CampusService
{
    private final CampusRepository campusRepo;

    @Autowired
    public CampusService(CampusRepository campusRepo)
    {
        this.campusRepo = campusRepo;
    }

    //create new Campus in the database
    public Campus newCampus(String campusName)
    {
        //if campusName does not exist, add to database
        if(campusRepo.findByCampusName(campusName)!= null)
        {
            Campus newCamp = new Campus();
            newCamp.setCampusName(campusName);
            return campusRepo.save(newCamp);
        }
        return null;
    }

    //retrieve all the campuses in the database
    public List<Campus> retrieveAllCampuses()
    {
        return (List<Campus>) campusRepo.findAll();
    }


}
