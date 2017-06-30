package com.pms.app.repo.repoCustom;

import com.pms.app.domain.ShawlEntry;

import java.util.List;

/**
 * Created by arjun on 6/28/2017.
 */
public interface ShawlEntryRepositoryCustom extends AbstractRepositoryCustom<ShawlEntry> {
    Long findCountForExportByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long sizeId);

    List<ShawlEntry> findForExportByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId, int quantity);

    Long findCountByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId);

    List<ShawlEntry> findByShawlIdColorIdYarnIdCustomerIdSizeId(Long shawlId, Long shawlColorId, Long shawlYarnId, Long shawlCustomerId, Long shawlSizeId, int quantity);
}
