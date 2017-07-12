package com.pms.app.service.impl;

import com.pms.app.repo.WeavingWorkLogRepository;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.WeavingLogResource;
import com.pms.app.service.WeavingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 7/11/2017.
 */

@Service
public class WeavingLogServiceImpl implements WeavingLogService {
    private final WeavingWorkLogRepository weavingWorkLogRepository;

    @Autowired
    public WeavingLogServiceImpl(WeavingWorkLogRepository weavingWorkLogRepository) {
        this.weavingWorkLogRepository = weavingWorkLogRepository;
    }

    @Override
    public PageResult<WeavingLogResource> getAll(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId, Pageable pageable) {
        return null;
    }

    @Override
    public void getReport(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId, HttpServletResponse httpServletResponse) {

    }
}
