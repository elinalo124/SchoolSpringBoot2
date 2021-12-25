package com.elina.school.service.serviceImpl;

import com.elina.school.exception.NotFoundException;
import com.elina.school.model.Enrollment;
import com.elina.school.model.Status;
import com.elina.school.repository.StatusRespository;
import com.elina.school.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("statusService")
@Transactional
public class StatusServiceImpl implements StatusService {
    /*

    private StatusRespository statusRespository;

    @Autowired
    public void setStatusRespository(StatusRespository statusRespository) {
        this.statusRespository = statusRespository;
    }

    @Override
    public void save(Status status) {
        statusRespository.save(status);
    }

    @Override
    public List<Status> findAll() {
        return (List<Status>) statusRespository.findAll();
    }

    @Override
    public void deleteById(Long status_id) {
        if(statusRespository.findById(status_id).isPresent())
            statusRespository.deleteById(status_id);
    }

     */
}
