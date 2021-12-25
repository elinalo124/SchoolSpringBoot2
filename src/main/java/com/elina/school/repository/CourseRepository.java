package com.elina.school.repository;

import com.elina.school.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseByCourseName(String courseName);
    Optional<List<Course>> findCourseByProfessorId(Long professorId);
}
