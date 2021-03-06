package com.elina.school.service.serviceImpl;

import com.elina.school.exception.NotFoundException;
import com.elina.school.model.*;
import com.elina.school.repository.*;
import com.elina.school.service.*;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService{

    private AptitudeRepository aptitudeRepository;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;
    private ProfessorRepository professorRepository;
    private StudentRepository studentRepository;
    private StatusRespository statusRespository;
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
    public void setStatusRespository(StatusRespository statusRespository) {
        this.statusRespository = statusRespository;
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

    /*SCHEDULED METHOD*/
    //UPDATE STATUS FOR COURSES BASED ON DATES
    //@SchedulerLock(name = "myscheduledTask")
    //@Scheduled(fixedRate = 10000) //10s
    //@Async
    //@Scheduled(cron = "@hourly")
    @Scheduled(fixedDelay = 2000)
    //@Scheduled(fixedRate = 3000)
    public void refreshStatus(){
        System.out.println("Running scheduled method");
        for (Course course : courseService.findAll()){
            if(course.getStartDate().isAfter(LocalDateTime.now()))
                course.setStatus(statusRespository.findByName("Not Started"));
            if(course.getStartDate().isBefore(LocalDateTime.now()) && course.getEndDate().isAfter(LocalDateTime.now()))
                course.setStatus(statusRespository.findByName("In Progress"));
            if(course.getEndDate().isBefore(LocalDateTime.now())&&(course.getStatus()!=statusRespository.findByName("Completed")))
                course.setStatus(statusRespository.findByName("Ended"));
            courseService.save(course);
        }


    }

    /*CONTROLLER METHODS*/
    @Override
    public void save(Course course){
        courseRepository.save(course);
    }

    @Override
    public Course findById(Long id){
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if(optionalCourse.isPresent())
            return optionalCourse.get();
        else
            throw new NotFoundException("Course Not Found");
    }

    @Override
    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    @Override
    public Course findByName(String courseName) {
        Optional<Course> optionalCourse = courseRepository.findCourseByCourseName(courseName);

        if(optionalCourse.isPresent())
            return optionalCourse.get();
        else
            throw new NotFoundException("Course Not Found");
    }
    @Override
    public Course updateById(Course newCourse, Long id) {

        if((courseService.findById(id).getStatus().getName()=="Not Started")){
            return courseRepository.findById(id)
                    .map(course -> {
                        course.setCourseName(newCourse.getCourseName());
                        course.setCourseDescription(newCourse.getCourseDescription());
                        course.setStartDate(newCourse.getStartDate());
                        course.setEndDate(newCourse.getEndDate());
                        course.setMinGrade(newCourse.getMinGrade());
                        course.setProfessor(newCourse.getProfessor());
                        return courseRepository.save(course);
                    })
                    .orElseGet(() -> {
                        newCourse.setId(id);
                        return courseRepository.save(newCourse);
                    });
        }
        else
            return null;
    }

    @Override
    public void deleteById(Long courseId){
        if(courseRepository.findById(courseId).isPresent()){

            Course courseToDelete = courseService.findById(courseId);
            for (Enrollment enrollmentToDelete: courseToDelete.getEnrollments()){
                enrollmentRepository.delete(enrollmentToDelete);
            }
            courseRepository.deleteById(courseId);
        }
    }

    @Override
    public void addAptitudeToCourse(Long courseId, Long aptitudeId) {
        Course courseToUpdate = courseService.findById(courseId);
        Aptitude aptitudeToAdd = aptitudeService.findById(aptitudeId);

        if((courseToUpdate.getStatus().getName()=="Not Started")){
            courseToUpdate.getAptitudes().add(aptitudeToAdd);
            aptitudeToAdd.getCourses().add(courseToUpdate);
            courseRepository.save(courseToUpdate);
            aptitudeRepository.save(aptitudeToAdd);
        }


    }

    @Override
    public void deleteAptitudeFromCourse(Long courseId, Long aptitudeId) {
        Course courseToUpdate = courseService.findById(courseId);
        Aptitude aptitudeToRemove = aptitudeService.findById(aptitudeId);

        if((courseToUpdate.getStatus().getName()=="Not Started")){
            courseToUpdate.getAptitudes().remove(aptitudeToRemove);
            aptitudeToRemove.getCourses().remove(courseToUpdate);
            courseRepository.save(courseToUpdate);
            aptitudeRepository.save(aptitudeToRemove);
        }

    }

    @Override
    public void addStudentToCourse(Long courseId, Long studentId) {
        Course courseToUpdate = courseService.findById(courseId);
        Student studentToAdd = studentService.findById(studentId);

        if((courseToUpdate.getStatus().getName()=="Not Started")){
            Enrollment newEnrollment = new Enrollment();
            newEnrollment.setCourse(courseToUpdate);
            newEnrollment.setStudent(studentToAdd);

            courseToUpdate.getEnrollments().add(newEnrollment);
            studentToAdd.getEnrollments().add(newEnrollment);

            courseRepository.save(courseToUpdate);
            enrollmentRepository.save(newEnrollment);
            studentRepository.save(studentToAdd);
        }

    }

    @Override
    public void deleteStudentFromCourse(Long courseId, Long studentId) {
        Course courseToUpdate = courseService.findById(courseId);
        Student studentToDelete = studentService.findById(studentId);

        if((courseToUpdate.getStatus().getName()=="Not Started")){
            Enrollment enrollmentToDelete = enrollmentRepository.findEnrollmentByCourse_IdAndStudent_Id(courseToUpdate.getId(),studentToDelete.getId());

            courseToUpdate.getEnrollments().remove(enrollmentToDelete);
            studentToDelete.getEnrollments().remove(enrollmentToDelete);

            courseRepository.save(courseToUpdate);
            studentRepository.save(studentToDelete);
            enrollmentRepository.delete(enrollmentToDelete);
        }
    }

    @Override
    public void updateGrade(Long courseId, Long studentId, Integer grade) {
        Enrollment enrollmentToUpdate = enrollmentRepository.findEnrollmentByCourse_IdAndStudent_Id(courseId, studentId);
        Course courseToUpdate = courseService.findById(courseId);
        if((courseService.findById(courseId).getStatus().getName()=="Ended")){
            enrollmentToUpdate.setGrade(grade);
            enrollmentRepository.save(enrollmentToUpdate);
        }
        int pendingGradeCounter = 0;
        for(Enrollment enrollmentFromCourse : courseToUpdate.getEnrollments() ){
            if(enrollmentFromCourse.getGrade()==null){
                pendingGradeCounter++;
            }
        }
        if (pendingGradeCounter!=0){
            courseToUpdate.setStatus(statusRespository.findByName("Completed"));
            courseService.save(courseToUpdate);
        }
    }
}
