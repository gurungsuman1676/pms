package com.pms.app.controller;

import com.pms.app.schema.PageResult;
import com.pms.app.schema.UserHistoryResource;
import com.pms.app.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 8/21/2017.
 */

@RestController
@RequestMapping(value = Routes.V1+"/histories")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<UserHistoryResource> getAllHistory(
            @RequestParam(required = false) Date completedDate,
            @RequestParam(required = false) Date dateFrom,
            @RequestParam(required = false) Date dateTo,
            @RequestParam( value = "roles") List<String> roles,
            @RequestParam(required = false) Integer orderNo,

            Pageable pageable) {
        String role = null;
        if (roles != null && !roles.isEmpty()) {
            for (String userRole : roles) {
                if (!userRole.equalsIgnoreCase("USER") && !userRole.equalsIgnoreCase("ADMIN")) {
                    role = userRole;
                    break;
                }
            }
        }
        return historyService.getAll(completedDate,dateFrom,dateTo,role,orderNo,pageable);

    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public void getAllHistoryReport(
            @RequestParam(required = false) Date completedDate,
            @RequestParam(required = false) Date dateFrom,
            @RequestParam(required = false) Date dateTo,
            @RequestParam(required = false, value = "roles") List<String> roles,
            @RequestParam(required = false) Integer orderNo,

            HttpServletResponse httpServletResponse) {
        String role = null;
        if (roles != null && !roles.isEmpty()) {
            for (String userRole : roles) {
                if (!userRole.equalsIgnoreCase("USER") && !userRole.equalsIgnoreCase("ADMIN")) {
                    role = userRole;
                    break;
                }
            }
        }
         historyService.getReport(completedDate,dateFrom,dateTo,role,orderNo,httpServletResponse);

    }
}
