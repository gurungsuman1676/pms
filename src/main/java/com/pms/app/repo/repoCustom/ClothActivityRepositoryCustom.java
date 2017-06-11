package com.pms.app.repo.repoCustom;

import com.pms.app.domain.ClothActivity;
import com.pms.app.domain.Clothes;
import com.pms.app.schema.ActivityResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by arrowhead on 7/16/16.
 */
public interface ClothActivityRepositoryCustom extends AbstractRepositoryCustom<ClothActivity> {
    List<ActivityResource> findAllClothActivityByClothId(Long clothId);

    Long doesActivityExist(Long locationId, Long clothesId);





















































































}

