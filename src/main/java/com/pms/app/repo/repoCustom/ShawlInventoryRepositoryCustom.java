package com.pms.app.repo.repoCustom;

import com.pms.app.domain.ShawlInventory;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.ShawlInventoryBatchDetailResource;
import com.pms.app.schema.ShawlInventoryDto;
import com.pms.app.schema.ShawlInventoryResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 6/28/2017.
 */
public interface ShawlInventoryRepositoryCustom extends AbstractRepositoryCustom<ShawlInventory> {
    Page<ShawlInventory> getAll(Long sizeId, Long colorId, Long designId, Pageable pageable);

    ShawlInventory findForExportBySizeIdAndColorIdAndDesignId(Long sizeId, Long colorId, Long designId);

    List<ShawlInventoryResource> getAllResources(Long sizeId, Long colorId, Long designId);

    PageResult<ShawlInventoryBatchDetailResource> getBatchDetails(Long id, Date createdFrom, Date createdTo,
                                                                  String receiptNumber, Pageable pageable);

    List<ShawlInventoryBatchDetailResource> getAllForReport(Long id, Date createdFrom, Date createdTo,
                                                            String receiptNumber);

    ShawlInventoryDto findByBatchId(Long batchId);
}
