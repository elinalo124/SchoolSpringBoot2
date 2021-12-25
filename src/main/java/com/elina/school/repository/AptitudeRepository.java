package com.elina.school.repository;

import com.elina.school.model.Aptitude;
import com.elina.school.model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AptitudeRepository extends CrudRepository<Aptitude, Long> {
    Optional<Aptitude> findAptitudeByName(String name);

}
