package com.elina.school.repository;

import com.elina.school.model.Course;
import com.elina.school.model.Professor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {
    Optional<Professor> findProfessorByFirstNameOrLastName(String firstName, String lastName);
}
