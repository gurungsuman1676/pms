package com.pms.app.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 7/12/2017.
 */
public interface WeavingReportService {
    void getReport(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId, HttpServletResponse httpServletResponse);































}
