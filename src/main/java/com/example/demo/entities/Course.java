package com.example.demo.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {

    @Column(name = "CRN")
    @Id
    private Integer crn;

    //course either has coreq or null
    @Column(name = "coreq")
    private Integer coreq;

    @Column(name = "course")
    private String course;

    @Column(name = "course_title")
    private String title;

    @Column(name = "Days")
    private String days;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "room")
    private String room;

    @ManyToOne
    @JoinColumn(name = "campusId")
    private Campus campus;

    //course either has one or many professors
    @ManyToMany
    @JoinTable(name = "course_professors",
                joinColumns = @JoinColumn(name = "courseCRN"),
                inverseJoinColumns = @JoinColumn(name = "profId"))
    private Set<Professor> professors;

}
