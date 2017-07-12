package com.pms.app.controller;

import com.pms.app.convert.ClothConvert;
import com.pms.app.convert.CustomerConvert;
import com.pms.app.convert.DesignConvert;
import com.pms.app.convert.PrintConvert;
import com.pms.app.convert.SizeConvert;
import com.pms.app.domain.Clothes;
import com.pms.app.schema.ClothDto;
import com.pms.app.schema.ClothLocationDto;
import com.pms.app.schema.ClothResource;
import com.pms.app.schema.CustomerResource;
import com.pms.app.schema.DesignResource;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.PrintResource;
import com.pms.app.schema.SizeResource;
import com.pms.app.schema.WeavingShippingDTO;
import com.pms.app.service.ClothService;
import com.pms.app.upload.ExcelUploadService;
import com.pms.app.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping(value = Routes.CLOTH)
public class ClothController {

    private final ClothConvert clothConvert;
    private final ClothService clothService;
    private final ReportingService reportingService;
    private final ExcelUploadService excelUploadService;
    private final CustomerConvert customerConvert;
    private final DesignConvert designConvert;
    private final PrintConvert printConvert;
    private final SizeConvert sizeConvert;
    private static final String CLOTHS_INVOICE = "/invoice";
    private static final String CLOTHS_PENDING_LIST = "/pending_list";
    private static final String CLOTHS_SHIPPING = "/shipping_list";
    private static final String CLOTHS_ORDER_SHEET = "/order_sheet";
    private static final String CLOTHS_PROFORMA_INVOICE = "/proforma_invoice";
    private static final String CLOTHS_WEAVING_ID = "/weaving" + "/{id}";
    private static final String CLOTHS_EXCEL_UPLOAD = "/excel-upload";

    private static final String CLOTHS_REPORT = "/report";

    @Autowired
    public ClothController(ClothConvert clothConvert, ClothService clothService, ReportingService reportingService, ExcelUploadService excelUploadService, CustomerConvert customerConvert, DesignConvert designConvert, PrintConvert printConvert, SizeConvert sizeConvert) {
        this.clothConvert = clothConvert;
        this.clothService = clothService;
        this.reportingService = reportingService;
        this.excelUploadService = excelUploadService;
        this.customerConvert = customerConvert;
        this.designConvert = designConvert;
        this.printConvert = printConvert;
        this.sizeConvert = sizeConvert;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<ClothResource> getClothes(@RequestParam(required = false, value = "customerId") Long customerId,
                                                @RequestParam(required = false, value = "locationId") Long locationId,
                                                @RequestParam(required = false, value = "orderNo") Integer orderNo,
                                                @RequestParam(required = false, value = "barcode") Long barcode,
                                                @RequestParam(required = false, value = "deliveryDateFrom") Date deliveryDateFrom,
                                                @RequestParam(required = false, value = "deliveryDateTo") Date deliveryDateTo,
                                                @RequestParam(required = false, value = "orderDateFrom") Date orderDateFrom,
                                                @RequestParam(required = false, value = "orderDateTo") Date orderDateTo,
                                                @RequestParam(required = false, value = "shippingNumber") String shippingNumber,
                                                @RequestParam(required = false, value = "boxNumber") String boxNumber,
                                                @RequestParam(required = false, value = "roles") List<String> roles,
                                                @RequestParam(required = false, value = "isReject") Boolean isReject,
                                                @RequestParam(required = false, value = "type") Integer type,
                                                @RequestParam(required = false, value = "locationDate") Date locationDate,
                                                @RequestParam(required = false, value = "designId") Long designId,
                                                @RequestParam(required = false, value = "gauge") Double gauge,
                                                @RequestParam(required = false, value = "setting") String setting,
                                                @RequestParam(required = false, value = "reOrder") Boolean reOrder,
                                                @RequestParam(required = false, value = "week") String week,
                                                @RequestParam(required = false, value = "colorId") Long colorId,
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

        Page<Clothes> page = clothService.getClothes(customerId, locationId, orderNo, barcode, deliveryDateFrom, deliveryDateTo, orderDateFrom,
                orderDateTo, pageable, role, shippingNumber, boxNumber, isReject, type, locationDate, designId, gauge, setting, reOrder, week, colorId);
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

    @RequestMapping(value = CLOTHS_ORDER_SHEET, method = RequestMethod.GET)
    public void createOrderSheet(@RequestParam(value = "orderNo") Long orderNo, @RequestParam(value = "customerId") Long customerId
            , HttpServletResponse httpServletResponse) {
        reportingService.createOrderSheet(orderNo, customerId, httpServletResponse);
    }


    @RequestMapping(value = CLOTHS_PROFORMA_INVOICE, method = RequestMethod.GET)
    public void createOrderInvoice(@RequestParam(value = "orderNo") Long orderNo, @RequestParam(value = "customerId") Long customerId
            , HttpServletResponse httpServletResponse) {
        reportingService.createProformaInvoice(orderNo, customerId, httpServletResponse);
    }

    @RequestMapping(value = CLOTHS_INVOICE, method = RequestMethod.GET)
    public void getInvoice(@RequestParam(required = false, value = "orderNo") Long orderNo, @RequestParam(required = false, value = "customerId") Long customerId,
                           @RequestParam(value = "shippingNumber") String shippingNumber
            , HttpServletResponse httpServletResponse) {
        reportingService.createInvoice(orderNo, customerId, shippingNumber, httpServletResponse);
    }


    @RequestMapping(value = CLOTHS_PENDING_LIST, method = RequestMethod.GET)
    public void createPendingList(@RequestParam(value = "orderNo") Long orderNo, @RequestParam(value = "customerId") Long customerId
            , HttpServletResponse httpServletResponse) {
        reportingService.createPendingList(orderNo, customerId, httpServletResponse);
    }

    @RequestMapping(value = CLOTHS_SHIPPING, method = RequestMethod.GET)
    public void createShippingList(@RequestParam(value = "shippingNumber") String shippingNumber, HttpServletResponse httpServletResponse) {
        reportingService.createShippingList(shippingNumber, httpServletResponse);
    }


    @RequestMapping(value = CLOTHS_REPORT, method = RequestMethod.GET)
    public void getReport(@RequestParam(required = false, value = "customerId") Long customerId,
                          @RequestParam(required = false, value = "locationId") Long locationId,
                          @RequestParam(required = false, value = "orderNo") Integer orderNo,
                          @RequestParam(required = false, value = "barcode") Long barcode,
                          @RequestParam(required = false, value = "deliverDateFrom") Date deliverDateFrom,
                          @RequestParam(required = false, value = "deliveryDateTo") Date deliveryDateTo,
                          @RequestParam(required = false, value = "orderDateFrom") Date orderDateFrom,
                          @RequestParam(required = false, value = "orderDateTo") Date orderDateTo,
                          @RequestParam(required = false, value = "shippingNumber") String shippingNumber,
                          @RequestParam(required = false, value = "boxNumber") String boxNumber,
                          @RequestParam(required = false, value = "roles") List<String> roles,
                          @RequestParam(required = false, value = "isReject") Boolean isReject,
                          @RequestParam(required = false, value = "type") Integer type,
                          @RequestParam(required = false, value = "locationDate") Date locationDate,
                          @RequestParam(required = false, value = "designId") Long designId,
                          @RequestParam(required = false, value = "gauge") Double gauge,
                          @RequestParam(required = false, value = "setting") String setting,
                          @RequestParam(required = false, value = "reOrder") Boolean reOrder,
                          @RequestParam(required = false, value = "week") String week,
                          @RequestParam(required = false, value = "colorId") Long colorId,

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
                orderDateTo, role, shippingNumber, boxNumber, isReject, type, locationDate, designId, gauge, setting, reOrder, week, colorId, httpServletResponse);
    }

    @RequestMapping(value = CLOTHS_WEAVING_ID, method = RequestMethod.GET)
    public void getWeavingReport(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        reportingService.createWeaving(id, httpServletResponse);
    }

    @RequestMapping(value = CLOTHS_EXCEL_UPLOAD, method = RequestMethod.POST)
    public void uploadEcelFile(@RequestParam("file") MultipartFile file,
                               @RequestParam String type,
                               HttpServletResponse httpServletResponse) throws Exception {
        excelUploadService.uploadClothes(file, httpServletResponse, type);
    }


    @RequestMapping(value = "/weaving/locations", method = RequestMethod.PUT)
    public void addweavingShipping(@RequestBody WeavingShippingDTO weavingShippingDTO) {
        clothService.updateWeavingCloth(weavingShippingDTO);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<CustomerResource> getCustomerOfOrderNumber(@RequestParam Integer orderNumber) {
        return customerConvert.convertCustomer(clothService.getCustomerByOrderNumber(orderNumber));
    }

    @RequestMapping(value = "/designs", method = RequestMethod.GET)
    public List<DesignResource> getDesignForCustomerAndOrderNumber(@RequestParam Integer orderNumber, @RequestParam Long customerId) {
        return designConvert.convert(clothService.getDesignByOrderNumberAndCustomer(orderNumber, customerId));
    }

    @RequestMapping(value = "/sizes", method = RequestMethod.GET)
    public List<SizeResource> getSizesForCustomerAndOrderNumber(@RequestParam Integer orderNumber,
                                                                @RequestParam Long customerId, @RequestParam Long designId) {
        return sizeConvert.convertSizes(clothService.getSizesForCustomerAndOrderNumber(orderNumber, customerId, designId));
    }

    @RequestMapping(value = "/prints", method = RequestMethod.GET)
    public List<PrintResource> getPrintnForCustomerAndOrderNumber(@RequestParam Integer orderNumber,
                                                                  @RequestParam Long customerId,
                                                                  @RequestParam Long designId,
                                                                  @RequestParam Long sizeId) {
        return printConvert.convert(clothService.getPrintByOrderNumberAndCustomer(orderNumber, customerId, designId, sizeId));
    }

    @RequestMapping(value = "/extraFields", method = RequestMethod.GET)
    public Set<String> getExtraFieldForForCustomerAndOrderNumber(@RequestParam Integer orderNumber,
                                                                 @RequestParam Long customerId,
                                                                 @RequestParam Long designId,
                                                                 @RequestParam Long sizeId,
                                                                 @RequestParam Long printId) {
        return new HashSet<>(clothService.getExtraFieldByOrderNumberAndCustomer(orderNumber, customerId, designId, sizeId, printId));
    }


    @RequestMapping(value = "/documents", method = RequestMethod.POST)
    public Long addDocument(@RequestBody byte[] file) throws IOException {
        return clothService.addDocument(file);
    }

    @RequestMapping(value = "/documents/workLogs/{workLogId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getDocument(@PathVariable Long workLogId) throws IOException {
        return clothService.getDocument(workLogId);
    }
}
