package com.github.school.controllers;

import com.github.school.domain.DTO.StudentDTO;
import com.github.school.domain.Student;
import com.github.school.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student) {
        return this.studentService.create(student);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable("id") Long id) {
        return this.studentService.getStudentById(id);
//                .map(student -> student)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "estudante não encontrado"
//                ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Student update(@PathVariable("id") Long id, @RequestBody Student student) {
        return this.studentService.updateStudent(id, student);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Student> listAll() {
        return this.studentService.listAllStudent();
    }


}
