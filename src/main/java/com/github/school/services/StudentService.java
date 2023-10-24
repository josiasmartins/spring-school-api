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
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Transactional
    public Student create(Student dto) {
        return repository.save(dto);
    }

    public Optional<Student> getStudentById(Long id) {
        return repository.findById(id);
    }

    public List<Student> listAllStudent() {
        return repository.findAll();
    }

    public void deleteStudent(Long id) {
        repository.deleteById(id);
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

//    public static Student updateFields(Student updateStudet, Student oldPlanet) {
//        String name = updatePlanet.getName() != null ? updatePlanet.getName() : oldPlanet.getName();
//        String climate = updatePlanet.getClimate() != null ? updatePlanet.getClimate() : oldPlanet.getClimate();
//        String terrain = updatePlanet.getTerrain() != null ? updatePlanet.getTerrain() : oldPlanet.getTerrain();
//        Planet planet = new Planet(name, climate, terrain);
//        planet.setId(oldPlanet.getId());
//        return planet;
//    }




}
