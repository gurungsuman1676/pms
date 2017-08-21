package com.pms.app.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.pms.app.repo.ClothActivityRepository;
import com.pms.app.schema.KnitterHistoryReportResource;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.UserHistoryResource;
import com.pms.app.service.HistoryService;
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
import org.springframework.data.domain.Pageable;
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
 * Created by arjun on 8/21/2017.
 */

@Service
public class HistoryServiceImpl implements HistoryService {
    private final ClothActivityRepository clothActivityRepository;

    @Autowired
    public HistoryServiceImpl(ClothActivityRepository clothActivityRepository) {
        this.clothActivityRepository = clothActivityRepository;
    }

    @Override
    public PageResult<UserHistoryResource> getAll(Date completedDate, Date dateFrom, Date dateTo, String role, Integer orderNo, Pageable pageable) {
        return clothActivityRepository.findActivities(completedDate,dateFrom,dateTo,role,orderNo,pageable);
    }

    @Override
    public void getReport(Date completedDate, Date dateFrom, Date dateTo, String role, Integer orderNo, HttpServletResponse httpServletResponse) {
      List<UserHistoryResource> activities = clothActivityRepository.findActivitiesReport(completedDate,dateFrom,dateTo,role,orderNo);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = getWithHeaderImage(workbook, "/images/pms-logo.png");

        if (activities == null || activities.isEmpty()) {
            Row headerRow = sheet.createRow(8);
            Cell snHeadCell = headerRow.createCell(8);
            snHeadCell.setCellValue("No History available");
        } else {
            int rownum = 8;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row topHeaderRow = sheet.createRow(7);


            Cell headerNameCell = topHeaderRow.createCell(2);
            headerNameCell.setCellValue("Cloth History");
            headerNameCell.setCellStyle(style);


            Row startRow = sheet.createRow(++rownum);


            Cell startCompltedOn = startRow.createCell(0);
            startCompltedOn.setCellValue("Completed On");
            startCompltedOn.setCellStyle(style);


            Cell startDeliveryDate = startRow.createCell(1);
            startDeliveryDate.setCellValue("Delivery Date");
            startDeliveryDate.setCellStyle(style);

            Cell startOrderNo = startRow.createCell(2);
            startOrderNo.setCellValue("Order No");
            startOrderNo.setCellStyle(style);


            Cell startValueCustomerName = startRow.createCell(3);
            startValueCustomerName.setCellValue("Customer");
            startValueCustomerName.setCellStyle(style);


            Cell designCell = startRow.createCell(4);
            designCell.setCellValue("Design");
            designCell.setCellStyle(style);

            Cell yarnCell = startRow.createCell(5);
            yarnCell.setCellValue("Yarn");
            yarnCell.setCellStyle(style);


            Cell sizeHeadCell = startRow.createCell(6);
            sizeHeadCell.setCellValue("Size");
            sizeHeadCell.setCellStyle(style);
            Cell gaugeCell = startRow.createCell(7);
            gaugeCell.setCellValue("Gauge");
            gaugeCell.setCellStyle(style);
            Cell settingHead = startRow.createCell(8);
            settingHead.setCellValue("Setting");
            settingHead.setCellStyle(style);
            Cell reOrderCell = startRow.createCell(9);
            reOrderCell.setCellValue("Re-Order");
            reOrderCell.setCellStyle(style);


            for (UserHistoryResource historyReportResource : activities) {
                rownum++;


                Row row = sheet.createRow(rownum);
                Cell completedCell = row.createCell(0);
                completedCell.setCellValue(getDateString(historyReportResource.getCompletedOn()));

                Cell deliveryCell = row.createCell(1);
                deliveryCell.setCellValue(getDateString(historyReportResource.getDeliveryDate()));

                Cell orderNoCell = row.createCell(2);
                orderNoCell.setCellValue(historyReportResource.getOrderNo());

                Cell customerCell = row.createCell(3);
                customerCell.setCellValue(historyReportResource.getCustomerName());

                Cell designCellVal = row.createCell(4);
                designCellVal.setCellValue(historyReportResource.getDesignName());

                Cell yarnCellVal = row.createCell(5);
                yarnCellVal.setCellValue(historyReportResource.getYarnName());

                Cell sizeCell = row.createCell(6);
                sizeCell.setCellValue(historyReportResource.getSizeName());

                Cell gaugeCellVal = row.createCell(7);
                gaugeCellVal.setCellValue(historyReportResource.getGauge() == null ? "N/A" : historyReportResource.getGauge()+"");

                Cell settingVal = row.createCell(8);
                settingVal.setCellValue(historyReportResource.getSetting() == null ? "N/A" : historyReportResource.getSetting());

                Cell reOrderCellVal = row.createCell(9);
                reOrderCellVal.setCellValue(historyReportResource.getOrderType() == null ? " " : historyReportResource.getOrderType());


            }

            rownum++;
            rownum++;
            Row lastRow = sheet.createRow(rownum);
            Cell totalValueTextCell = lastRow.createCell(1);
            totalValueTextCell.setCellStyle(style);
            totalValueTextCell.setCellValue("Total");
            Cell totalValueCell = lastRow.createCell(2);
            totalValueCell.setCellStyle(style);
            totalValueCell.setCellValue(activities.size());
        }


        for (int i = 0; i <= 9; i++) {
            sheet.autoSizeColumn(i);
        }
        exportToExcel(httpServletResponse, workbook, "Knitting_history_" + getDateStringForName(new Date()));
    }

    private void exportToExcel(HttpServletResponse httpServletResponse, HSSFWorkbook workbook, String fileName) {
        try {
            httpServletResponse.setContentType("application/vnd.ms-excel");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
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

    private String getDateStringForName(Date date) {
        return new SimpleDateFormat("MM-dd-yyyy").format(new Date(date.getTime()));

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
