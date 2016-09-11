package com.pms.app.controller;

import com.pms.app.convert.ClothConvert;
import com.pms.app.domain.Clothes;
import com.pms.app.schema.*;
import com.pms.app.service.ClothService;
import com.pms.app.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = Routes.CLOTH)
public class ClothController {

    private final ClothConvert clothConvert;
    private final ClothService clothService;
    private final ReportingService reportingService;


    private static final String CLOTHS_INVOICE =  "/invoice";

    private static  final String CLOTHS_REPORT ="/report";
    @Autowired
    public ClothController(ClothConvert clothConvert, ClothService clothService, ReportingService reportingService) {
        this.clothConvert = clothConvert;
        this.clothService = clothService;
        this.reportingService = reportingService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<ClothResource> getClothes(@RequestParam(required = false,value = "customerId") Long customerId,
                                                @RequestParam(required = false,value = "locationId") Long locationId,
                                                @RequestParam(required = false,value = "orderNo") Integer orderNo,
                                                @RequestParam(required = false,value = "barcode") Long barcode,
                                                @RequestParam(required = false,value = "deliverDateFrom") Date deliverDateFrom,
                                                @RequestParam(required = false,value = "deliveryDateTo") Date deliveryDateTo,
                                                @RequestParam(required = false,value = "orderDateFrom") Date orderDateFrom,
                                                @RequestParam(required = false,value = "orderDateTo") Date orderDateTo,
                                                @RequestParam(required = false,value = "shippingNumber") String shippingNumber,
                                                @RequestParam(required = false,value = "boxNumber") String boxNumber,
                                                @RequestParam(required = false,value = "roles") List<String> roles,
                                                @RequestParam(required = false,value = "isReject") Boolean isReject,
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

        Page<Clothes> page = clothService.getClothes(customerId, locationId, orderNo, barcode, deliverDateFrom, deliveryDateTo, orderDateFrom,
                orderDateTo, pageable, role, shippingNumber, boxNumber,isReject);
        return new PageResult<>(page.getTotalElements(), page.getSize(), page.getNumber(), clothConvert.convert(page.getContent()));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<ClothResource> addCloth(@RequestBody ClothDto clothDto) {
        return clothConvert.convert(clothService.addCloth(clothDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ClothResource getCloth(@PathVariable Long id) {
        return clothConvert.convert(clothService.getCloth(id));
    }

    @RequestMapping(value = "/{id}/locations", method = RequestMethod.PUT)
    public ClothResource updateCloth(@PathVariable Long id, @RequestBody ClothLocationDto locationDto) {
        return clothConvert.convert(clothService.updateCloth(id, locationDto));
    }

    @ResponseStatus(ACCEPTED)
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.PUT)
    public void updateCloth(@PathVariable Long id) {
        clothService.deleteCloth(id);
    }

    @RequestMapping(value = CLOTHS_INVOICE, method = RequestMethod.GET)
    public void createOrderInvoice(@RequestParam Long orderNo,@RequestParam Long customerId
            ,HttpServletResponse httpServletResponse) {
      reportingService.createOrderInvoice(orderNo, customerId,httpServletResponse);
    }

    @RequestMapping(value = CLOTHS_REPORT, method = RequestMethod.GET)
    public void getReport(@RequestParam(required = false,value = "customerId") Long customerId,
                                                @RequestParam(required = false,value = "locationId") Long locationId,
                                                @RequestParam(required = false,value = "orderNo") Integer orderNo,
                                                @RequestParam(required = false,value = "barcode") Long barcode,
                                                @RequestParam(required = false,value = "deliverDateFrom") Date deliverDateFrom,
                                                @RequestParam(required = false,value = "deliveryDateTo") Date deliveryDateTo,
                                                @RequestParam(required = false,value = "orderDateFrom") Date orderDateFrom,
                                                @RequestParam(required = false,value = "orderDateTo") Date orderDateTo,
                                                @RequestParam(required = false,value = "shippingNumber") String shippingNumber,
                                                @RequestParam(required = false,value = "boxNumber") String boxNumber,
                                                @RequestParam(required = false,value = "roles") List<String> roles,
                                                @RequestParam(required = false,value = "isReject") Boolean isReject,
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

         reportingService.getClothReport(customerId, locationId, orderNo, barcode, deliverDateFrom, deliveryDateTo, orderDateFrom,
                orderDateTo,role, shippingNumber, boxNumber,isReject,httpServletResponse);
    }

}