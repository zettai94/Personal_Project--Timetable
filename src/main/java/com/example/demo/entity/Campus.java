package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Entity
@Table(name = "campuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Campus {

    @Column(name = "campusId")
    @Id 
    @GeneratedValue
    private Integer campusId;

    @Column(name = "campusName")
    private String campusName;

    @Override
    public String toString() {
        return "Campus{" +
                "campusId=" + campusId +
                ", campusName='" + campusName + '\'' +
                '}';
    }
}
