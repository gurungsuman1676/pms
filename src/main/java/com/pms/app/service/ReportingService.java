package com.pms.app.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public interface ReportingService {
    void createOrderInvoice(Long orderNo, Long customerId, HttpServletResponse httpServletResponse);

    void getClothReport(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom,
                        Date deliveryDateTo, Date orderDateFrom, Date orderDateTo,
                        String role, String shippingNumber, String boxNumber, Boolean isReject,
                        HttpServletResponse httpServletResponse);

    void createOrderSheet(Long orderNo, Long customerId, HttpServletResponse httpServletResponse);

    void createPendingList(Long orderNo, Long customerId, HttpServletResponse httpServletResponse);

    void createShippingList(String shippingNumber, HttpServletResponse httpServletResponse);
}
