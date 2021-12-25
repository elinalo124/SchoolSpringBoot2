package com.elina.school.repository;

import com.elina.school.model.Course;
import com.elina.school.model.Professor;
import com.elina.school.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findStudentByFirstNameOrLastName(String firstName, String lastName);
}
