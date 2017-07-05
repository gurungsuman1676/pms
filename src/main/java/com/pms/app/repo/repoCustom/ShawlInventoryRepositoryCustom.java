package com.pms.app.repo.repoCustom;

import com.pms.app.domain.ShawlInventory;
import com.pms.app.schema.ShawlInventoryResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by arjun on 6/28/2017.
 */
public interface ShawlInventoryRepositoryCustom extends AbstractRepositoryCustom<ShawlInventory> {
    Page<ShawlInventory> getAll(Long sizeId, Long colorId, Long designId, Pageable pageable);
    ShawlInventory findCountForExportBySizeIdAndColorIdAndDesignId(Long sizeId, Long colorId, Long designId);

    List<ShawlInventoryResource> getAllResources(Long sizeId, Long colorId, Long designId);
}
