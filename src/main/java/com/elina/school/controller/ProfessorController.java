package com.elina.school.controller;

import com.elina.school.exception.NotFoundException;
import com.elina.school.model.Course;
import com.elina.school.model.Professor;
import com.elina.school.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    private ProfessorService professorService;

    @Autowired
    public void setProfessorService(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("")
    public ResponseEntity<List<Professor>> getAllProfessors(){
        List<Professor> professors = professorService.findAll();
        System.out.println("All professors\n"+professors);
        return new ResponseEntity<List<Professor>>(professors, HttpStatus.OK);
    }
    @PostMapping("")
    public void saveCourse(@RequestBody Professor newProfessor){
        System.out.println("Controller is saving:\n"+newProfessor);
        professorService.save(newProfessor);
    }
    @GetMapping("/byName")
    public ResponseEntity<Professor> getProfessorByName(@RequestParam("professorName") String professorName){
        try {
            return new ResponseEntity<Professor>(professorService.findByName(professorName),
                    HttpStatus.OK);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
        }
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<Professor> getProfessor(@PathVariable("professorId") Long professorId){
        try {
            return new ResponseEntity<Professor>(professorService.findById(professorId),
                    HttpStatus.OK);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor Not Found");
        }
    }
    @DeleteMapping("/{professorId}")
    void deleteProfessor(@PathVariable("professorId") Long professorId) {
        professorService.deleteById(professorId);
    }


    @PutMapping("/{professorId}/addAptitude/{aptitudeId}")
    public void addAptitudeToProfessor(@PathVariable("professorId") Long professorId, @PathVariable("aptitudeId") Long aptitudeId){
        professorService.addAptitudeToProfessor(professorId, aptitudeId);
    }
    @PutMapping("/{professorId}/deleteAptitude/{aptitudeId}")
    public void deleteAptitudeFromProfessor(@PathVariable("professorId") Long professorId, @PathVariable("aptitudeId") Long aptitudeId){
        professorService.deleteAptitudeFromProfessor(professorId, aptitudeId);
    }

    @PutMapping("/{professorId}/addCourse/{courseId}")
    public void addCourseToProfessor(@PathVariable("professorId") Long professorId, @PathVariable("courseId") Long courseId){
        professorService.addCourseToProfessor(professorId, courseId);
    }
    @PutMapping("/{professorId}/deleteCourse/{courseId}")
    public void deleteCourseFromProfessor(@PathVariable("professorId") Long professorId, @PathVariable("courseId") Long courseId){
        professorService.deleteCourseFromProfessor(professorId, courseId);
    }


}
