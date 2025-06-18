package com.example.demo.entity;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "professors")
public class Professor {

    @Column(name = "profId")
    @Id @GeneratedValue
    private Integer prof_id;

    @Column(name = "profName")
    private String name;

    //mappedBy to avoid duplicate mapping; bidirectional association between Course and Professor
    //may have one or multiple more courses
    @ManyToMany(mappedBy = "professors")
    private Set<Course> courses;

}
