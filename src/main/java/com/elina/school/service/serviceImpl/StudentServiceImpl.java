package com.elina.school.service.serviceImpl;

import com.elina.school.exception.NotFoundException;
import com.elina.school.model.*;
import com.elina.school.model.Student;
import com.elina.school.repository.*;
import com.elina.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

    private AptitudeRepository aptitudeRepository;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private ProfessorRepository professorRepository;
    private AptitudeService aptitudeService;
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private StudentService studentService;
    private ProfessorService professorService;

    @Autowired
    public void setAptitudeRepository(AptitudeRepository aptitudeRepository) {
        this.aptitudeRepository = aptitudeRepository;
    }
    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Autowired
    public void setEnrollmentRepository(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Autowired
    public void setProfessorRepository(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
    @Autowired
    public void setAptitudeService(AptitudeService aptitudeService) {
        this.aptitudeService = aptitudeService;
    }
    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    @Autowired
    public void setEnrollmentService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
    @Autowired
    public void setProfessorService(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Override
    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }
    
    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findByName(String studentName) {
        Optional<Student> optionalStudent = studentRepository.findStudentByFirstNameOrLastName(studentName, studentName);

        if(optionalStudent.isPresent())
            return optionalStudent.get();
        else
            throw new NotFoundException("Student Not Found");
    }

    @Override
    public Student findById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if(optionalStudent.isPresent())
            return optionalStudent.get();
        else
            throw new NotFoundException("Student Not Found");
    }


    @Override
    public void deleteById(Long studentId) {
        if(enrollmentRepository.findAllByStudentId(studentId).isPresent()){
            List<Enrollment> enrollmentsToDelete = enrollmentRepository.findAllByStudentId(studentId).get();
            for (Enrollment enrollmentToDelete : enrollmentsToDelete){
                enrollmentRepository.delete(enrollmentToDelete);
            }
        }
        if(studentRepository.findById(studentId).isPresent())
            courseRepository.deleteById(studentId);
    }

    @Override
    public void addAptitudeToStudent(Long studentId, Long aptitudeId) {
        Student studentToUpdate = studentService.findById(studentId);
        Aptitude aptitudeToAdd = aptitudeService.findById(aptitudeId);
        studentToUpdate.getAptitudes().add(aptitudeToAdd);
        aptitudeToAdd.getStudents().add(studentToUpdate);
        studentRepository.save(studentToUpdate);
        aptitudeRepository.save(aptitudeToAdd);

        //Delete the enrollments that the student no longer needs
        for (Enrollment enrollmentToUpdate : studentToUpdate.getEnrollments()){
            List<Aptitude> commonAptitudes = new ArrayList<Aptitude>(studentToUpdate.getAptitudes());
            commonAptitudes.retainAll(enrollmentToUpdate.getCourse().getAptitudes());
            if((enrollmentToUpdate.getCourse().getStatus().getName()=="Not Started")
                    &&(commonAptitudes.size()==enrollmentToUpdate.getCourse().getAptitudes().size())){
                enrollmentRepository.delete(enrollmentToUpdate);
            }
        }
    }

    @Override
    public void deleteAptitudeFromStudent(Long studentId, Long aptitudeId) {
        Student studentToUpdate = studentService.findById(studentId);
        Aptitude aptitudeToRemove = aptitudeService.findById(aptitudeId);
        studentToUpdate.getAptitudes().remove(aptitudeToRemove);
        aptitudeToRemove.getCourses().remove(studentToUpdate);
        studentRepository.save(studentToUpdate);
        aptitudeRepository.save(aptitudeToRemove);
    }

    @Override //Students can only choose a course if they don't have all the aptitudes
    public void addCourseToStudent(Long studentId, Long courseId) {
        Student studentToUpdate = studentService.findById(studentId);
        Course courseToAdd = courseService.findById(courseId);

        List<Aptitude> commonAptitudes = new ArrayList<Aptitude>(studentToUpdate.getAptitudes());
        commonAptitudes.retainAll(courseToAdd.getAptitudes());

        if((courseToAdd.getStatus().getName()=="Not Started")
                &&(commonAptitudes.size()!=courseToAdd.getAptitudes().size())){
            Enrollment newEnrollment = new Enrollment();
            newEnrollment.setCourse(courseService.findById(courseId));
            newEnrollment.setStudent(studentToUpdate);

            studentToUpdate.getEnrollments().add(newEnrollment);
            courseToAdd.getEnrollments().add(newEnrollment);

            enrollmentRepository.save(newEnrollment);
            studentRepository.save(studentToUpdate);
            courseRepository.save(courseToAdd);
        }
    }

    @Override //Students can only quit a course if it has not started
    public void deleteCourseFromStudent(Long studentId, Long courseId) {
        Student studentToUpdate = studentService.findById(studentId);
        Course courseToRemove = courseService.findById(courseId);

        if((courseToRemove.getStatus().getName()=="Not Started")){
            Enrollment enrollmentToDelete = enrollmentRepository.findEnrollmentByCourse_IdAndStudent_Id(courseId, studentId);
            studentToUpdate.getEnrollments().remove(enrollmentToDelete);
            courseToRemove.getEnrollments().remove(enrollmentToDelete);

            enrollmentRepository.delete(enrollmentToDelete);
            studentRepository.save(studentToUpdate);
            courseRepository.save(courseToRemove);
        }
    }

}
