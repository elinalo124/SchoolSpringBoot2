package com.elina.school.controller;


import com.elina.school.exception.NotFoundException;
import com.elina.school.model.Course;
import com.elina.school.model.Professor;
import com.elina.school.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("") //Displays title, description and status
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses = courseService.findAll();
        System.out.println("All courses\n"+courses);
        return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
    }

    @PostMapping("") //Window with Name, description, start, end, minGrade
    public void saveCourse(@RequestBody Course newCourse){
        System.out.println("Controller is saving:\n"+newCourse);
        courseService.save(newCourse);
    }

    @GetMapping("/byName")
    public ResponseEntity<Course> getCourseByName(@RequestParam("courseName") String courseName){
        try {
            return new ResponseEntity<Course>(courseService.findByName(courseName),
                    HttpStatus.OK);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
        }
    }
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long courseId){
        try {
            return new ResponseEntity<Course>(courseService.findById(courseId),
                    HttpStatus.OK);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
        }
    }

    @PutMapping("/{courseId}")
    public ResponseEntity updateCourse(@RequestBody Course newCourse, @PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.updateById(newCourse, courseId), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    void deleteCourse(@PathVariable Long courseId) {
        courseService.deleteById(courseId);
    }


    @PutMapping("/{courseId}/addAptitude/{aptitudeId}")
    public void addAptitudeToCourse(@PathVariable("courseId") Long courseId, @PathVariable("aptitudeId") Long aptitudeId){
        courseService.addAptitudeToCourse(courseId, aptitudeId);
    }
    @PutMapping("/{courseId}/deleteAptitude/{aptitudeId}")
    public void deleteAptitudeFromCourse(@PathVariable("courseId") Long courseId, @PathVariable("aptitudeId") Long aptitudeId){
        courseService.deleteAptitudeFromCourse(courseId, aptitudeId);
    }

    @PutMapping("/{courseId}/addStudent/{studentId}")
    public void addStudentToCourse(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId){
        courseService.addStudentToCourse(courseId, studentId);
    }
    @PutMapping("/{courseId}/deleteStudent/{studentId}")
    public void deleteStudentFromCourse(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId){
        courseService.deleteStudentFromCourse(courseId, studentId);
    }

    @PutMapping("/{courseId}/editEnrollment/{studentId}")
    public void updateGrade(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId, @RequestParam("grade") Integer grade){
        courseService.updateGrade(courseId, studentId, grade);
    }


}
