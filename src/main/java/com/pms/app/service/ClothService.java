package com.pms.app.service;

import com.pms.app.domain.Clothes;
import com.pms.app.domain.Colors;
import com.pms.app.domain.Customers;
import com.pms.app.domain.Designs;
import com.pms.app.domain.Prints;
import com.pms.app.domain.Sizes;
import com.pms.app.schema.ClothDto;
import com.pms.app.schema.ClothLocationDto;
import com.pms.app.schema.WeavingShippingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ClothService {
    Page<Clothes> getClothes(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom,
                             Date deliveryDateTo, Date orderDateFrom, Date orderDateTo,
                             Pageable pageable, String number, String shippingNumber, String boxNumber,
                             Boolean isReject, Integer type, Date locationDate, Long designId, Double gauge, String setting, String reOrder, String week, Long colorId);

    List<Clothes> addCloth(ClothDto clothDto);

    Clothes getCloth(Long id);

    Clothes updateCloth(Long id, ClothLocationDto clothDto);

    void deleteCloth(Long id);

    void updateWeavingCloth(WeavingShippingDTO weavingShippingDTO);

    List<Customers> getCustomerByOrderNumber(Integer orderNumber);

    List<Designs> getDesignByOrderNumberAndCustomer(Integer orderNumber, Long customerId);

    List<Prints> getPrintByOrderNumberAndCustomer(Integer orderNumber, Long customerId, Long designId, Long sizeId);

    List<Sizes> getSizesForCustomerAndOrderNumber(Integer orderNumber, Long customerId, Long designId);

    List<String> getExtraFieldByOrderNumberAndCustomer(Integer orderNumber, Long customerId, Long designId, Long sizeId, Long printId, Long colorId);

    List<Colors> getColorsForForCustomerAndOrderNumber(Integer orderNumber, Long customerId, Long designId, Long sizeId, Long printId);

    Long addDocument(byte[] file) throws IOException;

    byte[] getDocument(Long workLogId) throws IOException;

    void deleteWorkLog(Long id);
}
