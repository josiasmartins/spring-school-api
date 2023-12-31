package com.github.school.domain;


import com.github.school.annotations.Logger;
import com.github.school.annotations.Searcheable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_student")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Logger
    @Column(name = "name")
    @Searcheable(alias = "naruto")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "first_semestre_grade")
    @Logger
    private String firstSemesterGrade;

    @Logger
    @Column(name = "second_semestre_grade")
    private String secondSemesterGrade;

    @Logger
    @Column(name = "name_teacher")
    private String nameTeacher;

    @Logger
    @Column(name = "room_number")
    private String roomNumber;


}
