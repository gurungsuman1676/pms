package com.pms.app.service.impl;

import com.pms.app.domain.Clothes;
import com.pms.app.domain.Colors;
import com.pms.app.domain.Status;
import com.pms.app.repo.*;
import com.pms.app.service.ExcelUploadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by anepal on 2/20/2017.
 */

@Service
public class ExcelUploadServiceImpl implements ExcelUploadService {

    private final ClothRepository clothRepository;
    private final SizeRepository sizeRepository;
    private final DesignRepository designRepository;
    private final CustomerRepository customerRepository;
    private final PriceRepository priceRepository;
    private final ColorRepository colorRepository;


    @Autowired
    public ExcelUploadServiceImpl(ClothRepository clothRepository, SizeRepository sizeRepository, DesignRepository designRepository, CustomerRepository customerRepository, PriceRepository priceRepository, ColorRepository colorRepository) {
        this.clothRepository = clothRepository;
        this.sizeRepository = sizeRepository;
        this.designRepository = designRepository;
        this.customerRepository = customerRepository;
        this.priceRepository = priceRepository;
        this.colorRepository = colorRepository;
    }

    @Override
    @Transactional
    public void uploadClothes(MultipartFile file, HttpServletResponse httpServletResponse) throws Exception {

        Workbook wb = null;
        if (file.getOriginalFilename().toUpperCase().endsWith("XLS")) {
            wb = new HSSFWorkbook(new BufferedInputStream(file.getInputStream()));
        }

        if (file.getOriginalFilename().toUpperCase().endsWith("XLSX")) {
            wb = new XSSFWorkbook(new BufferedInputStream(file.getInputStream()));
        }

        System.out.println(" sheet " + 0 + "");
        Integer type = 0;
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        String customerName = null;
        String deliveryDateString = null;
        Map<Integer, List<Clothes>> clothesMap = new HashMap<>();
        while (true) {
            Row row = rows.next();
            customerName = getCustomerName(row);
            if (customerName != null) {
                deliveryDateString = getDeliveryDate(row);
                break;
            }
        }

        Long customerId = customerRepository.findCustomeIdByName(customerName.trim());
        if (customerId == null) {
            throw new RuntimeException("Customer  " + customerName + " not found");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (deliveryDateString == null || deliveryDateString.isEmpty()) {
            throw new RuntimeException(" Delivery Date  not found");

        }
        Date deliveryDate = null;
        try {
            deliveryDate = dateFormat.parse(deliveryDateString);
        } catch (Exception e) {
            throw new RuntimeException(" Invalid Delivery Date " + deliveryDateString);

        }

        String orderNumberString = null;
        while (true) {
            Row row = rows.next();
            orderNumberString = getOrderNumber(row);
            if (orderNumberString != null) {
                break;
            }
        }

        if (orderNumberString.isEmpty()) {
            throw new RuntimeException(" Order Number  not found");

        }

        Integer orderNumber = null;
        try {
            orderNumber = Integer.parseInt(orderNumberString);
        } catch (Exception e) {
            throw new RuntimeException(" Invalid Order Number " + orderNumberString);
        }

        int clothInitNumber = 1;


        while (true) {
            Row row = rows.next();
            if (row.getCell(1) != null && row.getCell(1).getStringCellValue() != null
                    && row.getCell(1).getStringCellValue().toUpperCase().contains("Grand Total".toUpperCase())) {
                long count = 0;
                for (List<Clothes> c : clothesMap.values()) {
                    count += c.size();
                }
                if ((long) row.getCell(2).getNumericCellValue() != count) {
                    throw new RuntimeException("Total number of clothes (" + count + ") is not equal to Given Grand Total (" + (int) row.getCell(2).getNumericCellValue() + ")");
                }
                break;
            }
            String designName = null;
            Integer colorColumn = null;
            Map<Integer, String> sizeColumn = new HashMap<>();
            while (true) {
                if (row.getCell(1) != null && row.getCell(1).getStringCellValue() != null
                        && row.getCell(1).getStringCellValue().toUpperCase().contains("TOTAL")) {

                    Iterator<Cell> iterator = row.iterator();
                    int total = 0;
                    while (iterator.hasNext()) {
                        Cell next = iterator.next();
                        if (next != null) {
                            try {
                                int t = (int) next.getNumericCellValue();
                                if (t != 0) {
                                    total = t;
                                }
                            } catch (Exception e) {

                            }
                        }
                    }
                    if (total != clothesMap.get(clothInitNumber).size()) {
                        throw new RuntimeException("Total number  of clothes (" + clothesMap.get(clothInitNumber).size() + ") is not equal to Given Total (" + (int) row.getCell(2).getNumericCellValue() + ") for design no. " + clothInitNumber);
                    }
                    rows.next();
                    clothInitNumber++;
                    break;
                }
                Iterator<Cell> cells = row.cellIterator();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (designName == null) {
                        if (cell.getStringCellValue() != null && cell.getStringCellValue().contains(clothInitNumber + "")) {
                            designName = cells.next().getStringCellValue();
                            clothesMap.put(clothInitNumber, new ArrayList<>());
                        }
                        row = rows.next();
                        break;
                    } else if (colorColumn == null) {
                        while (cells.hasNext()) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if (cell.getStringCellValue() == null || !cell.getStringCellValue().toUpperCase().contains("Color Code".toUpperCase())) {
                                cell = cells.next();
                            } else {
                                break;
                            }
                        }
                        if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains("Color Code".toUpperCase())) {
                            colorColumn = cell.getColumnIndex();
                            while (cells.hasNext()) {
                                Cell nextCell = cells.next();
                                nextCell.setCellType(Cell.CELL_TYPE_STRING);
                                if (nextCell.getStringCellValue() != null && !nextCell.getStringCellValue().isEmpty()) {
                                    if (nextCell.getStringCellValue().toUpperCase().contains("Total".toUpperCase())) {
                                        break;
                                    }
                                    sizeColumn.put(nextCell.getColumnIndex(), nextCell.getStringCellValue().trim());
                                }
                            }
                        }

                        row = rows.next();
                        break;

                    } else {
                        Cell colorCell = row.getCell(colorColumn);
                        colorCell.setCellType(Cell.CELL_TYPE_STRING);
                        String colorCode = colorCell.getStringCellValue();
                        Map<String, Integer> sizeAndNumberMap = new HashMap<>();
                        for (Integer sizeEntryColum : sizeColumn.keySet()) {
                            Cell sizeCell = row.getCell(sizeEntryColum);
                            if (sizeCell != null) {
                                sizeCell.setCellType(Cell.CELL_TYPE_STRING);
                                if (sizeCell.getStringCellValue() != null && !sizeCell.getStringCellValue().isEmpty()) {
                                    sizeAndNumberMap.put(sizeColumn.get(sizeEntryColum), Integer.parseInt(sizeCell.getStringCellValue()));
                                }
                            }
                        }
                        List<Clothes> clothes = getCloth(type, customerId, orderNumber, deliveryDate, colorCode, sizeAndNumberMap, designName, row.getRowNum());
                        clothesMap.get(clothInitNumber).addAll(clothes);
                        row = rows.next();
                        break;
                    }

                }

            }


        }
        for (Integer stepValue : clothesMap.keySet()) {
            clothRepository.save(clothesMap.get(stepValue));

        }

    }


    private String getDeliveryDate(Row row) {
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains("DELIVERY DATE".toUpperCase())) {
                Cell next = cells.next();
                next.setCellType(Cell.CELL_TYPE_STRING);
                return next.getStringCellValue();
            }
        }
        return null;
    }

    private String getWeavingDeliveryDate(Row row) {
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains("DELIVERY DATE".toUpperCase())) {
                String[] dateArray = cell.getStringCellValue().split(":");
                return dateArray[1].replaceAll(" ", "");
            }
        }
        return null;
    }

    private String getWeavingCustomerName(Row row) {
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains("ORDER".toUpperCase())) {
                String[] customers = cell.getStringCellValue().split(":");
                return customers[1].replaceAll(" ", "");
            }
        }
        return null;

    }

    private String getCustomerName(Row row) {
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains("CUSTOMER NAME".toUpperCase())) {
                Cell next = cells.next();
                next.setCellType(Cell.CELL_TYPE_STRING);
                return next.getStringCellValue();
            }
        }
        return null;
    }

    private String getOrderNumber(Row row) {
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains("ORDER NO".toUpperCase())) {
                Cell next = cells.next();
                next.setCellType(Cell.CELL_TYPE_STRING);
                return next.getStringCellValue();
            }
        }
        return null;
    }

    private String getWeavingOrderNumber(Row row) {
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains("ORDER".toUpperCase())) {
                String cellValue = cell.getStringCellValue().toUpperCase();
                cellValue = cellValue.replaceAll("ORDER", "");
                return cellValue.replaceAll(" ", "");
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void uploadWeavingClothes(MultipartFile file, HttpServletResponse httpServletResponse) throws Exception {

        Workbook wb = null;
        if (file.getOriginalFilename().toUpperCase().endsWith("XLS")) {
            wb = new HSSFWorkbook(new BufferedInputStream(file.getInputStream()));
        }

        if (file.getOriginalFilename().toUpperCase().endsWith("XLSX")) {
            wb = new XSSFWorkbook(new BufferedInputStream(file.getInputStream()));
        }

//        Workbook wb = WorkbookFactory.create(file);


        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            System.out.println(" sheet " + i + "");
            Integer type = 1;
            Sheet sheet = wb.getSheetAt(i);
            Iterator<Row> rows = sheet.rowIterator();
            String customerName = null;
            String deliveryDateString = null;
            Map<Integer, List<Clothes>> clothesMap = new HashMap<>();
            while (true) {
                Row row = rows.next();
                customerName = getWeavingCustomerName(row);
                if (customerName != null) {
                    deliveryDateString = getWeavingDeliveryDate(row);
                    break;
                }
            }

            Long customerId = customerRepository.findCustomeIdByName(customerName.trim());
            if (customerId == null) {
                throw new RuntimeException("Customer  " + customerName + " not found");
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            if (deliveryDateString == null || deliveryDateString.isEmpty()) {
                throw new RuntimeException(" Delivery Date  not found");

            }
            Date deliveryDate = null;
            try {
                deliveryDate = dateFormat.parse(deliveryDateString);
            } catch (Exception e) {
                throw new RuntimeException(" Invalid Delivery Date " + deliveryDateString);

            }

            String orderNumberString = null;
            while (true) {
                Row row = rows.next();
                orderNumberString = getOrderNumber(row);
                if (orderNumberString != null) {
                    break;
                }
            }

            if (orderNumberString.isEmpty()) {
                throw new RuntimeException(" Order Number  not found");

            }

            Integer orderNumber = null;
            try {
                orderNumber = Integer.parseInt(orderNumberString);
            } catch (Exception e) {
                throw new RuntimeException(" Invalid Order Number " + orderNumberString);
            }

            int clothInitNumber = 1;


            while (true) {
                Row row = rows.next();
                if (row.getCell(1) != null && row.getCell(1).getStringCellValue() != null && row.getCell(1).getStringCellValue().toUpperCase().contains("Grand Total".toUpperCase())) {
                    long count = 0;
                    for (List<Clothes> c : clothesMap.values()) {
                        count += c.size();
                    }
                    if ((long) row.getCell(2).getNumericCellValue() != count) {
                        throw new RuntimeException("Total number of clothes (" + count + ") is not equal to Given Grand Total (" + (int) row.getCell(2).getNumericCellValue() + ")");
                    }
                    break;
                }
                String designName = null;
                Integer colorColumn = null;
                Map<Integer, String> sizeColumn = new HashMap<>();
                while (true) {
                    if (row.getCell(1) != null && row.getCell(1).getStringCellValue() != null && row.getCell(1).getStringCellValue().toUpperCase().contains("TOTAL")) {
                        if ((int) row.getCell(2).getNumericCellValue() != clothesMap.get(clothInitNumber).size()) {
                            throw new RuntimeException("Total number  of clothes (" + clothesMap.get(clothInitNumber).size() + ") is not equal to Given Total (" + (int) row.getCell(2).getNumericCellValue() + ") for design no. " + clothInitNumber);
                        }
                        rows.next();
                        clothInitNumber++;
                        break;
                    }
                    Iterator<Cell> cells = row.cellIterator();
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (designName == null) {
                            if (cell.getStringCellValue() != null && cell.getStringCellValue().contains(clothInitNumber + "")) {
                                designName = cells.next().getStringCellValue();
                                clothesMap.put(clothInitNumber, new ArrayList<>());
                            }
                            row = rows.next();
                            break;
                        } else if (colorColumn == null) {
                            while (cells.hasNext()) {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                if (cell.getStringCellValue() == null || !cell.getStringCellValue().toUpperCase().contains("Color Code".toUpperCase())) {
                                    cell = cells.next();
                                } else {
                                    break;
                                }
                            }
                            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains("Color Code".toUpperCase())) {
                                colorColumn = cell.getColumnIndex();
                                while (cells.hasNext()) {
                                    Cell nextCell = cells.next();
                                    nextCell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (nextCell.getStringCellValue() != null && !nextCell.getStringCellValue().isEmpty()) {
                                        if (nextCell.getStringCellValue().toUpperCase().contains("Total".toUpperCase())) {
                                            break;
                                        }
                                        sizeColumn.put(nextCell.getColumnIndex(), nextCell.getStringCellValue().trim());
                                    }
                                }
                            }

                            row = rows.next();
                            break;

                        } else {
                            Cell colorCell = row.getCell(colorColumn);
                            colorCell.setCellType(Cell.CELL_TYPE_STRING);
                            String colorCode = colorCell.getStringCellValue();
                            Map<String, Integer> sizeAndNumberMap = new HashMap<>();
                            for (Integer sizeEntryColum : sizeColumn.keySet()) {
                                Cell sizeCell = row.getCell(sizeEntryColum);
                                if (sizeCell != null) {
                                    sizeCell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (sizeCell.getStringCellValue() != null && !sizeCell.getStringCellValue().isEmpty()) {
                                        sizeAndNumberMap.put(sizeColumn.get(sizeEntryColum), Integer.parseInt(sizeCell.getStringCellValue()));
                                    }
                                }
                            }
                            List<Clothes> clothes = getCloth(type, customerId, orderNumber, deliveryDate, colorCode, sizeAndNumberMap, designName, row.getRowNum());
                            clothesMap.get(clothInitNumber).addAll(clothes);
                            row = rows.next();
                            break;
                        }

                    }

                }


            }
            for (Integer stepValue : clothesMap.keySet()) {
                clothRepository.save(clothesMap.get(stepValue));

            }

        }
    }


    private List<Clothes> getCloth(Integer type,
                                   Long customerId,
                                   Integer orderNumber,
                                   Date deliveryDate,
                                   String colorCode,
                                   Map<String, Integer> sizeAndNumberMap,
                                   String designName,
                                   int rowNum) {
        List<Clothes> clothesList = new ArrayList<>();
        for (String sizeName : sizeAndNumberMap.keySet()) {
            for (int i = 0; i < sizeAndNumberMap.get(sizeName); i++) {
                Clothes clothes = new Clothes();
                Colors color = colorRepository.findByCode(colorCode.trim());
                if (color == null) {
                    throw new RuntimeException("Color " + colorCode + " not found for at row " + (rowNum + 1));
                }
                clothes.setColor(color);
                clothes.setDeliver_date(deliveryDate);
                clothes.setOrder_no(orderNumber);
                clothes.setCustomer(customerRepository.findOne(customerId));

                clothes.setType(type);
                clothes.setStatus(Status.ACTIVE.toString());
                Long designId = designRepository.findIdByName(designName.trim());
                if (designId == null) {
                    throw new RuntimeException("Design " + designName + " not found for at row " + (rowNum));
                }
                Long sizeId = sizeRepository.findIdByName(sizeName.trim());

                if (sizeId == null) {
                    throw new RuntimeException("Size " + sizeName + " not found for at row " + (rowNum));
                }
                clothes.setPrice(priceRepository.findByDesignAndSizeAndYarn(designId, sizeId, color.getYarn().getId()));
                if (clothes.getPrice() == null) {
                    throw new RuntimeException("Price for yarn " + color.getYarn().getName() + ", design " + designName + " , size " + sizeName + "  not found for at row " + (rowNum + 1));
                }
                clothesList.add(clothes);
            }
        }
        return clothesList;
    }

}
