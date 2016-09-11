package com.pms.app.service.impl;

import com.pms.app.repo.ClothRepository;
import com.pms.app.schema.ClothOrderResource;
import com.pms.app.schema.ClothResource;
import com.pms.app.service.ReportingService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final ClothRepository clothRepository;

    @Autowired
    public ReportingServiceImpl(ClothRepository clothRepository) {
        this.clothRepository = clothRepository;
    }

    @Override
    public void createOrderInvoice(Long orderNo, Long customerId, HttpServletResponse httpServletResponse) {
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
                emptyCell.setCellStyle(isOdd ?style : newStyle);

                totalCount += cloth.getClothCount();

            }

            rownum++;
            Row lastRow = sheet.createRow(rownum);
            Cell totalValueTextCell = lastRow.createCell(4);
            totalValueTextCell.setCellValue("Total");
            Cell totalValueCell = lastRow.createCell(5);
            totalValueCell.setCellValue(totalCount);
        }

        for( int i =0 ; i<= 7;i++){
            sheet.autoSizeColumn(i);
        }
        exportToExcel(httpServletResponse, workbook);
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
        List<ClothResource> clothResources = clothRepository.findClothResource( customerId,
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
                priceCell.setCellValue(cloth.getPrice().getCustomer().getCurrencyName() +cloth.getPrice().getAmount());
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
                printCell.setCellValue(cloth.getPrint().getName() == null ? "N/A" :cloth.getPrint().getName());
                printCell.setCellStyle(isOdd ? style : newStyle);


                Cell printAmountCell = row.createCell(11);
                printAmountCell.setCellValue(cloth.getPrint().getCurrencyName() == null ? "N/A" :(cloth.getPrint().getCurrencyName()+cloth.getPrint().getAmount()));
                printAmountCell.setCellStyle(isOdd ? style : newStyle);


                Cell shippingNumberCell = row.createCell(12);
                shippingNumberCell.setCellValue(cloth.getShippingNumber() == null ? "N/A":(cloth.getShippingNumber()));
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


            for( int i =0 ; i<= 15;i++){
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

    private String getDateString(Date date){
       return new SimpleDateFormat("MM/dd/yyyy").format(new Date(date.getTime()));

    }


}
