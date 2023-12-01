package com.github.school.domain.DTO;

import com.github.school.annotations.Logger;
import com.github.school.domain.Student;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String name;
    private Integer age;
    @Logger
    private String firstSemesterGrade;
    private String secondSemesterGrade;
    private String nameTeacher;
    private String roomNumber;
//    public StudentDTO(
//            String name,
//            Integer age,
//            String firstSemesterGrade,
//            String secondSemesterGrade,
//            String nameTeacher,
//            String roomNumber
//    ) {
//        this.name = name;
//        this.age = age;
//        this.firstSemesterGrade = firstSemesterGrade;
//        this.secondSemesterGrade = secondSemesterGrade;
//        this.nameTeacher = nameTeacher;
//        this.roomNumber = roomNumber;
//    }


    public StudentDTO(Student student) {
        this.name = student.getName();
        this.age = student.getAge();
        this.firstSemesterGrade = getFirstSemesterGrade();
        this.secondSemesterGrade = getSecondSemesterGrade();
        this.nameTeacher = getNameTeacher();
        this.roomNumber = getRoomNumber();
    }

}
