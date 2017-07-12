package com.pms.app.service;

import com.pms.app.schema.PageResult;
import com.pms.app.schema.WeavingLogResource;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 7/11/2017.
 */
public interface WeavingLogService {
    PageResult<WeavingLogResource> getAll(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId, Pageable pageable);

    void getReport(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId, HttpServletResponse httpServletResponse);

}
