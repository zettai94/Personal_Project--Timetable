package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;;

@Entity
@Table(name = "campuses")
@NoArgsConstructor
@AllArgsConstructor
public class Campus {

    @Column(name = "campusId")
    @Id 
    @GeneratedValue
    private Integer campusId;

    @Column(name = "campusName")
    private String campusName;
}
