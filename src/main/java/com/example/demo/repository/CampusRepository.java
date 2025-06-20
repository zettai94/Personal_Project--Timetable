package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Campus;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer>
{
    Campus findByCampusName(String campusName);
}
    