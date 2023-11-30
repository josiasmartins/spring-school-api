package com.github.school.services;

import com.github.school.annotations.Searcheable;
import com.github.school.domain.DTO.StudentDTO;
import com.github.school.domain.Student;
import com.github.school.repositories.StudentRepository;
import com.github.school.utils.Loggers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Transactional
    public Student create(Student dto) {
        return repository.save(dto);
    }

    public StudentDTO getStudentById(Long id) {

        this.validateField(Student.class, "name", "i love");

        return repository.findById(id)
                .map(student -> {

                    Loggers.log(student);

                    this.validateField(Student.class, "name", null);
                    return new StudentDTO(student);
                })
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

    private void validateField(Class<?> javaClass, String fieldName, String criteriaKey) {

        try {

            // pega o nome do campo
            String field = javaClass.getDeclaredField(fieldName).getName();
            // retorna annotation com o alias
            Searcheable searcheable = javaClass.getDeclaredField(fieldName).getAnnotation(Searcheable.class);
            // se ele não encontrar o annotation @Searcheable no campo, ele lança uma exception
             Optional.ofNullable(searcheable).orElseThrow(() -> new Exception("deu error"));

            // formata o texto com %s, atribuindo o valor do segundo argumento
            String messageFormatted = String.format("Campo %s está desabilitado para consulta", criteriaKey);

            System.out.println("class: " + javaClass.getTypeName());
            System.out.println("field: " + field);
            System.out.println("Searcheable: " + searcheable);
//            System.out.println("optional: " +optional);

            System.out.println("message formated: " + messageFormatted);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
