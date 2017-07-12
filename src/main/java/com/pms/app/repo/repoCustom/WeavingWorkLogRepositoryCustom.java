package com.pms.app.repo.repoCustom;

import com.pms.app.domain.WeavingWorkLog;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.WeavingLogResource;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 7/11/2017.
 */
public interface WeavingWorkLogRepositoryCustom extends AbstractRepositoryCustom<WeavingWorkLog> {
    PageResult<WeavingLogResource> getAll(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId, Pageable pageable);


    List<WeavingLogResource> getForReport(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId);

}
