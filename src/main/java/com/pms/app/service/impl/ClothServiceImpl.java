package com.pms.app.service.impl;

import com.pms.app.domain.*;
import com.pms.app.repo.*;
import com.pms.app.schema.ClothDto;
import com.pms.app.schema.ClothLocationDto;
import com.pms.app.schema.WeavingShippingDTO;
import com.pms.app.security.AuthUtil;
import com.pms.app.service.ClothService;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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
    private final EntityManager entityManager;
    private final WeavingWorkLogRepository weavingWorkLogRepository;
    private final RejectedDocumentRepository rejectedDocumentRepository;

    @Value("${file.path}")
    private String path;

    @Autowired
    public ClothServiceImpl(ClothRepository clothRepository, PriceRepository priceRepository, PrintRepository printRepository, LocationRepository locationRepository, CustomerRepository customerRepository, UserLocationRepository userLocationRepository, ColorRepository colorRepository, ClothActivityRepository clothActivityRepository, EntityManager entityManager, WeavingWorkLogRepository weavingWorkLogRepository, RejectedDocumentRepository rejectedDocumentRepository) {
        this.clothRepository = clothRepository;
        this.priceRepository = priceRepository;
        this.printRepository = printRepository;
        this.locationRepository = locationRepository;
        this.customerRepository = customerRepository;
        this.userLocationRepository = userLocationRepository;
        this.colorRepository = colorRepository;
        this.clothActivityRepository = clothActivityRepository;
        this.entityManager = entityManager;
        this.weavingWorkLogRepository = weavingWorkLogRepository;
        this.rejectedDocumentRepository = rejectedDocumentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Clothes> getClothes(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom, Date deliveryDateTo, Date orderDateFrom,
                                    Date orderDateTo, Pageable pageable, String role, String shippingNumber, String boxNumber,
                                    Boolean isReject, Integer type, Date locationDate, Long designId, Double gauge, String setting, String reOrder, String week, Long colorId) {
        if (locationDate != null && locationId != null && locationId != -1) {
            return clothRepository.findAllForHistoryByDate(customerId,
                    locationId, orderNo, barcode, deliverDateFrom, deliveryDateTo, orderDateFrom, orderDateTo, pageable,
                    role, shippingNumber, boxNumber, isReject, type, locationDate, designId, gauge, setting, reOrder, week, colorId);
        } else {
            return clothRepository.findAllClothes(customerId,
                    locationId, orderNo, barcode, deliverDateFrom, deliveryDateTo, orderDateFrom, orderDateTo, pageable,
                    role, shippingNumber, boxNumber, isReject, type, designId, locationDate, gauge, setting, reOrder, week, colorId);
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
        Locations locations = null;
        if (clothDto.getTypeId() == 0) {
            locations = locationRepository.findByNameAndLocationType(LocationEnum.PRE_KNITTING.getName(), LocationType.KNITTING);
        }

        for (int i = 0; i < clothDto.getQuantity(); i++) {
            Clothes cloth = new Clothes();
            cloth.setDeliver_date(clothDto.getDelivery_date());
            cloth.setPrice(price);
            cloth.setPrint(prints);
            cloth.setOrder_no(clothDto.getOrderNo());
            cloth.setCustomer(customers);
            cloth.setType(clothDto.getTypeId());
            cloth.setStatus(Status.ACTIVE.toString());
            cloth.setColor(color);
            if (cloth.getType() == 0) {
                cloth.setLocation(locations);
            }
            clothes.add(cloth);

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
    @Transactional
    public void updateWeavingCloth(WeavingShippingDTO weavingShippingDTO) {
        Locations locations = locationRepository.findOne(weavingShippingDTO.getLocationId());
        List<Clothes> clothes = clothRepository.findForWeavingShipping(weavingShippingDTO, locations.getId());

        if (clothes.size() < weavingShippingDTO.getQuantity()) {
            throw new RuntimeException("Quantity of cloth (" + weavingShippingDTO.getQuantity() + " ) is less than available clothes (" + clothes.size() + ")");
        }
        if (locations.getName().equalsIgnoreCase(LocationEnum.SHIPPING.getName())) {
            clothes.forEach(c -> {
                c.setBoxNumber(weavingShippingDTO.getBoxNumber());
                c.setShipping(weavingShippingDTO.getShipping());
                c.setLocation(locations);
            });
            clothRepository.save(clothes);
        }
        WeavingWorkLog weavingWorkLog = new WeavingWorkLog();
        weavingWorkLog.setBoxNumber(weavingShippingDTO.getBoxNumber());
        weavingWorkLog.setShipping(weavingShippingDTO.getShipping());
        weavingWorkLog.setColor(entityManager.getReference(Colors.class, weavingShippingDTO.getColorId()));
        weavingWorkLog.setCustomer(entityManager.getReference(Customers.class, weavingShippingDTO.getCustomerId()));
        weavingWorkLog.setDesign(entityManager.getReference(Designs.class, weavingShippingDTO.getDesignId()));
        weavingWorkLog.setExtraField(weavingShippingDTO.getExtraField());
        weavingWorkLog.setLocation(locations);
        weavingWorkLog.setOrderNo(weavingShippingDTO.getOrderNo());
        weavingWorkLog.setPrint(entityManager.getReference(Prints.class, weavingShippingDTO.getPrintId()));
        weavingWorkLog.setQuantity(weavingShippingDTO.getQuantity());
        weavingWorkLog.setReceiptNumber(weavingShippingDTO.getReceiptNumber());
        weavingWorkLog.setRemarks(weavingShippingDTO.getRemarks());
        weavingWorkLog.setSize(entityManager.getReference(Sizes.class, weavingShippingDTO.getSizeId()));
        weavingWorkLog = weavingWorkLogRepository.save(weavingWorkLog);

        if (locations.getName().equalsIgnoreCase(LocationEnum.REJECTED.getName()) && weavingShippingDTO.getDocId() != null) {
            RejectedDocument rejectedDocument = rejectedDocumentRepository.findOne(weavingShippingDTO.getDocId());
            rejectedDocument.setWeavingWorkLog(weavingWorkLog);
            rejectedDocumentRepository.save(rejectedDocument);
        }

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

    @Override
    public Long addDocument(byte[] file) throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        FileOutputStream fos = new FileOutputStream(path + dateFormat.format(date));
        fos.write(file);
        fos.close();
        RejectedDocument rejectedDocument = new RejectedDocument();
        rejectedDocument.setDocPath(dateFormat.format(date));
        rejectedDocument = rejectedDocumentRepository.save(rejectedDocument);
        return rejectedDocument.getId();
    }

    @Override
    public byte[] getDocument(Long workLogId) throws IOException {
        RejectedDocument rejectedDocument = rejectedDocumentRepository.findByWeavingWorkLogId(workLogId);
        Path path = Paths.get(this.path + rejectedDocument.getDocPath());
        return Base64.encode(Files.readAllBytes(path));
    }

    @Override
    @Transactional
    public void deleteWorkLog(Long id) {
        WeavingWorkLog weavingWorkLog = weavingWorkLogRepository.findOne(id);
        weavingWorkLog.setDeleted(true);
        weavingWorkLogRepository.save(weavingWorkLog);
        if (weavingWorkLog.getLocation().getName().equalsIgnoreCase(LocationEnum.SHIPPING.getName())) {
            WeavingShippingDTO weavingShippingDTO = new WeavingShippingDTO();
            weavingShippingDTO.setBoxNumber(weavingWorkLog.getBoxNumber());
            weavingShippingDTO.setShipping(weavingWorkLog.getShipping());
            weavingShippingDTO.setCustomerId(weavingWorkLog.getCustomer().getId());
            weavingShippingDTO.setDesignId(weavingWorkLog.getDesign().getId());
            weavingShippingDTO.setExtraField(weavingWorkLog.getExtraField());
            weavingShippingDTO.setOrderNo(weavingWorkLog.getOrderNo());
            weavingShippingDTO.setPrintId(weavingWorkLog.getPrint().getId());
            weavingShippingDTO.setSizeId(weavingWorkLog.getSize().getId());
            weavingShippingDTO.setQuantity(weavingWorkLog.getQuantity());
            weavingShippingDTO.setColorId(weavingWorkLog.getColor().getId());
            List<Clothes> clothes = clothRepository.findForEnteredWeavingShipping(weavingShippingDTO);
            clothes.forEach(c -> {
                c.setBoxNumber(null);
                c.setShipping(null);
                c.setLocation(null);
            });
            clothRepository.save(clothes);
        }
    }

}
