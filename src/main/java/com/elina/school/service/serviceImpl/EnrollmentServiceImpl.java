package com.elina.school.service.serviceImpl;

import com.elina.school.exception.NotFoundException;
import com.elina.school.model.Course;
import com.elina.school.model.Enrollment;
import com.elina.school.model.Professor;
import com.elina.school.model.Student;
import com.elina.school.repository.*;
import com.elina.school.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("enrollmentService")
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {
    private EnrollmentRepository enrollmentRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private ProfessorRepository professorRepository;
    @Autowired
    public void setEnrollmentRepository(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Autowired
    public void setProfessorRepository(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    /*
    @Override
    public void save(LocalDateTime enroll_date, Long course_id) {
        Enrollment enrollment = new Enrollment();
        Course courseToUpdate = courseRepository.getById(course_id);
        enrollment.setEnroll_date(enroll_date);
        enrollment.setCourse(courseToUpdate);
        courseToUpdate.getEnrollments().add(enrollment);

        enrollmentRepository.save(enrollment);
        courseRepository.save(courseToUpdate);
    }

    @Override
    public Enrollment findById(Long enrollment_id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollment_id);

        if(optionalEnrollment.isPresent())
            return optionalEnrollment.get();
        else
            throw new NotFoundException("Enrollment Not Found");
    }

    @Override
    public List<Enrollment> findAll() {
        return (List<Enrollment>) enrollmentRepository.findAll();
    }

    @Override
    public void addStudents(List<Long> student_ids, Long enrollment_id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollment_id);
        List<Optional<Student>> studentsToAdd = new ArrayList<>();
        student_ids.forEach(student_id -> studentsToAdd.add(studentRepository.findById(student_id)));

        studentsToAdd.forEach(student -> {
            if(student.isPresent()&&optionalEnrollment.isPresent()){
                Enrollment enrollmentToUpdate = optionalEnrollment.get();
                Student studentToAdd = student.get();
                if(studentToAdd.getEnrollments().size()<=2){//Max 3 enrollments
                    enrollmentToUpdate.getStudents().add(studentToAdd);
                    enrollmentRepository.save(enrollmentToUpdate);
                    studentToAdd.getEnrollments().add(enrollmentToUpdate);
                    studentRepository.save(studentToAdd);
                }
            }else{
                throw new NotFoundException("Entity Not Found");
            }
        });

    }

    @Override
    public void addProfessor(Long professor_id, Long enrollment_id) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollment_id);
        Optional<Professor> optionalProfessor = professorRepository.findById(professor_id);

        if(optionalProfessor.isPresent()&&optionalEnrollment.isPresent()){
            Enrollment enrollmentToUpdate = optionalEnrollment.get();
            Professor professorToAdd = optionalProfessor.get();
            if(professorToAdd.getEnrollments().size()<=3){//Max 4 enrollments
                enrollmentToUpdate.setProfessor(professorToAdd);
                enrollmentRepository.save(enrollmentToUpdate);
                professorToAdd.getEnrollments().add(enrollmentToUpdate);
                professorRepository.save(professorToAdd);
            }
            }else{
                throw new NotFoundException("Entity Not Found");
            }

    }

    @Override
    public void deleteById(Long enrollment_id) {
        if(enrollmentRepository.findById(enrollment_id).isPresent())
            enrollmentRepository.deleteById(enrollment_id);
    }
     */
}
