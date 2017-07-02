package com.pms.app.repo.repoCustom;

import com.pms.app.domain.ShawlEntry;
import com.pms.app.schema.ShawlEntryResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 6/28/2017.
 */
public interface ShawlEntryRepositoryCustom extends AbstractRepositoryCustom<ShawlEntry> {
    Long findCountForExportByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long sizeId);

    List<ShawlEntry> findForExportByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId, int quantity);

    Long findCountByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId);

    List<ShawlEntry> findByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId, int quantity);

    Page<ShawlEntry> getAll(Long locationId, Long sizeId, Long yarnId, Long customerId, Long colorId, Long shawlId, Date entryDateFrom, Date entryDateTo, Date exportDateFrom, Date exportDateTo, Pageable pageable);

    List<ShawlEntryResource> getAllResources(Long locationId, Long sizeId, Long yarnId, Long customerId, Long colorId, Long shawlId, Date entryDateFrom, Date entryDateTo, Date exportDateFrom, Date exportDateTo);


}
