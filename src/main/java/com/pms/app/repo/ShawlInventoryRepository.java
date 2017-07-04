package com.pms.app.repo;

import com.pms.app.domain.ShawlInventory;
import com.pms.app.repo.repoCustom.ShawlInventoryRepositoryCustom;

/**
 * Created by arjun on 6/27/2017.
 */
public interface ShawlInventoryRepository extends AbstractRepository<ShawlInventory> , ShawlInventoryRepositoryCustom {
}
