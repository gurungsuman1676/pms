package com.pms.app.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 6/30/2017.
 */
public interface KnitterHistoryReportService {
    void getHistoryReport(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, HttpServletResponse httpServletResponse);
}
