package com.github.school.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_student")
public class Student {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "first_semestre_grade")
    private String firstSemesterGrade;

    @Column(name = "second_semestre_grade")
    private String secondSemesterGrade;

    @Column(name = "name_teacher")
    private String nameTeacher;

    @Column(name = "room_number")
    private String roomNumber;


}
