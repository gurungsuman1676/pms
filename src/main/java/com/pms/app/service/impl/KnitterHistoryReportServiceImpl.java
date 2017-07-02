package com.pms.app.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.pms.app.repo.KnitterMachineHistoryRepository;
import com.pms.app.schema.KnitterHistoryReportResource;
import com.pms.app.service.KnitterHistoryReportService;
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
 * Created by arjun on 6/30/2017.
 */

@Service
public class KnitterHistoryReportServiceImpl implements KnitterHistoryReportService {

    private final KnitterMachineHistoryRepository knitterMachineHistoryRepository;

    @Autowired
    public KnitterHistoryReportServiceImpl(KnitterMachineHistoryRepository knitterMachineHistoryRepository) {
        this.knitterMachineHistoryRepository = knitterMachineHistoryRepository;
    }

    @Override
    public void getHistoryReport(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, HttpServletResponse httpServletResponse) {
        List<KnitterHistoryReportResource> historyReportResources = knitterMachineHistoryRepository.getAllResource(knitterId, machineId, completedDate, dateFrom, dateTo);
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = getWithHeaderImage(workbook, "/images/pms-logo.png");

        if (historyReportResources == null || historyReportResources.isEmpty()) {
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

            Cell startKnitterName = startRow.createCell(2);
            startKnitterName.setCellValue("Knitter");
            startKnitterName.setCellStyle(style);

            Cell startMachineName = startRow.createCell(3);
            startMachineName.setCellValue("Machine");
            startMachineName.setCellStyle(style);


            Cell startOrderNo = startRow.createCell(4);
            startOrderNo.setCellValue("Order No");
            startOrderNo.setCellStyle(style);


            Cell startValueCustomerName = startRow.createCell(5);
            startValueCustomerName.setCellValue("Customer");
            startValueCustomerName.setCellStyle(style);


            Cell designCell = startRow.createCell(6);
            designCell.setCellValue("Design");
            designCell.setCellStyle(style);

            Cell yarnCell = startRow.createCell(7);
            yarnCell.setCellValue("Yarn");
            yarnCell.setCellStyle(style);


            Cell sizeHeadCell = startRow.createCell(8);
            sizeHeadCell.setCellValue("Size");
            sizeHeadCell.setCellStyle(style);
            Cell gaugeCell = startRow.createCell(9);
            gaugeCell.setCellValue("Gauge");
            gaugeCell.setCellStyle(style);
            Cell settingHead = startRow.createCell(10);
            settingHead.setCellValue("Setting");
            settingHead.setCellStyle(style);
            Cell reOrderCell = startRow.createCell(11);
            reOrderCell.setCellValue("Re-Order");
            reOrderCell.setCellStyle(style);


            for (KnitterHistoryReportResource historyReportResource : historyReportResources) {
                rownum++;


                Row row = sheet.createRow(rownum);
                Cell completedCell = row.createCell(0);
                completedCell.setCellValue(getDateString(historyReportResource.getCompletedOn()));

                Cell deliveryCell = row.createCell(1);
                deliveryCell.setCellValue(getDateString(historyReportResource.getDeliveryDate()));

                Cell knitterCell = row.createCell(2);
                knitterCell.setCellValue(historyReportResource.getKnitterName());

                Cell machineCell = row.createCell(3);
                machineCell.setCellValue(historyReportResource.getMachineName());

                Cell orderNoCell = row.createCell(4);
                orderNoCell.setCellValue(historyReportResource.getOrderNo());

                Cell customerCell = row.createCell(5);
                customerCell.setCellValue(historyReportResource.getCustomerName());

                Cell designCellVal = row.createCell(6);
                designCellVal.setCellValue(historyReportResource.getDesignName());

                Cell yarnCellVal = row.createCell(7);
                yarnCellVal.setCellValue(historyReportResource.getYarnName());

                Cell sizeCell = row.createCell(8);
                sizeCell.setCellValue(historyReportResource.getSizeName());

                Cell gaugeCellVal = row.createCell(9);
                gaugeCellVal.setCellValue(historyReportResource.getGauge());

                Cell settingVal = row.createCell(10);
                settingVal.setCellValue(historyReportResource.getSetting());

                Cell reOrderCellVal = row.createCell(11);
                reOrderCellVal.setCellValue(historyReportResource.getReOrder() == null ? " " : historyReportResource.getReOrder() ? " Re-Order" : "Bulk");


            }

            rownum++;
            rownum++;
            Row lastRow = sheet.createRow(rownum);
            Cell totalValueTextCell = lastRow.createCell(1);
            totalValueTextCell.setCellStyle(style);
            totalValueTextCell.setCellValue("Total");
            Cell totalValueCell = lastRow.createCell(2);
            totalValueCell.setCellStyle(style);
            totalValueCell.setCellValue(historyReportResources.size());
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
