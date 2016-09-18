package com.pms.app.service.impl;

import com.pms.app.repo.ClothRepository;
import com.pms.app.schema.*;
import com.pms.app.service.ReportingService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final ClothRepository clothRepository;

    @Autowired
    public ReportingServiceImpl(ClothRepository clothRepository) {
        this.clothRepository = clothRepository;
    }

    @Override
    public void createProformaInvoice(Long orderNo, Long customerId, HttpServletResponse httpServletResponse) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = getWithHeaderImage(workbook, "/images/pms-logo.png");

        List<ClothInvoiceResource> resources = clothRepository.findClothesForProformaInvoice(orderNo.intValue(), customerId);
        if (resources == null || resources.isEmpty()) {
            Row headerRow = sheet.createRow(8);
            Cell snHeadCell = headerRow.createCell(8);
            snHeadCell.setCellValue("No cloth available");
        } else {
            int rownum = 8;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row topHeaderRow = sheet.createRow(7);


            Cell headerNameCell = topHeaderRow.createCell(2);
            headerNameCell.setCellValue("PROFORMA INVOICE");
            headerNameCell.setCellStyle(style);


            Row startRow = sheet.createRow(rownum);


            Cell startCustomerName = startRow.createCell(0);
            startCustomerName.setCellValue("Customer Name");
            startCustomerName.setCellStyle(style);


            Cell startDeliveryDate = startRow.createCell(1);
            startDeliveryDate.setCellValue("Delivery Date");
            startDeliveryDate.setCellStyle(style);


            Cell startOrderNo = startRow.createCell(2);
            startOrderNo.setCellValue("Order No");
            startOrderNo.setCellStyle(style);

            Cell startOrderDate = startRow.createCell(3);
            startOrderDate.setCellValue("Order Date");
            startOrderDate.setCellStyle(style);
            rownum++;


            Row startValueRow = sheet.createRow(rownum);


            Cell startValueCustomerName = startValueRow.createCell(0);
            startValueCustomerName.setCellValue(resources.get(0).getCustomerName());
            startValueCustomerName.setCellStyle(style);


            Cell startvalueDeliveryDate = startValueRow.createCell(1);
            startvalueDeliveryDate.setCellValue(getDateString(resources.get(0).getDeliveryDate()));
            startvalueDeliveryDate.setCellStyle(style);

            Cell startValueOrderNo = startValueRow.createCell(2);
            startValueOrderNo.setCellValue(resources.get(0).getOrderNo());
            startValueOrderNo.setCellStyle(style);

            Cell startValueOrderDate = startValueRow.createCell(3);
            startValueOrderDate.setCellValue(getDateString(resources.get(0).getOrderDate()));
            startValueOrderDate.setCellStyle(style);

            rownum++;
            rownum++;
            Row headerRow = sheet.createRow(rownum);
            Cell snHeadCell = headerRow.createCell(0);
            snHeadCell.setCellValue("Marks & No. & Kind of  Pkgs. ");
            snHeadCell.setCellStyle(style);
            Cell designHeadCell = headerRow.createCell(1);
            designHeadCell.setCellValue("Particulars");
            designHeadCell.setCellStyle(style);
            Cell sizHeadCell = headerRow.createCell(2);
            sizHeadCell.setCellValue("Size");
            sizHeadCell.setCellStyle(style);

            Cell quantityHeadCell = headerRow.createCell(3);
            quantityHeadCell.setCellValue("Qty");
            quantityHeadCell.setCellStyle(style);


            Cell priceHeader = headerRow.createCell(4);
            priceHeader.setCellValue("Rate");
            priceHeader.setCellStyle(style);


            Cell printAmountHeader = headerRow.createCell(5);
            printAmountHeader.setCellValue("Print Rate");
            printAmountHeader.setCellStyle(style);


            Cell amountHeadCell = headerRow.createCell(6);
            amountHeadCell.setCellValue("Amount");
            amountHeadCell.setCellStyle(style);


            Cell emptyHeadCell = headerRow.createCell(7);
            emptyHeadCell.setCellStyle(style);
            Long totalCount = 0L;

            Double totalPrice = 0D;
            for (ClothInvoiceResource cloth : resources) {
                rownum++;


                Row row = sheet.createRow(rownum);
                Cell snCell = row.createCell(0);
                snCell.setCellValue("");

                Cell designCell = row.createCell(1);
                designCell.setCellValue(cloth.getDesignName());


                Cell sizeCell = row.createCell(2);
                sizeCell.setCellValue(cloth.getSizeName());


                Cell quantityCell = row.createCell(3);
                quantityCell.setCellValue(cloth.getClothCount());


                Cell priceCell = row.createCell(4);
                priceCell.setCellValue(cloth.getCurrency() + cloth.getPrice());


                Cell printAmount = row.createCell(5);
                printAmount.setCellValue(cloth.getPrint() != null ? cloth.getPrintCurrency() + cloth.getPrintAmount() : "");


                Cell amountCell = row.createCell(6);
                amountCell.setCellValue(cloth.getCurrency() + (cloth.getClothCount() * cloth.getPrice()));


                Cell emptyCell = row.createCell(7);

                totalCount += cloth.getClothCount();
                totalPrice += cloth.getPrice() * cloth.getClothCount();

            }

            rownum++;
            rownum++;
            Row lastRow = sheet.createRow(rownum);
            Cell totalValueTextCell = lastRow.createCell(1);
            totalValueTextCell.setCellStyle(style);
            totalValueTextCell.setCellValue("Total");
            Cell totalValueCell = lastRow.createCell(3);
            totalValueCell.setCellValue(totalCount);
            totalValueCell.setCellStyle(style);
            Cell totalPriceCell = lastRow.createCell(6);
            totalPriceCell.setCellValue(resources.get(0).getCurrency() + totalPrice);
            totalPriceCell.setCellStyle(style);
        }


        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }
        exportToExcel(httpServletResponse, workbook);
    }

    private HSSFSheet getWithHeaderImage(HSSFWorkbook workbook, String logo) {
        HSSFSheet sheet = workbook.createSheet("Clothes Data");

        try {

            Resource resource = new ClassPathResource(logo);
            InputStream resourceInputStream = resource.getInputStream();
            final CreationHelper helper = workbook.getCreationHelper();
            final Drawing drawing = sheet.createDrawingPatriarch();

            final ClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);


            final int pictureIndex =
                    workbook.addPicture(IOUtils.toByteArray(resourceInputStream), Workbook.PICTURE_TYPE_PNG);


            anchor.setCol1(0);
            anchor.setRow1(0); // same row is okay
            anchor.setRow2(4);
            anchor.setCol2(4);
            final Picture pict = drawing.createPicture(anchor, pictureIndex);
            pict.resize();
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace());
        } catch (IOException aa) {
            System.out.println(aa.getStackTrace());
        }
        return sheet;
    }

    @Override
    public void getClothReport(Long customerId,
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
                               HttpServletResponse httpServletResponse) {
        List<ClothResource> clothResources = clothRepository.findClothResource(customerId,
                locationId,
                orderNo,
                barcode,
                deliverDateFrom,
                deliveryDateTo,
                orderDateFrom,
                orderDateTo,
                role,
                shippingNumber,
                boxNumber,
                isReject);


        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Clothes Data");


        if (clothResources == null || clothResources.isEmpty()) {
            Row headerRow = sheet.createRow(0);
            Cell snHeadCell = headerRow.createCell(0);
            snHeadCell.setCellValue("No cloth available");
        } else {
            int rownum = 0;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row headerRow = sheet.createRow(rownum);

            Cell barcodeHeader = headerRow.createCell(0);
            barcodeHeader.setCellValue("Bar Code");
            barcodeHeader.setCellStyle(style);

            Cell locationHeader = headerRow.createCell(1);
            locationHeader.setCellValue("Location");
            locationHeader.setCellStyle(style);


            Cell priceHeader = headerRow.createCell(2);
            priceHeader.setCellValue("Price");
            priceHeader.setCellStyle(style);

            Cell orderHeader = headerRow.createCell(3);
            orderHeader.setCellValue("Order no");
            orderHeader.setCellStyle(style);


            Cell designNameHeader = headerRow.createCell(4);
            designNameHeader.setCellValue("Design Name");
            designNameHeader.setCellStyle(style);


            Cell yarnNameHeader = headerRow.createCell(5);
            yarnNameHeader.setCellValue("Yarn");
            yarnNameHeader.setCellStyle(style);


            Cell colorCodeHeader = headerRow.createCell(6);
            colorCodeHeader.setCellValue("Color Code");
            colorCodeHeader.setCellStyle(style);


            Cell sizeHeader = headerRow.createCell(7);
            sizeHeader.setCellValue("Size");
            sizeHeader.setCellStyle(style);


            Cell customerHeader = headerRow.createCell(8);
            customerHeader.setCellValue("Customer");
            customerHeader.setCellStyle(style);


            Cell deliveryHeader = headerRow.createCell(9);
            deliveryHeader.setCellValue("Delivery Date");
            deliveryHeader.setCellStyle(style);


            Cell printHeader = headerRow.createCell(10);
            printHeader.setCellValue("Print");
            printHeader.setCellStyle(style);


            Cell printAmountHeader = headerRow.createCell(11);
            printAmountHeader.setCellValue("Print Amount");
            printAmountHeader.setCellStyle(style);


            Cell shippingNumberHeader = headerRow.createCell(12);
            shippingNumberHeader.setCellValue("Shipping Number");
            shippingNumberHeader.setCellStyle(style);


            Cell boxNumberHeader = headerRow.createCell(13);
            boxNumberHeader.setCellValue("Box Number");
            boxNumberHeader.setCellStyle(style);

            Cell weightHeader = headerRow.createCell(14);
            weightHeader.setCellValue("Weight");
            weightHeader.setCellStyle(style);


            Cell rejectHeader = headerRow.createCell(15);
            rejectHeader.setCellValue("Reject");
            rejectHeader.setCellStyle(style);


            for (ClothResource cloth : clothResources) {
                rownum++;
                Boolean isOdd = rownum % 2 != 0;
                HSSFCellStyle newStyle = workbook.createCellStyle();
                newStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                newStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                newStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


                Row row = sheet.createRow(rownum);
                Cell barcodeCell = row.createCell(0);
                barcodeCell.setCellValue(cloth.getId());
                barcodeCell.setCellStyle(isOdd ? style : newStyle);

                Cell locationCell = row.createCell(1);
                locationCell.setCellValue(cloth.getLocationName() == null ? "N/A" : cloth.getLocationName());
                locationCell.setCellStyle(isOdd ? style : newStyle);


                Cell priceCell = row.createCell(2);
                priceCell.setCellValue(cloth.getPrice().getCustomer().getCurrencyName() + cloth.getPrice().getAmount());
                priceCell.setCellStyle(isOdd ? style : newStyle);

                Cell orderCell = row.createCell(3);
                orderCell.setCellValue(cloth.getOrder_no());
                orderCell.setCellStyle(isOdd ? style : newStyle);


                Cell designNameCell = row.createCell(4);
                designNameCell.setCellValue(cloth.getPrice().getDesignName());
                designNameCell.setCellStyle(isOdd ? style : newStyle);


                Cell yarnName = row.createCell(5);
                yarnName.setCellValue(cloth.getPrice().getYarnName());
                yarnName.setCellStyle(isOdd ? style : newStyle);


                Cell colorCodeCell = row.createCell(6);
                colorCodeCell.setCellValue(cloth.getColorCode());
                colorCodeCell.setCellStyle(isOdd ? style : newStyle);


                Cell sizeCell = row.createCell(7);
                sizeCell.setCellValue(cloth.getPrice().getSizeName());
                sizeCell.setCellStyle(isOdd ? style : newStyle);


                Cell customerCell = row.createCell(8);
                customerCell.setCellValue(cloth.getPrice().getCustomer().getName());
                customerCell.setCellStyle(isOdd ? style : newStyle);


                Cell deliveryCell = row.createCell(9);
                deliveryCell.setCellValue(getDateString(cloth.getDelivery_date()));
                deliveryCell.setCellStyle(isOdd ? style : newStyle);


                Cell printCell = row.createCell(10);
                printCell.setCellValue(cloth.getPrint().getName() == null ? "N/A" : cloth.getPrint().getName());
                printCell.setCellStyle(isOdd ? style : newStyle);


                Cell printAmountCell = row.createCell(11);
                printAmountCell.setCellValue(cloth.getPrint().getCurrencyName() == null ? "N/A" : (cloth.getPrint().getCurrencyName() + cloth.getPrint().getAmount()));
                printAmountCell.setCellStyle(isOdd ? style : newStyle);


                Cell shippingNumberCell = row.createCell(12);
                shippingNumberCell.setCellValue(cloth.getShippingNumber() == null ? "N/A" : (cloth.getShippingNumber()));
                shippingNumberCell.setCellStyle(isOdd ? style : newStyle);


                Cell boxNumberCell = row.createCell(13);
                boxNumberCell.setCellValue(cloth.getBoxNumber() == null ? "N/A" : cloth.getBoxNumber());
                boxNumberCell.setCellStyle(isOdd ? style : newStyle);

                Cell weightCell = row.createCell(14);
                weightCell.setCellValue(cloth.getWeight() == null ? "N/A" : cloth.getWeight());
                weightCell.setCellStyle(isOdd ? style : newStyle);

                Cell isRejectCell = row.createCell(15);
                isRejectCell.setCellValue(cloth.getIsReturn() == null ? "N/A" : cloth.getIsReturn().toString());
                isRejectCell.setCellStyle(isOdd ? style : newStyle);


            }


            for (int i = 0; i <= 15; i++) {
                sheet.autoSizeColumn(i);
            }

        }
        exportToExcel(httpServletResponse, workbook);

    }


    private void exportToExcel(HttpServletResponse httpServletResponse, HSSFWorkbook workbook) {
        try {
            httpServletResponse.setContentType("application/vnd.ms-excel");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyExcel.xls");
            OutputStream out = httpServletResponse.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDateString(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy").format(new Date(date.getTime()));

    }

    @Override
    public void createOrderSheet(Long orderNo, Long customerId, HttpServletResponse httpServletResponse) {

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Clothes Data");


        List<ClothOrderResource> resources = clothRepository.findClothesForOrderAndCustomer(orderNo.intValue(), customerId);
        if (resources == null || resources.isEmpty()) {
            Row headerRow = sheet.createRow(0);
            Cell snHeadCell = headerRow.createCell(0);
            snHeadCell.setCellValue("No cloth available");
        } else {
            int rownum = 0;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row startRow = sheet.createRow(rownum);

            Cell startCustomerName = startRow.createCell(0);
            startCustomerName.setCellValue("Customer Name");
            startCustomerName.setCellStyle(style);


            Cell startDeliveryDate = startRow.createCell(1);
            startDeliveryDate.setCellValue("Delivery Date");
            startDeliveryDate.setCellStyle(style);


            Cell startOrderNo = startRow.createCell(2);
            startOrderNo.setCellValue("Order No");
            startOrderNo.setCellStyle(style);

            Cell startOrderDate = startRow.createCell(3);
            startOrderDate.setCellValue("Order Date");
            startOrderDate.setCellStyle(style);
            rownum++;


            Row startValueRow = sheet.createRow(rownum);


            Cell startValueCustomerName = startValueRow.createCell(0);
            startValueCustomerName.setCellValue(resources.get(0).getCustomerName());
            startValueCustomerName.setCellStyle(style);


            Cell startvalueDeliveryDate = startValueRow.createCell(1);
            startvalueDeliveryDate.setCellValue(getDateString(resources.get(0).getDeliveryDate()));
            startvalueDeliveryDate.setCellStyle(style);

            Cell startValueOrderNo = startValueRow.createCell(2);
            startValueOrderNo.setCellValue(resources.get(0).getOrderNo());
            startValueOrderNo.setCellStyle(style);

            Cell startValueOrderDate = startValueRow.createCell(3);
            startValueOrderDate.setCellValue(getDateString(resources.get(0).getOrderDate()));
            startValueOrderDate.setCellStyle(style);

            rownum++;
            rownum++;
            Row headerRow = sheet.createRow(rownum);
            Cell snHeadCell = headerRow.createCell(0);
            snHeadCell.setCellValue("SN");
            snHeadCell.setCellStyle(style);
            Cell designHeadCell = headerRow.createCell(1);
            designHeadCell.setCellValue("Design");
            designHeadCell.setCellStyle(style);
            Cell sizHeadCell = headerRow.createCell(2);
            sizHeadCell.setCellValue("Size");
            sizHeadCell.setCellStyle(style);
            Cell colorHeadCell = headerRow.createCell(3);
            colorHeadCell.setCellValue("Color");
            colorHeadCell.setCellStyle(style);

            Cell colorCodeHeadCell = headerRow.createCell(4);
            colorCodeHeadCell.setCellValue("Color Code");
            colorCodeHeadCell.setCellStyle(style);

            Cell quantityHeadCell = headerRow.createCell(5);
            quantityHeadCell.setCellValue("Quantity");
            quantityHeadCell.setCellStyle(style);

            Cell printHeadCell = headerRow.createCell(6);
            printHeadCell.setCellValue("Print");
            printHeadCell.setCellStyle(style);

            Cell emptyHeadCell = headerRow.createCell(7);
            emptyHeadCell.setCellStyle(style);
            Long totalCount = 0L;
            for (ClothOrderResource cloth : resources) {
                rownum++;
                Boolean isOdd = rownum % 2 != 0;
                HSSFCellStyle newStyle = workbook.createCellStyle();
                newStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                newStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                newStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


                Row row = sheet.createRow(rownum);
                Cell snCell = row.createCell(0);
                snCell.setCellValue(rownum - 3);

                snCell.setCellStyle(isOdd ? style : newStyle);
                Cell designCell = row.createCell(1);
                designCell.setCellValue(cloth.getDesignName());

                designCell.setCellStyle(isOdd ? style : newStyle);

                Cell sizeCell = row.createCell(2);
                sizeCell.setCellValue(cloth.getSizeName());

                sizeCell.setCellStyle(isOdd ? style : newStyle);


                Cell colorCell = row.createCell(3);
                colorCell.setCellValue(cloth.getColor());


                colorCell.setCellStyle(isOdd ? style : newStyle);


                Cell colorCodeCell = row.createCell(4);
                colorCodeCell.setCellValue(cloth.getColorCode());


                colorCodeCell.setCellStyle(isOdd ? style : newStyle);


                Cell quantityCell = row.createCell(5);
                quantityCell.setCellValue(cloth.getClothCount());


                quantityCell.setCellStyle(isOdd ? style : newStyle);


                Cell printCell = row.createCell(6);
                printCell.setCellValue(cloth.getPrint());


                printCell.setCellStyle(isOdd ? style : newStyle);

                Cell emptyCell = row.createCell(7);
                emptyCell.setCellStyle(isOdd ? style : newStyle);

                totalCount += cloth.getClothCount();

            }

            rownum++;
            Row lastRow = sheet.createRow(rownum);
            Cell totalValueTextCell = lastRow.createCell(4);
            totalValueTextCell.setCellValue("Total");
            Cell totalValueCell = lastRow.createCell(5);
            totalValueCell.setCellValue(totalCount);
        }

        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }
        exportToExcel(httpServletResponse, workbook);
    }

    @Override
    public void createPendingList(Long orderNo, Long customerId, HttpServletResponse httpServletResponse) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("Clothes Data");


        List<ClothOrderPendingResource> resources = clothRepository.findClothesPendingForOrderAndCustomer(orderNo.intValue(), customerId);
        if (resources == null || resources.isEmpty()) {
            Row headerRow = sheet.createRow(0);
            Cell snHeadCell = headerRow.createCell(0);
            snHeadCell.setCellValue("No cloth available");
        } else {
            int rownum = 0;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row startRow = sheet.createRow(rownum);

            Cell startCustomerName = startRow.createCell(0);
            startCustomerName.setCellValue("Customer Name");
            startCustomerName.setCellStyle(style);


            Cell startDeliveryDate = startRow.createCell(1);
            startDeliveryDate.setCellValue("Delivery Date");
            startDeliveryDate.setCellStyle(style);


            Cell startOrderNo = startRow.createCell(2);
            startOrderNo.setCellValue("Order No");
            startOrderNo.setCellStyle(style);

            Cell startOrderDate = startRow.createCell(3);
            startOrderDate.setCellValue("Order Date");
            startOrderDate.setCellStyle(style);
            rownum++;


            Row startValueRow = sheet.createRow(rownum);


            Cell startValueCustomerName = startValueRow.createCell(0);
            startValueCustomerName.setCellValue(resources.get(0).getCustomerName());
            startValueCustomerName.setCellStyle(style);


            Cell startvalueDeliveryDate = startValueRow.createCell(1);
            startvalueDeliveryDate.setCellValue(getDateString(resources.get(0).getDeliveryDate()));
            startvalueDeliveryDate.setCellStyle(style);

            Cell startValueOrderNo = startValueRow.createCell(2);
            startValueOrderNo.setCellValue(resources.get(0).getOrderNo());
            startValueOrderNo.setCellStyle(style);

            Cell startValueOrderDate = startValueRow.createCell(3);
            startValueOrderDate.setCellValue(getDateString(resources.get(0).getOrderDate()));
            startValueOrderDate.setCellStyle(style);

            rownum++;
            rownum++;
            Row headerRow = sheet.createRow(rownum);
            Cell snHeadCell = headerRow.createCell(0);
            snHeadCell.setCellValue("SN");
            snHeadCell.setCellStyle(style);
            Cell designHeadCell = headerRow.createCell(1);
            designHeadCell.setCellValue("Design");
            designHeadCell.setCellStyle(style);
            Cell sizHeadCell = headerRow.createCell(2);
            sizHeadCell.setCellValue("Size");
            sizHeadCell.setCellStyle(style);
            Cell colorHeadCell = headerRow.createCell(3);
            colorHeadCell.setCellValue("Color");
            colorHeadCell.setCellStyle(style);

            Cell colorCodeHeadCell = headerRow.createCell(4);
            colorCodeHeadCell.setCellValue("Color Code");
            colorCodeHeadCell.setCellStyle(style);

            Cell printHeadCell = headerRow.createCell(5);
            printHeadCell.setCellValue("Print");
            printHeadCell.setCellStyle(style);

            Cell locationHeadCell = headerRow.createCell(6);
            locationHeadCell.setCellValue("Location");
            locationHeadCell.setCellStyle(style);

            Cell emptyHeadCell = headerRow.createCell(7);
            emptyHeadCell.setCellStyle(style);
            Long totalCount = 0L;
            for (ClothOrderPendingResource cloth : resources) {
                rownum++;
                Boolean isOdd = rownum % 2 != 0;
                HSSFCellStyle newStyle = workbook.createCellStyle();
                newStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                newStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                newStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                newStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


                Row row = sheet.createRow(rownum);
                Cell snCell = row.createCell(0);
                snCell.setCellValue(rownum - 3);

                snCell.setCellStyle(isOdd ? style : newStyle);
                Cell designCell = row.createCell(1);
                designCell.setCellValue(cloth.getDesignName());

                designCell.setCellStyle(isOdd ? style : newStyle);

                Cell sizeCell = row.createCell(2);
                sizeCell.setCellValue(cloth.getSizeName());

                sizeCell.setCellStyle(isOdd ? style : newStyle);


                Cell colorCell = row.createCell(3);
                colorCell.setCellValue(cloth.getColor());


                colorCell.setCellStyle(isOdd ? style : newStyle);


                Cell colorCodeCell = row.createCell(4);
                colorCodeCell.setCellValue(cloth.getColorCode());


                colorCodeCell.setCellStyle(isOdd ? style : newStyle);


                Cell printCell = row.createCell(5);
                printCell.setCellValue(cloth.getPrint());
                printCell.setCellStyle(isOdd ? style : newStyle);


                Cell locationCell = row.createCell(6);
                locationCell.setCellValue(cloth.getLocation() == null ? "N/A" : cloth.getLocation());
                locationCell.setCellStyle(isOdd ? style : newStyle);


                Cell emptyCell = row.createCell(7);
                emptyCell.setCellStyle(isOdd ? style : newStyle);
                totalCount++;

            }

        }
        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }
        exportToExcel(httpServletResponse, workbook);
    }

    @Override
    public void createShippingList(String shippingNumber, HttpServletResponse httpServletResponse) {
        List<ClothShippingResource> clothResources = clothRepository.findShippedCloth(shippingNumber);


        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = getWithHeaderImage(workbook, "/images/pms-logo.png");


        if (clothResources == null || clothResources.isEmpty()) {
            Row headerRow = sheet.createRow(7);
            Cell snHeadCell = headerRow.createCell(7);
            snHeadCell.setCellValue("No cloth available");
        } else {


            HSSFCellStyle mainStyle = workbook.createCellStyle();
            mainStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            mainStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            mainStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            mainStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row topRow = sheet.createRow(7);

            Cell cell = topRow.createCell(2);
            cell.setCellStyle(mainStyle);
            cell.setCellValue("Packing List -" + shippingNumber);


            int rownum = 8;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row headerRow = sheet.createRow(rownum);


            Cell designNameHeader = headerRow.createCell(0);
            designNameHeader.setCellValue("Design Name");
            designNameHeader.setCellStyle(style);


            Cell sizeHeader = headerRow.createCell(1);
            sizeHeader.setCellValue("Size");
            sizeHeader.setCellStyle(style);

            Cell colorCodeHeader = headerRow.createCell(2);
            colorCodeHeader.setCellValue("Color");
            colorCodeHeader.setCellStyle(style);


            Cell rejectHeader = headerRow.createCell(3);
            rejectHeader.setCellValue("QTY");
            rejectHeader.setCellStyle(style);


            Map<String, List<ClothShippingResource>> orderAndResourceMap = new HashMap<String, List<ClothShippingResource>>();
            clothResources.forEach(r -> {

                List<ClothShippingResource> clothShippingResources = orderAndResourceMap.get(r.getBoxNumber());
                if (clothShippingResources == null) {
                    clothShippingResources = new ArrayList<ClothShippingResource>();
                    clothShippingResources.add(r);
                    orderAndResourceMap.put(r.getBoxNumber(), clothShippingResources);
                } else {
                    clothShippingResources.add(r);
                }

            });


            Long total = 0L;


            for (String boxNumber : orderAndResourceMap.keySet()) {
                Long subTotal = 0L;
                rownum++;
                HSSFCellStyle boxNumberStyle = workbook.createCellStyle();
                boxNumberStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                boxNumberStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                boxNumberStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                boxNumberStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                boxNumberStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                boxNumberStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


                Row row = sheet.createRow(rownum);
                Cell barcodeCell = row.createCell(0);
                barcodeCell.setCellValue("BOX-" + boxNumber);
                barcodeCell.setCellStyle(boxNumberStyle);

                Map<Integer, List<ClothShippingResource>> orderNoAndResourceMap = new HashMap<Integer, List<ClothShippingResource>>();
                clothResources.forEach(r -> {

                    List<ClothShippingResource> clothShippingResources = orderNoAndResourceMap.get(r.getOrder_no());
                    if (clothShippingResources == null) {
                        clothShippingResources = new ArrayList<ClothShippingResource>();
                        clothShippingResources.add(r);
                        orderNoAndResourceMap.put(r.getOrder_no(), clothShippingResources);
                    } else {
                        clothShippingResources.add(r);
                    }

                });


                for (Integer orderNo : orderNoAndResourceMap.keySet()) {
                    rownum++;
                    HSSFCellStyle orderStyle = workbook.createCellStyle();
                    orderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                    orderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    orderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                    orderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                    orderStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    orderStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

                    Row orderRow = sheet.createRow(rownum);
                    Cell orderNoCell = orderRow.createCell(0);
                    orderNoCell.setCellValue("PO-" + orderNo);
                    orderNoCell.setCellStyle(orderStyle);


                    List<ClothShippingResource> resources = orderNoAndResourceMap.get(orderNo);


                    for (ClothShippingResource res : resources) {
                        rownum++;

                        Row clothRow = sheet.createRow(rownum);


                        Cell designCell = clothRow.createCell(0);
                        designCell.setCellValue(res.getDesignName());


                        Cell sizeCell = clothRow.createCell(1);
                        sizeCell.setCellValue(res.getSizeName());


                        Cell colorCell = clothRow.createCell(2);
                        colorCell.setCellValue(res.getColorName());

                        Cell snCell = clothRow.createCell(3);
                        snCell.setCellValue(res.getCount());
                        subTotal += res.getCount();


                    }

                }
                rownum++;
                Row subTotalRow = sheet.createRow(rownum);

                HSSFCellStyle subTotalRowStyle = workbook.createCellStyle();
                subTotalRowStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                subTotalRowStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                subTotalRowStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                subTotalRowStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                subTotalRowStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                subTotalRowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


                Cell designCell = subTotalRow.createCell(0);
                designCell.setCellStyle(subTotalRowStyle);
                designCell.setCellValue("SUB-TOTAL");

                Cell colorCell = subTotalRow.createCell(1);
                colorCell.setCellStyle(subTotalRowStyle);
                Cell sizeCell = subTotalRow.createCell(2);
                sizeCell.setCellStyle(subTotalRowStyle);
                Cell subTotalCell = subTotalRow.createCell(3);
                subTotalCell.setCellStyle(subTotalRowStyle);
                subTotalCell.setCellValue(subTotal);
                total += subTotal;

            }


            rownum++;
            Row totalRow = sheet.createRow(rownum);
            Cell designCell = totalRow.createCell(0);
            designCell.setCellValue("GRAND-TOTAL");

            Cell grandtotal = totalRow.createCell(1);
            grandtotal.setCellValue(total);


            for (int i = 0; i <= 3; i++) {
                sheet.autoSizeColumn(i);
            }

        }
        exportToExcel(httpServletResponse, workbook);

    }

    @Override
    public void createInvoice(Long orderNo, Long customerId, String shippingNumber, HttpServletResponse httpServletResponse) {

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = getWithHeaderImage(workbook, "/images/pms-logo.png");

        List<ClothInvoiceResource> resources = clothRepository.findInvoice(orderNo, customerId,shippingNumber);
        if (resources == null || resources.isEmpty()) {
            Row headerRow = sheet.createRow(7);
            Cell snHeadCell = headerRow.createCell(7);
            snHeadCell.setCellValue("No cloth available");
        } else {
            int rownum = 8;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row topHeaderRow = sheet.createRow(7);


            Cell headerNameCell = topHeaderRow.createCell(2);
            headerNameCell.setCellValue("COMMERCIAL INVOICE");
            headerNameCell.setCellStyle(style);


            Row startRow = sheet.createRow(rownum);


            Cell startCustomerName = startRow.createCell(0);
            startCustomerName.setCellValue("Customer Name");
            startCustomerName.setCellStyle(style);


            Cell startDeliveryDate = startRow.createCell(1);
            startDeliveryDate.setCellValue("Delivery Date");
            startDeliveryDate.setCellStyle(style);


            Cell startOrderNo = startRow.createCell(2);
            startOrderNo.setCellValue("Order No");
            startOrderNo.setCellStyle(style);

            Cell startOrderDate = startRow.createCell(3);
            startOrderDate.setCellValue("Order Date");
            startOrderDate.setCellStyle(style);
            rownum++;


            Row startValueRow = sheet.createRow(rownum);


            Cell startValueCustomerName = startValueRow.createCell(0);
            startValueCustomerName.setCellValue(resources.get(0).getCustomerName());
            startValueCustomerName.setCellStyle(style);


            Cell startvalueDeliveryDate = startValueRow.createCell(1);
            startvalueDeliveryDate.setCellValue(getDateString(resources.get(0).getDeliveryDate()));
            startvalueDeliveryDate.setCellStyle(style);

            Cell startValueOrderNo = startValueRow.createCell(2);
            startValueOrderNo.setCellValue(resources.get(0).getOrderNo());
            startValueOrderNo.setCellStyle(style);

            Cell startValueOrderDate = startValueRow.createCell(3);
            startValueOrderDate.setCellValue(getDateString(resources.get(0).getOrderDate()));
            startValueOrderDate.setCellStyle(style);

            rownum++;
            rownum++;
            Row headerRow = sheet.createRow(rownum);
            Cell snHeadCell = headerRow.createCell(0);
            snHeadCell.setCellValue("Marks & No. & Kind of  Pkgs. ");
            snHeadCell.setCellStyle(style);
            Cell designHeadCell = headerRow.createCell(1);
            designHeadCell.setCellValue("Particulars");
            designHeadCell.setCellStyle(style);
            Cell sizHeadCell = headerRow.createCell(2);
            sizHeadCell.setCellValue("Size");
            sizHeadCell.setCellStyle(style);

            Cell quantityHeadCell = headerRow.createCell(3);
            quantityHeadCell.setCellValue("Qty");
            quantityHeadCell.setCellStyle(style);


            Cell priceHeader = headerRow.createCell(4);
            priceHeader.setCellValue("Rate");
            priceHeader.setCellStyle(style);


            Cell printAmountHeader = headerRow.createCell(5);
            printAmountHeader.setCellValue("Print Rate");
            printAmountHeader.setCellStyle(style);


            Cell amountHeadCell = headerRow.createCell(6);
            amountHeadCell.setCellValue("Amount");
            amountHeadCell.setCellStyle(style);


            Cell emptyHeadCell = headerRow.createCell(7);
            emptyHeadCell.setCellStyle(style);
            Long totalCount = 0L;

            Double totalPrice = 0D;


            Map<String, List<ClothInvoiceResource>> orderAndResourceMap = new HashMap<String, List<ClothInvoiceResource>>();
            resources.forEach(r -> {

                List<ClothInvoiceResource> clothShippingResources = orderAndResourceMap.get(r.getBoxNumber());
                if (clothShippingResources == null) {
                    clothShippingResources = new ArrayList<ClothInvoiceResource>();
                    clothShippingResources.add(r);
                    orderAndResourceMap.put(r.getBoxNumber(), clothShippingResources);
                } else {
                    clothShippingResources.add(r);
                }

            });

            for (String boxNumber : orderAndResourceMap.keySet()) {
                rownum++;

                HSSFCellStyle boxNumberStyle = workbook.createCellStyle();
                boxNumberStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                boxNumberStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                boxNumberStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                boxNumberStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                boxNumberStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                boxNumberStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

                Row boxRow = sheet.createRow(rownum);
                Cell boxCell = boxRow.createCell(1);
                boxCell.setCellValue("BOX-" + boxNumber);
                boxCell.setCellStyle(boxNumberStyle);

                Map<Integer, List<ClothInvoiceResource>> orderNoAndResourceMap = new HashMap<Integer, List<ClothInvoiceResource>>();
                orderAndResourceMap.get(boxNumber).forEach(r -> {

                    List<ClothInvoiceResource> clothShippingResources = orderNoAndResourceMap.get(r.getOrderNo());
                    if (clothShippingResources == null) {
                        clothShippingResources = new ArrayList<ClothInvoiceResource>();
                        clothShippingResources.add(r);
                        orderNoAndResourceMap.put(r.getOrderNo(), clothShippingResources);
                    } else {
                        clothShippingResources.add(r);
                    }

                });


                for (Integer iOrderNo : orderNoAndResourceMap.keySet()) {

                    rownum++;

                    HSSFCellStyle orderNumberStyle = workbook.createCellStyle();
                    orderNumberStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                    orderNumberStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    orderNumberStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                    orderNumberStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                    orderNumberStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    orderNumberStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

                    Row orderRow = sheet.createRow(rownum);
                    Cell orderCell = orderRow.createCell(1);
                    orderCell.setCellValue("PO-" + iOrderNo);
                    orderCell.setCellStyle(orderNumberStyle);
                    for (ClothInvoiceResource cloth : orderNoAndResourceMap.get(iOrderNo)) {
                        rownum++;

                        Row row = sheet.createRow(rownum);
                        Cell snCell = row.createCell(0);
                        snCell.setCellValue("");

                        Cell designCell = row.createCell(1);
                        designCell.setCellValue(cloth.getDesignName());


                        Cell sizeCell = row.createCell(2);
                        sizeCell.setCellValue(cloth.getSizeName());


                        Cell quantityCell = row.createCell(3);
                        quantityCell.setCellValue(cloth.getClothCount());


                        Cell priceCell = row.createCell(4);
                        priceCell.setCellValue(cloth.getCurrency() + cloth.getPrice());


                        Cell printAmount = row.createCell(5);
                        printAmount.setCellValue(cloth.getPrint() != null ? cloth.getPrintCurrency() + cloth.getPrintAmount() : "");


                        Cell amountCell = row.createCell(6);
                        amountCell.setCellValue(cloth.getCurrency() + (cloth.getClothCount() * cloth.getPrice()));


                        Cell emptyCell = row.createCell(7);

                        totalCount += cloth.getClothCount();
                        totalPrice += cloth.getPrice() * cloth.getClothCount();

                    }
                }

            }

            rownum++;
            rownum++;
            Row lastRow = sheet.createRow(rownum);
            Cell totalValueTextCell = lastRow.createCell(1);
            totalValueTextCell.setCellStyle(style);
            totalValueTextCell.setCellValue("Total");
            Cell totalValueCell = lastRow.createCell(3);
            totalValueCell.setCellValue(totalCount);
            totalValueCell.setCellStyle(style);
            Cell totalPriceCell = lastRow.createCell(6);
            totalPriceCell.setCellValue(resources.get(0).getCurrency() + totalPrice);
            totalPriceCell.setCellStyle(style);

            for (int i = 0; i <= 7; i++) {
                sheet.autoSizeColumn(i);
            }
            exportToExcel(httpServletResponse, workbook);

        }
    }

}
