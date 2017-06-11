package com.pms.app.repo.repoCustom;

import com.pms.app.domain.Clothes;
import com.pms.app.domain.Customers;
import com.pms.app.domain.Designs;
import com.pms.app.domain.Prints;
import com.pms.app.domain.Sizes;
import com.pms.app.schema.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ClothRepositoryCustom extends AbstractRepositoryCustom<Clothes> {

    Page<Clothes> findAllClothes(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom,
                                 Date deliveryDateTo, Date orderDateFrom, Date orderDateTo, Pageable pageable,
                                 String number, String shippingNumber, String boxNumber, Boolean isReject, Integer type, Long designId, Date locationDate, Double gauge);


    List<ClothOrderResource> findClothesForOrderAndCustomer(Integer orderNo, Long customerId);


    List<ClothResource> findClothResource(Long customerId,
                                          Long locationId,
                                          Integer orderNo,
                                          Long barcode,
                                          Date deliverDateFrom,
                                          Date deliveryDateTo,
                                          Date orderDateFrom,
                                          Date orderDateTo,
                                          String role,
                                          String shippingNumber,
                                          String boxNumber,
                                          Boolean isReject,
                                          Integer type, Long designId, Double gauge, Date locationDate);

    List<ClothInvoiceResource> findClothesForProformaInvoice(int orderNo, Long customerId);

    List<ClothOrderPendingResource> findClothesPendingForOrderAndCustomer(int orderNo, Long customerId);

    List<ClothShippingResource> findShippedCloth(String shippingNumber);

    List<ClothInvoiceResource> findInvoice(Long orderNumber, Long customerId, String shippingNumber);

    List<ClothWeavingResource> findClothesForOrder(Long id);

    Page<Clothes> findAllForHistoryByDate(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom, Date deliveryDateTo, Date orderDateFrom, Date orderDateTo, Pageable pageable, String role, String shippingNumber, String boxNumber, Boolean isReject, Integer type, Date locationDate, Long designId, Double gauge);


    List<ClothResource> findClothResourceByLocation(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom, Date deliveryDateTo, Date orderDateFrom, Date orderDateTo, String role, String shippingNumber, String boxNumber, Boolean isReject, Integer type, Date locationDate, Long designId, Double gauge);

    List<Clothes> findForWeavingShipping(WeavingShippingDTO weavingShippingDTO, Long locationId);

    List<Customers> findRemaingWeavingCustomerByOrderNumber(Integer orderNumber, Long locationId);

    List<Designs> findRemaingWeavingDesignByOrderNumber(Integer orderNumber, Long customerId, Long locationId);

    List<Prints> findRemaingWeavingPrintByOrderNumber(Integer orderNumber, Long customerId, Long designId, Long locationId, Long sizeId);

    List<Sizes> findRemaingWeavingSizeByOrderNumber(Integer orderNumber, Long customerId, Long designId, Long locationId);
}
