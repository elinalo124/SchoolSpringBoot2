package com.elina.school.service.serviceImpl;

import com.elina.school.exception.NotFoundException;
import com.elina.school.model.Aptitude;
import com.elina.school.model.Course;
import com.elina.school.model.Professor;
import com.elina.school.repository.*;
import com.elina.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("professorService")
@Transactional
public class ProfessorServiceImpl implements ProfessorService {

    private AptitudeRepository aptitudeRepository;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;
    private ProfessorRepository professorRepository;
    private StudentRepository studentRepository;
    private AptitudeService aptitudeService;
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private ProfessorService professorService;
    private StudentService studentService;

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
    public void setProfessorRepository(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    public void setProfessorService(ProfessorService professorService) {
        this.professorService = professorService;
    }
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<Professor> findAll() {
        return (List<Professor>) professorRepository.findAll();
    }

    @Override
    public void save(Professor newProfessor) {
        professorRepository.save(newProfessor);
    }

    @Override
    public Professor findByName(String professorName) {
        Optional<Professor> optionalProfessor = professorRepository.findProfessorByFirstNameOrLastName(professorName, professorName);

        if(optionalProfessor.isPresent())
            return optionalProfessor.get();
        else
            throw new NotFoundException("Professor Not Found");
    }

    @Override
    public Professor findById(Long professorId) {
        Optional<Professor> optionalProfessor = professorRepository.findById(professorId);

        if(optionalProfessor.isPresent())
            return optionalProfessor.get();
        else
            throw new NotFoundException("Professor Not Found");
    }

    @Override
    public void deleteById(Long professorId) {
        if(courseRepository.findCourseByProfessorId(professorId).isPresent()){
            List<Course> coursesToUpdate = courseRepository.findCourseByProfessorId(professorId).get();
            for (Course courseToUpdate : coursesToUpdate){
                courseToUpdate.setProfessor(null);
                courseRepository.save(courseToUpdate);
            }
        }
        if(professorRepository.findById(professorId).isPresent())
            courseRepository.deleteById(professorId);
    }

    @Override
    public void addAptitudeToProfessor(Long professorId, Long aptitudeId) {
        Professor professorToUpdate = professorService.findById(professorId);
        Aptitude aptitudeToAdd = aptitudeService.findById(aptitudeId);
        professorToUpdate.getAptitudes().add(aptitudeToAdd);
        aptitudeToAdd.getProfessors().add(professorToUpdate);
        professorRepository.save(professorToUpdate);
        aptitudeRepository.save(aptitudeToAdd);
    }

    //Add business logic para que los courses que si borro un aptitude, se actualicen los courses si no da la cantidad de aptitude
    @Override
    public void deleteAptitudeFromProfessor(Long professorId, Long aptitudeId) {
        Professor professorToUpdate = professorService.findById(professorId);
        Aptitude aptitudeToRemove = aptitudeService.findById(aptitudeId);
        professorToUpdate.getAptitudes().remove(aptitudeToRemove);
        aptitudeToRemove.getCourses().remove(professorToUpdate);
        professorRepository.save(professorToUpdate);
        aptitudeRepository.save(aptitudeToRemove);
    }

    @Override //Professors only teach a course if they have all the aptitudes that the course offers
    public void addCourseToProfessor(Long professorId, Long courseId) {
        Professor professorToUpdate = professorService.findById(professorId);
        Course courseToAdd = courseService.findById(courseId);

        List<Aptitude> commonAptitudes = new ArrayList<Aptitude>(professorToUpdate.getAptitudes());
        commonAptitudes.retainAll(courseToAdd.getAptitudes());
        if((courseToAdd.getStatus().getName()=="Not Started")&&(commonAptitudes.size()==courseToAdd.getAptitudes().size())){
            professorToUpdate.getCourses().add(courseToAdd);
            courseToAdd.setProfessor(professorToUpdate);
            professorRepository.save(professorToUpdate);
            courseRepository.save(courseToAdd);
        }

    }

    @Override //Professors can only removed during the Not Started period of the course
    public void deleteCourseFromProfessor(Long professorId, Long courseId) {
        Professor professorToUpdate = professorService.findById(professorId);
        Course courseToRemove = courseService.findById(courseId);

        if((courseToRemove.getStatus().getName()=="Not Started")){
            professorToUpdate.getCourses().remove(courseToRemove);
            courseToRemove.setProfessor(professorToUpdate);
            professorRepository.save(professorToUpdate);
            courseRepository.save(courseToRemove);
        }

    }

}
