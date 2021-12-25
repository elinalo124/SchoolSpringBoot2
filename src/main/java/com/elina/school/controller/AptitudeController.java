package com.elina.school.controller;

import com.elina.school.exception.NotFoundException;
import com.elina.school.model.Aptitude;
import com.elina.school.service.AptitudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/aptitudes")
public class AptitudeController {
    private AptitudeService aptitudeService;

    @Autowired
    public void AptitudeService(AptitudeService aptitudeService) {
        this.aptitudeService = aptitudeService;
    }

    @GetMapping("")
    public ResponseEntity<Aptitude> getAptitudeByName(@RequestParam("aptitudeName") String aptitudeName){
        try {
            return new ResponseEntity<Aptitude>(aptitudeService.findByName(aptitudeName),
                    HttpStatus.OK);
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
        }
    }
}
