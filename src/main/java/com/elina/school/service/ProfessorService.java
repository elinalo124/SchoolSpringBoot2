package com.elina.school.service;

import com.elina.school.model.Course;
import com.elina.school.model.Professor;
import com.elina.school.model.Status;

import java.util.List;

public interface ProfessorService {
    List<Professor> findAll();

    void save(Professor newProfessor);

    Professor findByName(String professorName);

    Professor findById(Long professorId);

    void deleteById(Long professorId);

    void addAptitudeToProfessor(Long professorId, Long aptitudeId);

    void deleteAptitudeFromProfessor(Long professorId, Long aptitudeId);

    void addCourseToProfessor(Long professorId, Long courseId);

    void deleteCourseFromProfessor(Long professorId, Long courseId);
    /*
    void save(Professor professor); //first and last name
    Professor findById(Long professor_id);
    List<Professor> findAll();
    void addAptitudes(List<String> aptitude_names, Long professor_id);
    void deleteAptitudes(List<String> aptitude_names, Long professor_id);
    void deleteById(Long professor_id);

     */
}
