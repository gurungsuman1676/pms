package com.pms.app.service;

import com.pms.app.schema.PageResult;
import com.pms.app.schema.UserHistoryResource;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 8/21/2017.
 */
public interface HistoryService {
    PageResult<UserHistoryResource> getAll(Date completedDate, Date dateFrom, Date dateTo, String role, Integer orderNo, Pageable pageable);

    void getReport(Date completedDate, Date dateFrom, Date dateTo, String role, Integer orderNo, HttpServletResponse httpServletResponse);
}
