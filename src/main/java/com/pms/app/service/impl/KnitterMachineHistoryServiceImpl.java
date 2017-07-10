package com.pms.app.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.pms.app.domain.ClothActivity;
import com.pms.app.domain.Clothes;
import com.pms.app.domain.Knitter;
import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.domain.LocationEnum;
import com.pms.app.domain.Locations;
import com.pms.app.domain.Machine;
import com.pms.app.domain.Status;
import com.pms.app.repo.ClothActivityRepository;
import com.pms.app.repo.ClothRepository;
import com.pms.app.repo.KnitterMachineHistoryRepository;
import com.pms.app.repo.KnitterRepository;
import com.pms.app.repo.LocationRepository;
import com.pms.app.repo.MachineRepository;
import com.pms.app.schema.KnitterHistoryReportResource;
import com.pms.app.schema.KnitterMachineHistoryDto;
import com.pms.app.security.AuthUtil;
import com.pms.app.service.KnitterMachineHistoryService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by arjun on 6/21/2017.
 */

@Service
public class KnitterMachineHistoryServiceImpl implements KnitterMachineHistoryService {
    private final KnitterMachineHistoryRepository knitterMachineHistoryRepository;
    private final KnitterRepository knitterRepository;
    private final ClothRepository clothRepository;
    private final MachineRepository machineRepository;
    private final LocationRepository locationRepository;
    private final ClothActivityRepository clothActivityRepository;

    @Autowired
    public KnitterMachineHistoryServiceImpl(KnitterMachineHistoryRepository knitterMachineHistoryRepository, KnitterRepository knitterRepository, ClothRepository clothRepository, MachineRepository machineRepository, LocationRepository locationRepository, ClothActivityRepository clothActivityRepository) {
        this.knitterMachineHistoryRepository = knitterMachineHistoryRepository;
        this.knitterRepository = knitterRepository;
        this.clothRepository = clothRepository;
        this.machineRepository = machineRepository;
        this.locationRepository = locationRepository;
        this.clothActivityRepository = clothActivityRepository;
    }

    @Override
    public Page<KnitterMachineHistory> getAll(Long knitterId, Long machineId, Date completedDate, Date dateFrom, Date dateTo, Pageable pageable) {
        return knitterMachineHistoryRepository.getAll(knitterId, machineId, completedDate, dateFrom, dateTo, pageable);
    }

    @Override
    @Transactional
    public void add(KnitterMachineHistoryDto knitterMachineHistoryDto) {
        if (knitterMachineHistoryDto.getKnitterId() == null) {
            throw new RuntimeException("No knitter available");
        }
        Knitter knitter = knitterRepository.findOne(knitterMachineHistoryDto.getKnitterId());
        if (knitter == null) {
            throw new RuntimeException("No such knitter available");
        }
        if (knitterMachineHistoryDto.getMachineId() == null) {
            throw new RuntimeException("No Machine available");
        }
        Machine machine = machineRepository.findOne(knitterMachineHistoryDto.getMachineId());
        if (machine == null) {
            throw new RuntimeException("No such machine available");
        }
        if (knitterMachineHistoryDto.getClothId() == null) {
            throw new RuntimeException("No Cloth available");
        }
        Clothes providedCloth = clothRepository.findOne(knitterMachineHistoryDto.getClothId());
        if (providedCloth == null) {
            throw new RuntimeException("No such cloth available");
        }

        Locations preKnitting = locationRepository.findByName(LocationEnum.PRE_KNITTING.getName());

        List<Clothes> clothes = clothRepository.findByOrderNoAndCustomerAndPriceAndColorAndTypeAndStatusAndLocation(providedCloth.getOrder_no(),
                providedCloth.getCustomer().getId(),
                providedCloth.getPrice().getId(),
                providedCloth.getColor().getId(),
                providedCloth.getType(),
                Status.ACTIVE.toString(),
                preKnitting.getId(),
                knitterMachineHistoryDto.getQuantity());

        if (!Objects.equals(clothes.size(), knitterMachineHistoryDto.getQuantity())) {
            throw new RuntimeException("Total available cloth for given values in pre knitting is " + clothes.size());
        }

        Locations locations = locationRepository.findByName(LocationEnum.PRE_KNITTING_COMPLETED.getName());

        clothes.forEach(
                cloth -> {
                    if (cloth.getLocation().getName().equalsIgnoreCase(LocationEnum.PRE_KNITTING.getName())) {
                        cloth.setLocation(locations);
                        cloth = clothRepository.save(cloth);
                    }
                    KnitterMachineHistory knitterMachineHistory = new KnitterMachineHistory();
                    knitterMachineHistory.setCloth(cloth);
                    knitterMachineHistory.setKnitter(knitter);
                    knitterMachineHistory.setMachine(machine);

                    ClothActivity activity = new ClothActivity();
                    activity.setCloth(cloth);
                    activity.setLocation(locations);
                    activity.setUser(AuthUtil.getCurrentUser());

                    clothActivityRepository.save(activity);
                    knitterMachineHistoryRepository.save(knitterMachineHistory);

                }
        );

    }

    @Override
    public void delete(Long id) {
        KnitterMachineHistory knitterMachineHistory = knitterMachineHistoryRepository.findOne(id);
        if (knitterMachineHistory == null) {
            throw new RuntimeException("No history available");
        }
        if (Objects.equals(knitterMachineHistory.getCloth().getLocation().getName(), LocationEnum.PRE_KNITTING_COMPLETED.getName())) {
            Clothes clothes = knitterMachineHistory.getCloth();
            clothes.setLocation(locationRepository.findByName(LocationEnum.PRE_KNITTING.getName()));
            clothes = clothRepository.save(clothes);
            ClothActivity activity = new ClothActivity();
            activity.setCloth(clothes);
            activity.setLocation(clothes.getLocation());
            activity.setUser(AuthUtil.getCurrentUser());
            clothActivityRepository.save(activity);
        }
        knitterMachineHistory.setDeleted(true);
        knitterMachineHistoryRepository.save(knitterMachineHistory);
    }

    @Override
    public void edit(Long id, KnitterMachineHistoryDto knitterMachineHistoryDto) {

        if (knitterMachineHistoryDto.getKnitterId() == null) {
            throw new RuntimeException("No knitter available");
        }
        Knitter knitter = knitterRepository.findOne(knitterMachineHistoryDto.getKnitterId());
        if (knitter == null) {
            throw new RuntimeException("No such knitter available");
        }
        if (knitterMachineHistoryDto.getMachineId() == null) {
            throw new RuntimeException("No Machine available");
        }
        Machine machine = machineRepository.findOne(knitterMachineHistoryDto.getMachineId());
        if (machine == null) {
            throw new RuntimeException("No such machine available");
        }
        KnitterMachineHistory knitterMachineHistory = knitterMachineHistoryRepository.findOne(id);
        if (knitterMachineHistory == null) {
            throw new RuntimeException("No history available");
        }
        knitterMachineHistory.setMachine(machine);
        knitterMachineHistory.setKnitter(knitter);
        knitterMachineHistoryRepository.save(knitterMachineHistory);
    }

    @Override
    public KnitterMachineHistoryDto get(Long id) {
        return knitterMachineHistoryRepository.getById(id);
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

            Cell colorHeadCell = startRow.createCell(8);
            colorHeadCell.setCellValue("Color");
            colorHeadCell.setCellStyle(style);

            Cell sizeHeadCell = startRow.createCell(9);
            sizeHeadCell.setCellValue("Size");
            sizeHeadCell.setCellStyle(style);
            Cell gaugeCell = startRow.createCell(10);
            gaugeCell.setCellValue("Gauge");
            gaugeCell.setCellStyle(style);
            Cell settingHead = startRow.createCell(11);
            settingHead.setCellValue("Setting");
            settingHead.setCellStyle(style);
            Cell reOrderCell = startRow.createCell(12);
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
                yarnCellVal.setCellValue(historyReportResource.getYarnName() == null ? "N/A" : historyReportResource.getYarnName());

                Cell colorCell = row.createCell(8);
                colorCell.setCellValue(historyReportResource.getColorCode());

                Cell sizeCell = row.createCell(9);
                sizeCell.setCellValue(historyReportResource.getSizeName());

                Cell gaugeCellVal = row.createCell(10);
                gaugeCellVal.setCellValue(historyReportResource.getGauge() == null ? "N/A" : historyReportResource.getGauge() + "");

                Cell settingVal = row.createCell(11);
                settingVal.setCellValue(historyReportResource.getSetting() == null ? "N/A" : historyReportResource.getSetting());

                Cell reOrderCellVal = row.createCell(12);
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


        for (int i = 0; i <= 12; i++) {
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
