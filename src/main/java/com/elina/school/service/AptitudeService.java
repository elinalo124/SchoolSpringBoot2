package com.elina.school.service;

import com.elina.school.model.Aptitude;

import java.util.List;

public interface AptitudeService {
    Aptitude findByName(String aptitudeName);
    Aptitude findById(Long id);
    /*
    void save(Aptitude aptitude);

    List<Aptitude> findAll();
    void deleteById(Long id);
     */
}
