package com.pms.app.service;

import com.pms.app.domain.Clothes;
import com.pms.app.schema.ClothDto;
import com.pms.app.schema.ClothLocationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ClothService {
    Page<Clothes> getClothes(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom,
                             Date deliveryDateTo, Date orderDateFrom, Date orderDateTo,
                             Pageable pageable, String number, String shippingNumber, String boxNumber, Boolean isReject);

    List<Clothes> addCloth(ClothDto clothDto);

    Clothes getCloth(Long id);

    Clothes updateCloth(Long id, ClothLocationDto clothDto);

    void deleteCloth(Long id);
}