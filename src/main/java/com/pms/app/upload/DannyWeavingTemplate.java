package com.pms.app.upload;

import com.pms.app.domain.Clothes;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by arjun on 7/13/2017.
 */


@Component
@Scope("prototype")
public class DannyWeavingTemplate extends AbstractTemplate implements TemplateService {

    private static final String CUSTOMER_NAME_ALIAS = "CUSTOMER NAME";
    private static final String DELIVERY_DATE_ALIAS = "DELIVERY DATE";
    private static final String ORDER_NO_ALIAS = "ORDER NO";
    private static final String TOTAL_ALIAS = "TOTAL";
    private static final String GRAND_TOTAL_ALIAS = "GRAND TOTAL";


    public DannyWeavingTemplate(MultipartFile file, int type, String orderType) throws IOException {
        super(file, type, orderType);
    }

    private boolean completed = false;


    @Override
    @Caching(evict = {
            @CacheEvict(value = "designs", allEntries = true),
            @CacheEvict(value = "colors", allEntries = true),
            @CacheEvict(value = "yarns", allEntries = true),
            @CacheEvict(value = "sizes", allEntries = true),
            @CacheEvict(value = "prints", allEntries = true),
            @CacheEvict(value = "prices", allEntries = true)
    })
    public void process() throws Exception {
        getCustomerName(CUSTOMER_NAME_ALIAS);
        getDeliveryDate(DELIVERY_DATE_ALIAS);
        getOrderNo(ORDER_NO_ALIAS);
        addClothes();
    }

    private void addClothes() {
        List<Clothes> totalClothes = new ArrayList<>();
        while (!hasTotal(GRAND_TOTAL_ALIAS)) {
            LinkedList<Integer> designIndexes = getDesignIndex();
            if (completed) {
                continue;
            }
            String printName = getCellValueByIndex(designIndexes.get(0) + 1, "Print", clothType == 1);
            Map<Integer, String> designNames = getDesignName(designIndexes);
            currentRow = rowsIterator.next();
            String yarnName = getCellValueByIndex(designIndexes.get(0) + 1, "Yarn", true).trim();
            Map<Integer, String> sizeNames = new HashMap<>();
            for (int i = clothType == 1 ? 2 : 1; i < designIndexes.size(); i++) {
                sizeNames.put(designIndexes.get(i), getCellValueByIndex(designIndexes.get(i), "Size", true).trim());
            }
            List<Clothes> clothes = new ArrayList<>();

            while (!hasTotal(TOTAL_ALIAS)) {
                currentRow = rowsIterator.next();
                String colorCode = getCellValueByIndex(designIndexes.get(0), "Color Code", false);
                if (colorCode.isEmpty()) {
                    continue;
                }
                String colorName = getCellValueByIndex(designIndexes.get(0) + 1, "Color Name", true);
                for (int key : sizeNames.keySet()) {
                    String size = getCellValueByIndex(key, "Size", false).trim();
                    Integer value;
                    try {
                        value = size.isEmpty() ? 0 : Integer.valueOf(size);
                    } catch (Exception e) {
                        throw new RuntimeException("Invalid size (" + size + ") at  row " + currentRow.getRowNum() + " and column " + key);
                    }
                    clothes.addAll(getCloth(colorCode, colorName, yarnName, Collections.singletonMap(sizeNames.get(key), value), designNames.get(key), printName));
                }
            }
            validateTotal(TOTAL_ALIAS, clothes.size());
            totalClothes.addAll(clothes);
        }
        validateTotal(GRAND_TOTAL_ALIAS, totalClothes.size());
        clothRepository.save(totalClothes);

    }

    private void validateTotal(String alias, int size) {
        Iterator<Cell> iterator = currentRow.iterator();
        int total = 0;
        while (iterator.hasNext()) {
            Cell next = iterator.next();
            if (next != null) {
                try {
                    next.setCellType(Cell.CELL_TYPE_STRING);
                    int t = Integer.valueOf(next.getStringCellValue());
                    System.out.println(t);
                    if (t != 0) {
                        total = t;
                    }
                } catch (Exception ignored) {

                }
            }
        }
        if (total != size) {
            throw new RuntimeException(alias + " number  of clothes (" + size + ") is not equal to Given " + alias + " (" + total + ") at row" + currentRow.getRowNum());
        }
    }


    private Map<Integer, String> getDesignName(LinkedList<Integer> designIndexes) {
        String mainDesigns = getCellValueByIndex(designIndexes.get(0), "Design", true);
        Map<Integer, String> designName = new HashMap<>();
        for (int i = clothType == 1 ? 2 : 1; i < designIndexes.size(); i++) {
            designName.put(designIndexes.get(i), mainDesigns.trim() + " " + getCellValueByIndex(designIndexes.get(i), "Design", true).trim());
        }

        return designName;
    }


    private String getCellValueByIndex(int index, String alias, boolean required) {
        if (index == -1) {
            if (required) {
                throw new RuntimeException("Invalid value for " + alias + " at row " + currentRow.getRowNum());
            } else {
                return "";
            }
        }
        Cell cell = currentRow.getCell(index);
        if (cell == null) {
            if (required) {
                throw new RuntimeException("Invalid value for " + alias + " at row " + currentRow.getRowNum());
            } else {
                return "";
            }
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        String stringCellValue = cell.getStringCellValue();
        if (stringCellValue.isEmpty()) {
            if (required) {
                throw new RuntimeException("Invalid value for " + alias + " at row " + currentRow.getRowNum());
            } else {
                return "";
            }
        }
        return stringCellValue;
    }

    private boolean hasTotal(String grandTotalAlias) {
        Iterator<Cell> cellIterator = currentRow.iterator();
        boolean hasTotal = false;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().equalsIgnoreCase(grandTotalAlias)) {
                    hasTotal = true;
                }
            }
        }
        return hasTotal;
    }

    public LinkedList<Integer> getDesignIndex() {
        while (rowsIterator.hasNext()) {
            LinkedList<Integer> indexes = new LinkedList<>();
            currentRow = rowsIterator.next();
            if (hasTotal(GRAND_TOTAL_ALIAS)) {
                completed = true;
                return new LinkedList<>();
            }
            for (Cell cell : currentRow) {
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()) {
                        indexes.add(cell.getColumnIndex());
                    }
                }
            }
            if (indexes.size() > (clothType == 1 ? 2 : 1)) {
                return indexes;
            }
        }
        throw new RuntimeException("No design headers were available");
    }

}
