package com.github.school.domain.DTO;


import com.github.school.annotations.Logger;
import com.github.school.domain.Student;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teste {

    @Logger
    @Setter
    public Student students;

    @Logger
    private String name;

    @Logger
    private String lastName;

}
