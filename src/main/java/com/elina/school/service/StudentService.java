package com.elina.school.service;

import com.elina.school.model.Professor;
import com.elina.school.model.Status;
import com.elina.school.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    void save(Student newStudent);

    Student findByName(String studentName);

    Student findById(Long studentId);

    void deleteById(Long studentId);

    void addAptitudeToStudent(Long studentId, Long aptitudeId);

    void deleteAptitudeFromStudent(Long studentId, Long aptitudeId);

    void addCourseToStudent(Long studentId, Long courseId);

    void deleteCourseFromStudent(Long studentId, Long courseId);

}
