package com.pms.app.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.pms.app.repo.ShawlEntryRepository;
import com.pms.app.schema.ShawlEntryResource;
import com.pms.app.service.ShawlReportService;
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
public class ShawlReportServiceImpl implements ShawlReportService {

    private final ShawlEntryRepository shawlEntryRepository;

    @Autowired
    public ShawlReportServiceImpl(ShawlEntryRepository shawlEntryRepository) {
        this.shawlEntryRepository = shawlEntryRepository;
    }

    @Override
    public void generateReport(Long locationId, Long sizeId, Long yarnId, Long customerId, Long colorId, Long shawlId, Date entryDateFrom, Date entryDateTo, Date exportDateFrom, Date exportDateTo, HttpServletResponse httpServletResponse) {
        List<ShawlEntryResource> entryResources = shawlEntryRepository.getAllResources(locationId, sizeId, yarnId, customerId, colorId, shawlId, entryDateFrom, entryDateTo, exportDateFrom, exportDateTo);
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = getWithHeaderImage(workbook, "/images/pms-logo.png");

        if (entryResources == null || entryResources.isEmpty()) {
            Row headerRow = sheet.createRow(8);
            Cell snHeadCell = headerRow.createCell(8);
            snHeadCell.setCellValue("No shawl available");
        } else {
            int rownum = 8;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);


            Row topHeaderRow = sheet.createRow(7);


            Cell headerNameCell = topHeaderRow.createCell(2);
            headerNameCell.setCellValue("Shawl Report");
            headerNameCell.setCellStyle(style);


            Row startRow = sheet.createRow(++rownum);


            Cell shawlHeader = startRow.createCell(0);
            shawlHeader.setCellValue("Shawl");
            shawlHeader.setCellStyle(style);


            Cell importedForHeader = startRow.createCell(1);
            importedForHeader.setCellValue("Imported For");
            importedForHeader.setCellStyle(style);

            Cell exportedForHeader = startRow.createCell(2);
            exportedForHeader.setCellValue("Exported For");
            exportedForHeader.setCellStyle(style);


            Cell yarnHeaderCell = startRow.createCell(3);
            yarnHeaderCell.setCellValue("Yarn");
            yarnHeaderCell.setCellStyle(style);

            Cell colorHeaderCell = startRow.createCell(4);
            colorHeaderCell.setCellValue("Color");
            colorHeaderCell.setCellStyle(style);


            Cell sizeHeaderCell = startRow.createCell(5);
            sizeHeaderCell.setCellValue("Size");
            sizeHeaderCell.setCellStyle(style);


            Cell locationHeaderCell = startRow.createCell(6);
            locationHeaderCell.setCellValue("Location");
            locationHeaderCell.setCellStyle(style);


            Cell entryDateHeaderCell = startRow.createCell(7);
            entryDateHeaderCell.setCellValue("Entry Date");
            entryDateHeaderCell.setCellStyle(style);


            Cell exportDateHeaderCell = startRow.createCell(8);
            exportDateHeaderCell.setCellValue("Export Date");
            exportDateHeaderCell.setCellStyle(style);


            for (ShawlEntryResource shawlEntryResource : entryResources) {
                rownum++;


                Row row = sheet.createRow(rownum);
                Cell shawlCell = row.createCell(0);
                shawlCell.setCellValue(shawlEntryResource.getShawl().getName());

                Cell importedForCell = row.createCell(1);
                importedForCell.setCellValue(shawlEntryResource.getImportCustomer().getName());

                Cell exportedForCell = row.createCell(2);
                exportedForCell.setCellValue(shawlEntryResource.getExportCustomer().getName());


                Cell yarnCell = row.createCell(3);
                yarnCell.setCellValue(shawlEntryResource.getYarn().getName());


                Cell colorCell = row.createCell(4);
                colorCell.setCellValue(shawlEntryResource.getColor().getName());

                Cell sizeCell = row.createCell(5);
                sizeCell.setCellValue(shawlEntryResource.getSize().getName());

                Cell locationCell = row.createCell(6);
                locationCell.setCellValue(shawlEntryResource.getLocation().getName());

                Cell entryDateCell = row.createCell(7);
                entryDateCell.setCellValue(getDateString(shawlEntryResource.getImportDate()));

                Cell exportDateCell = row.createCell(8);
                exportDateCell.setCellValue(getDateString(shawlEntryResource.getExportDate()));
            }

            rownum++;
            rownum++;
            Row lastRow = sheet.createRow(rownum);
            Cell totalValueTextCell = lastRow.createCell(1);
            totalValueTextCell.setCellStyle(style);
            totalValueTextCell.setCellValue("Total");
            Cell totalValueCell = lastRow.createCell(2);
            totalValueCell.setCellStyle(style);
            totalValueCell.setCellValue(entryResources.size());
        }


        for (int i = 0; i <= 8; i++) {
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
        if(date == null){
            return "N/A";
        }
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
