package com.github.school.domain.DTO;


import com.github.school.annotations.Logger;
import com.github.school.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teste {

    @Logger
    private List<Student> students;

    @Logger
    private String name;

    @Logger
    private String lastName;

}
