package com.pms.app.service.impl;

import com.pms.app.repo.ClothActivityRepository;
import com.pms.app.schema.ActivityResource;
import com.pms.app.service.ClothActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by arrowhead on 7/16/16.
 */


@Service
public class ClothActivityServiceImpl implements ClothActivityService {

    private final ClothActivityRepository clothActivityRepository;

    @Autowired
    public ClothActivityServiceImpl(ClothActivityRepository clothActivityRepository) {
        this.clothActivityRepository = clothActivityRepository;
    }


    @Override
    public List<ActivityResource> findAllActivityForClothes(Long clothId) {
        return clothActivityRepository.findAllClothActivityByClothId(clothId);
    }
}
