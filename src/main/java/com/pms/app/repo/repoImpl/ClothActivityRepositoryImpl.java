package com.pms.app.repo.repoImpl;

import com.pms.app.domain.ClothActivity;
import com.pms.app.domain.QClothActivity;
import com.pms.app.repo.ClothActivityRepository;
import com.pms.app.repo.repoCustom.ClothActivityRepositoryCustom;
import com.pms.app.schema.ActivityResource;
import com.pms.app.schema.QActivityResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Created by arrowhead on 7/16/16.
 */
public class ClothActivityRepositoryImpl extends AbstractRepositoryImpl<ClothActivity,ClothActivityRepository> implements ClothActivityRepositoryCustom {
    public ClothActivityRepositoryImpl() {
        super(ClothActivity.class);
    }

    @Autowired
    @Lazy
    public void setRepository(ClothActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ActivityResource> findAllClothActivityByClothId(Long clothId) {
        QClothActivity clothActivity = QClothActivity.clothActivity;
        return from(clothActivity)
                .innerJoin(clothActivity.cloth)
                .innerJoin(clothActivity.location)
                .innerJoin(clothActivity.user)
                .where(clothActivity.cloth.id.eq(clothId))
                .list(new QActivityResource(
                        clothActivity.id,
                        clothActivity.location.name,
                        clothActivity.created,
                        clothActivity.user.username
                ));
    }

    @Override
    public Long doesActivityExist(Long locationId, Long clothesId) {
        QClothActivity clothActivity = QClothActivity.clothActivity;
        return from(clothActivity)
                .innerJoin(clothActivity.cloth)
                .innerJoin(clothActivity.user)
                .where(clothActivity.cloth.id.eq(clothesId)
                        .and(clothActivity.location.id.eq(locationId)))
                .count();
    }
}

