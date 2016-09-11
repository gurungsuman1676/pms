package com.pms.app.repo;

import com.pms.app.domain.Clothes;
import com.pms.app.repo.repoCustom.ClothRepositoryCustom;

public interface ClothRepository extends AbstractRepository<Clothes>,ClothRepositoryCustom {

}
