package com.elina.school.repository;

import com.elina.school.model.Aptitude;
import com.elina.school.model.Course;
import com.elina.school.model.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusRespository extends CrudRepository<Status, Long> {
    Status findByName(String status_string);
}
