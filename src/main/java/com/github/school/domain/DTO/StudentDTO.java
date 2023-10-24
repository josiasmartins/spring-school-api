package com.github.school.domain.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO {
    private String name;
    private Integer age;
    private String firstSemesterGrade;
    private String secondSemesterGrade;
    private String nameTeacher;
    private String roomNumber;


}
