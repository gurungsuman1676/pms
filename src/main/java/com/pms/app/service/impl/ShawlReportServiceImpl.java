package com.pms.app.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.pms.app.repo.ShawlInventoryRepository;
import com.pms.app.schema.ShawlInventoryResource;
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
import java.util.List;

/**
 * Created by arjun on 6/30/2017.
 */

@Service
public class ShawlReportServiceImpl implements ShawlReportService {

    private final ShawlInventoryRepository shawlEntryRepository;

    @Autowired
    public ShawlReportServiceImpl(ShawlInventoryRepository shawlEntryRepository) {
        this.shawlEntryRepository = shawlEntryRepository;
    }

    @Override
    public void generateReport(Long sizeId, Long colorId, Long designId, HttpServletResponse httpServletResponse) {
        List<ShawlInventoryResource> entryResources = shawlEntryRepository.getAllResources(sizeId, colorId, designId);
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


            Cell designHeader = startRow.createCell(0);
            designHeader.setCellValue("Design");
            designHeader.setCellStyle(style);


            Cell colorHeaderCell = startRow.createCell(1);
            colorHeaderCell.setCellValue("Color");
            colorHeaderCell.setCellStyle(style);


            Cell sizeHeaderCell = startRow.createCell(2);
            sizeHeaderCell.setCellValue("Size");
            sizeHeaderCell.setCellStyle(style);


            Cell quantityHeaderCell = startRow.createCell(3);
            quantityHeaderCell.setCellValue("Quantity");
            quantityHeaderCell.setCellStyle(style);


            for (ShawlInventoryResource shawlEntryResource : entryResources) {
                rownum++;


                Row row = sheet.createRow(rownum);
                Cell designCell = row.createCell(0);
                designCell.setCellValue(shawlEntryResource.getDesign().getName());


                Cell colorCell = row.createCell(1);
                colorCell.setCellValue(shawlEntryResource.getColor().getName());

                Cell sizeCell = row.createCell(2);
                sizeCell.setCellValue(shawlEntryResource.getSize().getName());

                Cell quantityCell = row.createCell(3);
                quantityCell.setCellValue(shawlEntryResource.getCount());

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


        for (int i = 0; i <= 3; i++) {
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