package com.pms.app.repo.repoCustom;

import com.pms.app.domain.Clothes;
import com.pms.app.schema.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ClothRepositoryCustom extends AbstractRepositoryCustom<Clothes> {

    Page<Clothes> findAllClothes(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom,
                                 Date deliveryDateTo, Date orderDateFrom, Date orderDateTo, Pageable pageable,
                                 String number, String shippingNumber, String boxNumber, Boolean isReject);


    List<ClothOrderResource> findClothesForOrderAndCustomer(Integer orderNo, Long customerId);

    List<ClothResource> findClothResource(Long customerId, Long locationId, Integer orderNo,
                                          Long barcode, Date deliverDateFrom, Date deliveryDateTo,
                                          Date orderDateFrom, Date orderDateTo,
                                          String role, String shippingNumber, String boxNumber, Boolean isReject);

    List<ClothInvoiceResource> findClothesForProformaInvoice(int orderNo, Long customerId);

    List<ClothOrderPendingResource> findClothesPendingForOrderAndCustomer(int orderNo, Long customerId);

    List<ClothShippingResource> findShippedCloth(String shippingNumber);

    List<ClothInvoiceResource> findInvoice(Long orderNumber, Long customerId, String shippingNumber);
}
