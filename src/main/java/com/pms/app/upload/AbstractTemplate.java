package com.pms.app.upload;

import com.pms.app.domain.Clothes;
import com.pms.app.domain.Colors;
import com.pms.app.domain.Customers;
import com.pms.app.domain.Designs;
import com.pms.app.domain.Prices;
import com.pms.app.domain.Prints;
import com.pms.app.domain.Sizes;
import com.pms.app.domain.Status;
import com.pms.app.domain.Yarns;
import com.pms.app.repo.ClothRepository;
import com.pms.app.repo.ColorRepository;
import com.pms.app.repo.CustomerRepository;
import com.pms.app.repo.DesignRepository;
import com.pms.app.repo.PriceRepository;
import com.pms.app.repo.PrintRepository;
import com.pms.app.repo.SizeRepository;
import com.pms.app.repo.YarnRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by arjun on 6/13/2017.
 */


@Component
@Transactional
abstract class AbstractTemplate {

    @Autowired
    ClothRepository clothRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    DesignRepository designRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    ColorRepository colorRepository;

    @Autowired
    PrintRepository printRepository;

    @Autowired
    YarnRepository yarnRepository;

    @Autowired
    EntityManager entityManager;

    final int clothType;

    final Iterator<Row> rowsIterator;

    final Workbook wb;

    Row currentRow;

    Long customerId;

    Date deliveryDate;

    Integer orderNumber;

    boolean reOrder = false;

    BiFunction<Row, String, String> nameExtractFormula;

    private final static String REORDER_ALIAS = "REORDER";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public AbstractTemplate(MultipartFile file, int clothType) throws IOException {
        wb = getWorkbook(file);
        this.clothType = clothType;
        this.rowsIterator = wb.getSheetAt(0).rowIterator();
    }

    public void setExtractFormula(BiFunction<Row, String, String> nameExtractFormula) {
        this.nameExtractFormula = nameExtractFormula;
    }


    public Workbook getWorkbook(MultipartFile file) throws IOException {
        Workbook wb = null;
        if (file.getOriginalFilename().toUpperCase().endsWith("XLS")) {
            wb = new HSSFWorkbook(new BufferedInputStream(file.getInputStream()));
        }

        if (file.getOriginalFilename().toUpperCase().endsWith("XLSX")) {
            wb = new XSSFWorkbook(new BufferedInputStream(file.getInputStream()));
        }
        return wb;

    }


    void getCustomerName(String alias) {
        while (rowsIterator.hasNext()) {
            currentRow = rowsIterator.next();
            String customerName = nameExtractFormula.apply(currentRow, alias);
            if (customerName != null) {
                customerId = customerRepository.findCustomeIdByName(customerName);
                if (customerId == null) {
                    throw new RuntimeException("Invalid customer name " + customerName);
                }
                return;

            }
        }
        throw new RuntimeException("Customer name  field is not available");
    }

    void getDeliveryDate(String alias) {
        String deliveryDate = nameExtractFormula.apply(currentRow, alias);
        if (deliveryDate == null || deliveryDate.isEmpty()) {
            throw new RuntimeException("Delivery date field is not available");
        }

        try {
            this.deliveryDate = dateFormat.parse(deliveryDate);
        } catch (Exception e) {
            throw new RuntimeException(" Invalid Delivery Date " + deliveryDate);

        }
    }

    void getOrderNo(String alias) {
        while (rowsIterator.hasNext()) {
            currentRow = rowsIterator.next();
            String orderNo = nameExtractFormula.apply(currentRow, alias);
            if (orderNo != null) {
                try {
                    orderNo = orderNo.replaceAll(" ", "");
                    if (orderNo.toUpperCase().contains(REORDER_ALIAS)) {
                        reOrder = true;
                    }
                    this.orderNumber = Integer.valueOf(orderNo.toUpperCase().replaceAll(REORDER_ALIAS, ""));
                    return;
                } catch (Exception e) {
                    throw new RuntimeException("Invalid order number " + orderNo);
                }
            }
        }
        throw new RuntimeException("Order number  field is not available");
    }


    List<Clothes> getCloth(String colorCode, String colorName, String yarnName,
                           Map<String, Integer> sizeAndNumberMap,
                           String designName, String printName, String... attrs) {
        List<Clothes> clothesList = new ArrayList<>();
        for (String sizeName : sizeAndNumberMap.keySet()) {
            if (colorCode != null) {
                colorCode = colorCode.trim();
            }
            designName = designName.trim();
            for (int i = 0; i < sizeAndNumberMap.get(sizeName); i++) {
                Clothes clothes = new Clothes();
                Colors color = getColorByName(colorCode, colorName, yarnName);
                clothes.setColor(color);
                clothes.setDeliver_date(deliveryDate);
                clothes.setOrder_no(orderNumber);
                clothes.setCustomer(entityManager.getReference(Customers.class, customerId));
                clothes.setExtraField(String.join("-", attrs));
                clothes.setType(clothType);
                clothes.setReOrder(reOrder);
                clothes.setStatus(Status.ACTIVE.toString());
                Long designId = designRepository.findIdByNameAndCustomer(designName, customerId);
                if (designId == null) {
                    Designs designs = new Designs();
                    designs.setGauge(0D);
                    designs.setCustomer(entityManager.getReference(Customers.class, customerId));
                    designs.setName(designName);
                    designId = designRepository.save(designs).getId();
                }
                Long sizeId = sizeRepository.findIdByName(sizeName.trim());

                if (sizeId == null) {
                    Sizes sizes = new Sizes();
                    sizes.setName(sizeName.trim());
                    sizeId = sizeRepository.save(sizes).getId();
                }
                Prices price = getPrices(designName, sizeName, color, designId, sizeId);

                if (printName != null) {
                    printName = printName.trim();
                    List<Long> printId = printRepository.findByNameAndSizeId(printName, sizeId);
                    if (printId == null || printId.isEmpty()) {
                        Prints prints = new Prints();
                        prints.setAmount(0D);
                        prints.setCurrency(customerRepository.findCurrencyByCustomer(customerId));
                        prints.setName(printName);
                        prints.setSize(entityManager.getReference(Sizes.class, sizeId));
                        prints = printRepository.save(prints);
                        printId = Collections.singletonList(prints.getId());
                    }
                    if (printId.size() > 1) {
                        throw new RuntimeException("Multiple print available by name " + printName);
                    }
                    clothes.setPrint(entityManager.getReference(Prints.class, printId.get(0)));

                }

                clothes.setPrice(price);
                clothesList.add(clothes);
            }
        }
        return clothesList;
    }

    public Prices getPrices(String designName, String sizeName, Colors color, Long designId, Long sizeId) {
        Prices price = priceRepository.findByDesignAndSizeAndYarn(designId, sizeId, color.getYarn().getId());
        if (price == null) {
            price = new Prices();
            price.setDesign(entityManager.getReference(Designs.class, designId));
            price.setSize(entityManager.getReference(Sizes.class, sizeId));
            price.setYarn(entityManager.getReference(Yarns.class, color.getYarn().getId()));
            price.setAmount(0D);
            price = priceRepository.save(price);
        }
        return price;
    }

    public Colors getColorByName(String colorCode, String colorName, String yarnName) {
        Colors color = colorRepository.findByCode(colorCode);
        if (color == null) {
            Yarns yarns = yarnRepository.findByName(yarnName);
            if (yarns == null) {
                yarns = new Yarns();
                yarns.setName(yarnName);
                yarns = yarnRepository.save(yarns);
            }
            color = new Colors();
            color.setCode(colorCode);
            color.setYarn(yarns);
            color.setName_company(colorName);
            color = colorRepository.save(color);
        }
        return color;
    }

}
