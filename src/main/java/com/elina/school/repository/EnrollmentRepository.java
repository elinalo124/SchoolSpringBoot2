package com.elina.school.repository;

import com.elina.school.model.Course;
import com.elina.school.model.Enrollment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
    Enrollment findEnrollmentByCourse_IdAndStudent_Id(Long course_id, Long student_id);
    Optional<List<Enrollment>> findAllByStudentId(Long student_id);
}
