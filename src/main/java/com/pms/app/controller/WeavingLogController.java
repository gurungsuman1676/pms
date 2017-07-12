package com.pms.app.controller;

import com.pms.app.schema.PageResult;
import com.pms.app.schema.WeavingLogResource;
import com.pms.app.service.WeavingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 7/11/2017.
 */

@RestController
@RequestMapping(Routes.WEAVING_LOG)
public class WeavingLogController {

    private final WeavingLogService weavingLogService;

    @Autowired
    public WeavingLogController(WeavingLogService weavingLogService) {
        this.weavingLogService = weavingLogService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<WeavingLogResource> getClothes(@RequestParam(required = false, value = "customerId") Long customerId,
                                                     @RequestParam(required = false, value = "locationId") Long locationId,
                                                     @RequestParam(required = false, value = "orderNo") Integer orderNo,
                                                     @RequestParam(required = false, value = "receiptNumber") String receiptNumber,
                                                     @RequestParam(required = false, value = "createdDateFrom") Date createdDateFrom,
                                                     @RequestParam(required = false, value = "createdDateTo") Date createdDateTo,
                                                     @RequestParam(required = false, value = "designId") Long designId,
                                                     @RequestParam(required = false, value = "printId") Long printId,
                                                     @RequestParam(required = false, value = "sizeId") Long sizeId,
                                                     Pageable pageable) {
        return weavingLogService.getAll(customerId, locationId, orderNo, receiptNumber, createdDateFrom, createdDateTo, designId, printId, sizeId, pageable);


    }


    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public void getReport(@RequestParam(required = false, value = "customerId") Long customerId,
                          @RequestParam(required = false, value = "locationId") Long locationId,
                          @RequestParam(required = false, value = "orderNo") Integer orderNo,
                          @RequestParam(required = false, value = "receiptNumber") String receiptNumber,
                          @RequestParam(required = false, value = "createdDateFrom") Date createdDateFrom,
                          @RequestParam(required = false, value = "createdDateTo") Date createdDateTo,
                          @RequestParam(required = false, value = "designId") Long designId,
                          @RequestParam(required = false, value = "printId") Long printId,
                          @RequestParam(required = false, value = "sizeId") Long sizeId,
                          HttpServletResponse httpServletResponse) {
         weavingLogService.getReport(customerId, locationId, orderNo, receiptNumber, createdDateFrom, createdDateTo, designId, printId, sizeId, httpServletResponse);

    }
}
