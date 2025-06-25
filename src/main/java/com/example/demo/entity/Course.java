package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;
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

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "endTime")
    private LocalTime endTime;

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

    @Override
    public String toString()
    {
        String profList = professors != null? professors.stream().map(Professor::getProfName)
                            .collect(Collectors.joining(", ")) : "N/A";
        return "Course{" +
                "crn=" + crn +
                ", coreq=" + coreq +
                ", course=" + course +
                ", title=" + title +
                ", days=" + days +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", room=" + room +
                ", campus=" + (campus != null ? campus.getCampusName() : "null") +
                ", professors=" + profList;

    }
}
