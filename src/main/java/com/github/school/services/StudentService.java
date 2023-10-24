package com.github.school.services;

import com.github.school.domain.DTO.StudentDTO;
import com.github.school.domain.Student;
import com.github.school.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Transactional
    public Student create(Student dto) {
        return repository.save(dto);
    }

    public StudentDTO getStudentById(Long id) {
        return repository.findById(id)
                .map(student -> new StudentDTO(student))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "estudante não encontrado"
                ));
    }

    public List<Student> listAllStudent() {
        return repository.findAll();
    }

    public void deleteStudent(Long id) {
        this.repository.findById(id)
                .map(student -> {
                repository.deleteById(id);
                return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "estudante não encontrado"
                ));
    }

    public Student updateStudent(Long id, Student studentDTO) {
        return repository.findById(id)
                .map(student -> {
                    studentDTO.setId(student.getId());
                    repository.save(studentDTO);
                    return student;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "estudante não encontrado"));
    }



}
