package com.elina.school.controller;

import com.elina.school.exception.NotFoundException;
import com.elina.school.model.Course;
import com.elina.school.model.Professor;
import com.elina.school.model.Student;
import com.elina.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.findAll();
        System.out.println("All students\n"+students);
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
    }
    @PostMapping("")
    public void saveCourse(@RequestBody Student newStudent){
        System.out.println("Controller is saving:\n"+newStudent);
        studentService.save(newStudent);
    }
    @GetMapping("/byName")
    public ResponseEntity<Student> getStudentByName(@RequestParam("studentName") String studentName){
        try {
            return new ResponseEntity<Student>(studentService.findByName(studentName),
                    HttpStatus.OK);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
        }
    }


    @GetMapping("/{StudentId}")
    public ResponseEntity<Student> getStudent(@PathVariable("StudentId") Long StudentId){
        try {
            return new ResponseEntity<Student>(studentService.findById(StudentId),
                    HttpStatus.OK);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found");
        }
    }
    @DeleteMapping("/{StudentId}")
    void deleteStudent(@PathVariable("StudentId") Long StudentId) {
        studentService.deleteById(StudentId);
    }



    @PutMapping("/{studentId}/addAptitude/{aptitudeId}")
    public void addAptitudeToStudent(@PathVariable("studentId") Long studentId, @PathVariable("aptitudeId") Long aptitudeId){
        studentService.addAptitudeToStudent(studentId, aptitudeId);
    }
    @PutMapping("/{studentId}/deleteAptitude/{aptitudeId}")
    public void deleteAptitudeFromStudent(@PathVariable("studentId") Long studentId, @PathVariable("aptitudeId") Long aptitudeId){
        studentService.deleteAptitudeFromStudent(studentId, aptitudeId);
    }

    @PutMapping("/{studentId}/addCourse/{courseId}")
    public void addCourseToStudent(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId){
        studentService.addCourseToStudent(studentId, courseId);
    }
    @PutMapping("/{studentId}/deleteCourse/{courseId}")
    public void deleteCourseFromStudent(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId){
        studentService.deleteCourseFromStudent(studentId, courseId);
    }
    
}
