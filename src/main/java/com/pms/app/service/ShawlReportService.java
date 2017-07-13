package com.pms.app.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 6/30/2017.
 */
public interface ShawlReportService {
    void generateReport(Long sizeId, Long colorId, Long designId, HttpServletResponse httpServletResponse);

    void getBatchDetailReport(Long id, Date createdFrom, Date createdTo, String receiptNumber,HttpServletResponse httpServletResponse);
}
