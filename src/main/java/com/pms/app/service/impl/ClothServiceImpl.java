package com.pms.app.service.impl;

import com.pms.app.domain.*;
import com.pms.app.repo.*;
import com.pms.app.schema.ClothDto;
import com.pms.app.schema.ClothLocationDto;
import com.pms.app.schema.WeavingShippingDTO;
import com.pms.app.security.AuthUtil;
import com.pms.app.service.ClothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClothServiceImpl implements ClothService {
    private final ClothRepository clothRepository;
    private final PriceRepository priceRepository;
    private final PrintRepository printRepository;
    private final LocationRepository locationRepository;
    private final CustomerRepository customerRepository;
    private final UserLocationRepository userLocationRepository;
    private final ColorRepository colorRepository;
    private final ClothActivityRepository clothActivityRepository;

    @Autowired
    public ClothServiceImpl(ClothRepository clothRepository, PriceRepository priceRepository, PrintRepository printRepository, LocationRepository locationRepository, CustomerRepository customerRepository, UserLocationRepository userLocationRepository, ColorRepository colorRepository, ClothActivityRepository clothActivityRepository) {
        this.clothRepository = clothRepository;
        this.priceRepository = priceRepository;
        this.printRepository = printRepository;
        this.locationRepository = locationRepository;
        this.customerRepository = customerRepository;
        this.userLocationRepository = userLocationRepository;
        this.colorRepository = colorRepository;
        this.clothActivityRepository = clothActivityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Clothes> getClothes(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom, Date deliveryDateTo, Date orderDateFrom,
                                    Date orderDateTo, Pageable pageable, String role, String shippingNumber, String boxNumber,
                                    Boolean isReject, Integer type, Date locationDate, Long designId, Double gauge, String setting, String reOrder, String week, Long colorId) {
        if (locationDate != null && locationId != null && locationId != -1) {
            return clothRepository.findAllForHistoryByDate(customerId,
                    locationId, orderNo, barcode, deliverDateFrom, deliveryDateTo, orderDateFrom, orderDateTo, pageable,
                    role, shippingNumber, boxNumber, isReject, type, locationDate, designId, gauge,setting,reOrder,week,colorId);
        } else {
            return clothRepository.findAllClothes(customerId,
                    locationId, orderNo, barcode, deliverDateFrom, deliveryDateTo, orderDateFrom, orderDateTo, pageable,
                    role, shippingNumber, boxNumber, isReject, type, designId, locationDate, gauge,setting,reOrder,week,colorId);
        }
    }

    @Override
    public List<Clothes> addCloth(ClothDto clothDto) {
        if (clothDto.getCustomerId() == null) {
            throw new RuntimeException("Customer is required");
        }

        if (clothDto.getColorId() == null) {
            throw new RuntimeException("Color is required");

        }
        if (clothDto.getSizeId() == null) {
            throw new RuntimeException("Size is required");
        }

        if (clothDto.getDesignId() == null) {
            throw new RuntimeException("Design is required");

        }

        Customers customers = customerRepository.findOne(clothDto.getCustomerId());
        if (customers == null) {
            throw new RuntimeException("No customer available");
        }

        Colors color = colorRepository.findOne(clothDto.getColorId());


        Prices price = priceRepository.findByDesignAndSizeAndYarn(clothDto.getDesignId(), clothDto.getSizeId(), color.getYarn().getId());
        if (price == null) {
            throw new RuntimeException("No appropriate price set");
        }
        Prints prints = null;
        if (clothDto.getPrintId() != null) {
            prints = printRepository.findOne(clothDto.getPrintId());
            if (prints == null) {
                throw new RuntimeException("No prints found");
            }
        }

        List<Clothes> clothes = new ArrayList<>();
        Locations locations = locationRepository.findByName(LocationEnum.PRE_KNITTING.getName());

        for (int i = 0; i < clothDto.getQuantity(); i++) {
            Clothes cloth = new Clothes();
            cloth.setDeliver_date(clothDto.getDelivery_date());
            cloth.setLocation(locationRepository.findOne(0L));
            cloth.setPrice(price);
            cloth.setPrint(prints);
            cloth.setOrder_no(clothDto.getOrderNo());
            cloth.setCustomer(customers);
            clothes.add(cloth);
            cloth.setType(clothDto.getTypeId());
            cloth.setStatus(Status.ACTIVE.toString());
            cloth.setColor(color);
            cloth.setLocation(locations);
        }
        return (List<Clothes>) clothRepository.save(clothes);
    }

    @Override
    @Transactional(readOnly = true)
    public Clothes getCloth(Long id) {
        Clothes clothes = clothRepository.findOne(id);
        if (clothes == null) {
            throw new RuntimeException("No clothes found");
        }
        return clothes;
    }

    @Override
    @Transactional
    public Clothes updateCloth(Long id, ClothLocationDto clothDto) {
        Locations locations = getLocationsForCurrentUser();
        Clothes clothes = clothRepository.findOne(id);
        if (clothes == null) {
            throw new RuntimeException("Error reading barcode");
        }
        if (locations.getName().equals(LocationEnum.SHIPPING.toString())) {
            if (clothDto.getBoxNumber() == null || clothDto.getBoxNumber().isEmpty()) {
                throw new RuntimeException("Enter box number");
            }

            if (clothDto.getShippingNumber() == null || clothDto.getShippingNumber().isEmpty()) {
                throw new RuntimeException("Enter shipping number");
            }

            clothes.setWeight(clothDto.getWeight());
            clothes.setBoxNumber(clothDto.getBoxNumber());
            clothes.setShipping(clothDto.getShippingNumber());
        }
        Long activityCount = clothActivityRepository.doesActivityExist(locations.getId(), clothes.getId());
        clothes.setIsReturn(clothes.getIsReturn() || activityCount >= 1);
        clothes.setLocation(locations);
        ClothActivity activity = new ClothActivity();
        activity.setCloth(clothes);
        activity.setLocation(locations);
        activity.setUser(AuthUtil.getCurrentUser());
        clothActivityRepository.save(activity);
        return clothRepository.save(clothes);
    }

    @Override
    public void deleteCloth(Long id) {
        Clothes clothes = clothRepository.findOne(id);
        if (clothes == null) {
            throw new RuntimeException("No clothes found");
        }
        clothes.setStatus(Status.INACTIVE.toString());
        clothRepository.save(clothes);
    }

    @Override
    public void updateWeavingCloth(WeavingShippingDTO weavingShippingDTO) {
        Locations locations = getLocationsForCurrentUser();
        List<Clothes> clothes = clothRepository.findForWeavingShipping(weavingShippingDTO, locations.getId());

        if (clothes.size() < weavingShippingDTO.getQuantity()) {
            throw new RuntimeException("Quantity of cloth (" + weavingShippingDTO.getQuantity() + " ) is less than available clothes (" + clothes.size() + ")");
        }
        clothes.forEach(c -> {
            c.setBoxNumber(weavingShippingDTO.getBoxNumber());
            c.setShipping(weavingShippingDTO.getShipping());
            c.setLocation(locations);
        });
        clothRepository.save(clothes);
    }

    @Override
    public List<Customers> getCustomerByOrderNumber(Integer orderNumber) {
        return clothRepository.findRemaingWeavingCustomerByOrderNumber(orderNumber, getLocationsForCurrentUser().getId());
    }

    private Locations getLocationsForCurrentUser() {
        List<Locations> locationByUser = userLocationRepository.findLocationByUser(AuthUtil.getCurrentUser());
        if (locationByUser == null || locationByUser.isEmpty()) {
            throw new RuntimeException("No location available");
        }
        return locationByUser.get(0);
    }

    @Override
    public List<Designs> getDesignByOrderNumberAndCustomer(Integer orderNumber, Long customerId) {
        return clothRepository.findRemaingWeavingDesignByOrderNumber(orderNumber, customerId, getLocationsForCurrentUser().getId());
    }

    @Override
    public List<Prints> getPrintByOrderNumberAndCustomer(Integer orderNumber, Long customerId, Long designId, Long sizeId) {
        return clothRepository.findRemaingWeavingPrintByOrderNumber(orderNumber, customerId, designId, getLocationsForCurrentUser().getId(), sizeId);
    }

    @Override
    public List<Sizes> getSizesForCustomerAndOrderNumber(Integer orderNumber, Long customerId, Long designId) {
        return clothRepository.findRemaingWeavingSizeByOrderNumber(orderNumber, customerId, designId, getLocationsForCurrentUser().getId());
    }

    @Override
    public List<String> getExtraFieldByOrderNumberAndCustomer(Integer orderNumber, Long customerId, Long designId, Long sizeId, Long printId, Long colorId) {
        return clothRepository.getExtraFieldByOrderNumberAndCustomer(orderNumber, customerId, designId, sizeId, printId ,colorId);
    }

    @Override
    public List<Colors> getColorsForForCustomerAndOrderNumber(Integer orderNumber, Long customerId, Long designId, Long sizeId, Long printId) {
        return clothRepository.getColorsByOrderNumberAndCustomer(orderNumber, customerId, designId, sizeId, printId);
    }

}
