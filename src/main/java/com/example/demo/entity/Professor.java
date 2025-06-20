package com.example.demo.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "professors")
@Getter
@Setter
public class Professor {

    @Column(name = "profId")
    @Id 
    @GeneratedValue
    private Integer prof_id;

    @Column(name = "profName")
    private String profName;

    //mappedBy to avoid duplicate mapping; bidirectional association between Course and Professor
    //may have one or multiple more courses
    @ManyToMany(mappedBy = "professors")
    private Set<Course> courses;

    @Override
    public String toString() {
        return "Professor{" +
                "prof_id=" + prof_id +
                ", profName='" + profName + '\'' +
                '}';
    }

}
