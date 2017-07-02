package com.pms.app.service.impl;

import com.pms.app.repo.ShawlActivityRepository;
import com.pms.app.schema.ActivityResource;
import com.pms.app.service.ShawlActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arrowhead on 7/16/16.
 */


@Service
public class ShawlActivityServiceImpl implements ShawlActivityService {

    private final ShawlActivityRepository shawlActivityRepository;

    @Autowired
    public ShawlActivityServiceImpl(ShawlActivityRepository shawlActivityRepository) {
        this.shawlActivityRepository = shawlActivityRepository;
    }

    @Override
    public List<ActivityResource> findAllActivityForShawl(Long entryId) {
        return shawlActivityRepository.findAllActivityForShawl(entryId);
    }


}
