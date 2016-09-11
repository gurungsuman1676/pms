package com.pms.app.repo;

import com.pms.app.domain.ClothActivity;
import com.pms.app.repo.repoCustom.ClothActivityRepositoryCustom;
import org.springframework.stereotype.Repository;

/**
 * Created by arrowhead on 7/16/16.
 */

@Repository
public interface ClothActivityRepository extends AbstractRepository<ClothActivity> , ClothActivityRepositoryCustom{
}
