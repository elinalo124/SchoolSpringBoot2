package com.elina.school.controller;

import com.elina.school.model.Status;
import com.elina.school.model.Student;
import com.elina.school.repository.StatusRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private StatusRespository statusRespository;

    @Autowired
    public void setStatusRespository(StatusRespository statusRespository) {
        this.statusRespository = statusRespository;
    }

    @GetMapping("/getAllStatuses")
    public ResponseEntity<List<Status>> getAllStatuses(){
        List<Status> statuses = (List<Status>) statusRespository.findAll();
        System.out.println("All statuses\n"+statuses);
        return new ResponseEntity<List<Status>>(statuses, HttpStatus.OK);
    }
}
