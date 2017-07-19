package com.pms.app.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.pms.app.repo.WeavingWorkLogRepository;
import com.pms.app.schema.WeavingLogResource;
import com.pms.app.service.WeavingReportService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by arjun on 7/12/2017.
 */

@Service
public class WeavingReportServiceImpl implements WeavingReportService {

    private final WeavingWorkLogRepository weavingWorkLogRepository;

    @Autowired
    public WeavingReportServiceImpl(WeavingWorkLogRepository weavingWorkLogRepository) {
        this.weavingWorkLogRepository = weavingWorkLogRepository;
    }

    @Override
    public void getReport(Long customerId, Long locationId, Integer orderNo, String receiptNumber, Date createdDateFrom, Date createdDateTo, Long designId, Long printId, Long sizeId, HttpServletResponse httpServletResponse) {
        List<WeavingLogResource> logs = weavingWorkLogRepository.getForReport(customerId, locationId, orderNo, receiptNumber, createdDateFrom, createdDateTo, designId, printId, sizeId);
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = getWithHeaderImage(workbook, "/images/pms-logo.png");

        if (logs == null || logs.isEmpty()) {
            Row headerRow = sheet.createRow(8);
            Cell snHeadCell = headerRow.createCell(8);
            snHeadCell.setCellValue("No Log available");
        } else {
            int rownum = 8;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row topHeaderRow = sheet.createRow(7);


            Cell headerNameCell = topHeaderRow.createCell(2);
            headerNameCell.setCellValue("Weaving Log ");
            headerNameCell.setCellStyle(style);


            Row startRow = sheet.createRow(++rownum);


            Cell receiptHeader = startRow.createCell(0);
            receiptHeader.setCellValue("Receipt No");
            receiptHeader.setCellStyle(style);

            Cell startCompltedOn = startRow.createCell(1);
            startCompltedOn.setCellValue("Completed On");
            startCompltedOn.setCellStyle(style);

            Cell startOrderNo = startRow.createCell(2);
            startOrderNo.setCellValue("Order No");
            startOrderNo.setCellStyle(style);


            Cell startLocationName = startRow.createCell(3);
            startLocationName.setCellValue("Location");
            startLocationName.setCellStyle(style);


            Cell startValueCustomerName = startRow.createCell(4);
            startValueCustomerName.setCellValue("Customer");
            startValueCustomerName.setCellStyle(style);


            Cell designCell = startRow.createCell(5);
            designCell.setCellValue("Design");
            designCell.setCellStyle(style);

            Cell printHeaderCell = startRow.createCell(6);
            printHeaderCell.setCellValue("Print");
            printHeaderCell.setCellStyle(style);


            Cell colorHeadCell = startRow.createCell(7);
            colorHeadCell.setCellValue("Color");
            colorHeadCell.setCellStyle(style);

            Cell extraHeadCell = startRow.createCell(8);
            extraHeadCell.setCellValue("Extra Field");
            extraHeadCell.setCellStyle(style);

            Cell sizeHeadCell = startRow.createCell(9);
            sizeHeadCell.setCellValue("Size");
            sizeHeadCell.setCellStyle(style);

            Cell quantityHeadCell = startRow.createCell(10);
            quantityHeadCell.setCellValue("Quantity");
            quantityHeadCell.setCellStyle(style);


            Cell remarkHeadCellHeadCell = startRow.createCell(11);
            remarkHeadCellHeadCell.setCellValue("Remark");
            remarkHeadCellHeadCell.setCellStyle(style);


            for (WeavingLogResource log : logs) {
                rownum++;


                Row row = sheet.createRow(rownum);


                Cell receiptCell = row.createCell(0);
                receiptCell.setCellValue(log.getReceiptNumber());


                Cell completedCell = row.createCell(1);
                completedCell.setCellValue(getDateString(log.getCreated()));

                Cell orderNoCell = row.createCell(2);
                orderNoCell.setCellValue(log.getOrderNo());

                Cell locationCell = row.createCell(3);
                locationCell.setCellValue(log.getLocationName());


                Cell customerCell = row.createCell(4);
                customerCell.setCellValue(log.getCustomerName());

                Cell designCellVal = row.createCell(5);
                designCellVal.setCellValue(log.getDesignName());

                Cell printCell = row.createCell(6);
                printCell.setCellValue(log.getPrintName());

                Cell colorCell = row.createCell(7);
                colorCell.setCellValue(log.getColorName());

                Cell extraCell = row.createCell(8);
                extraCell.setCellValue(log.getExtraField());

                Cell sizeCell = row.createCell(9);
                sizeCell.setCellValue(log.getSizeName());


                Cell quantityCell = row.createCell(10);
                quantityCell.setCellValue(log.getQuantity());

                Cell remarkCell = row.createCell(11);
                remarkCell.setCellValue(log.getRemarks());

            }

            rownum++;
            rownum++;
            Row lastRow = sheet.createRow(rownum);
            Cell totalValueTextCell = lastRow.createCell(1);
            totalValueTextCell.setCellStyle(style);
            totalValueTextCell.setCellValue("Total");
            Cell totalValueCell = lastRow.createCell(2);
            totalValueCell.setCellStyle(style);
            totalValueCell.setCellValue(logs.size());
        }


        for (int i = 0; i <= 11; i++) {
            sheet.autoSizeColumn(i);
        }
        exportToExcel(httpServletResponse, workbook);
    }

    private void exportToExcel(HttpServletResponse httpServletResponse, HSSFWorkbook workbook) {
        try {
            httpServletResponse.setContentType("application/vnd.ms-excel");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyExcel.xls");
            OutputStream out = httpServletResponse.getOutputStream();
            workbook.write(out);
            Document iText_xls_2_pdf = new Document();
            PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream("Excel2PDF_Output.pdf"));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDateString(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy").format(new Date(date.getTime()));

    }

    private HSSFSheet getWithHeaderImage(HSSFWorkbook workbook, String logo) {
        HSSFSheet sheet = workbook.createSheet("Knitter History");

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
}
