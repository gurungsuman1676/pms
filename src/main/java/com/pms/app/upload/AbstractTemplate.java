package com.pms.app.upload;

import com.pms.app.domain.Clothes;
import com.pms.app.domain.Colors;
import com.pms.app.domain.Customers;
import com.pms.app.domain.Status;
import com.pms.app.repo.ClothRepository;
import com.pms.app.repo.ColorRepository;
import com.pms.app.repo.CustomerRepository;
import com.pms.app.repo.DesignRepository;
import com.pms.app.repo.PriceRepository;
import com.pms.app.repo.SizeRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by arjun on 6/13/2017.
 */


@Component
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
    EntityManager entityManager;

    final int clothType;

    final Iterator<Row> rowsIterator;

    final Workbook wb;

    Row currentRow;

    Long customerId;

    Date deliveryDate;

    Integer orderNumber;

    BiFunction<Row, String, String> nameExtractFormula;

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
                    this.orderNumber = Integer.valueOf(orderNo);
                    return;
                } catch (Exception e) {
                    throw new RuntimeException("Invalid order number " + orderNo);
                }
            }
        }
        throw new RuntimeException("Order number  field is not available");
    }


    List<Clothes> getCloth(String colorCode,
                           Map<String, Integer> sizeAndNumberMap,
                           String designName) {
        List<Clothes> clothesList = new ArrayList<>();
        for (String sizeName : sizeAndNumberMap.keySet()) {
            for (int i = 0; i < sizeAndNumberMap.get(sizeName); i++) {
                Clothes clothes = new Clothes();
                Colors color = colorRepository.findByCode(colorCode.trim());
                if (color == null) {
                    throw new RuntimeException("Color " + colorCode + " not found for at row " + (currentRow.getRowNum() + 1));
                }
                clothes.setColor(color);
                clothes.setDeliver_date(deliveryDate);
                clothes.setOrder_no(orderNumber);
                clothes.setCustomer(entityManager.getReference(Customers.class, customerId));

                clothes.setType(clothType);
                clothes.setStatus(Status.ACTIVE.toString());
                Long designId = designRepository.findIdByName(designName.trim());
                if (designId == null) {
                    throw new RuntimeException("Design " + designName + " not found for at row " + currentRow.getRowNum());
                }
                Long sizeId = sizeRepository.findIdByName(sizeName.trim());

                if (sizeId == null) {
                    throw new RuntimeException("Size " + sizeName + " not found for at row " + currentRow.getRowNum());
                }
                clothes.setPrice(priceRepository.findByDesignAndSizeAndYarn(designId, sizeId, color.getYarn().getId()));
                if (clothes.getPrice() == null) {
                    throw new RuntimeException("Price for yarn " + color.getYarn().getName() + ", design " + designName + " , size " + sizeName + "  not found for at row " + (currentRow.getRowNum() + 1));
                }
                clothesList.add(clothes);
            }
        }
        return clothesList;
    }

}
