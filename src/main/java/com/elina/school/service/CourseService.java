package com.elina.school.service;

import com.elina.school.model.Aptitude;
import com.elina.school.model.Course;
import com.elina.school.model.Professor;
import com.elina.school.model.Status;

import java.util.List;

public interface CourseService {
    /*


    void addAptitudes(List<String> aptitude_names, Long id);
    void setStatus(String status, Long id);
     */

    List<Course> findAll();
    void save(Course course);
    Course findByName(String courseName);
    Course findById(Long id);
    Course updateById(Course newCourse, Long id);
    void deleteById(Long courseId);
    void addAptitudeToCourse(Long courseId, Long aptitudeId);

    void deleteAptitudeFromCourse(Long courseId, Long aptitudeId);

    void addStudentToCourse(Long courseId, Long studentId);

    void deleteStudentFromCourse(Long courseId, Long studentId);

    void updateGrade(Long courseId, Long studentId, Integer grade);
}
