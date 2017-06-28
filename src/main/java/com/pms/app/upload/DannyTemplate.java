package com.pms.app.upload;

import com.pms.app.domain.Clothes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by arjun on 6/13/2017.
 */

@Component
@Scope("prototype")
public class DannyTemplate extends AbstractTemplate implements TemplateService {

    private static final String CUSTOMER_NAME_ALIAS = "CUSTOMER NAME";
    private static final String DELIVERY_DATE_ALIAS = "DELIVERY DATE";
    private static final String ORDER_NO_ALIAS = "ORDER NO";
    private static final String COLOR_NAME_ALIAS = "COLOR NAME";
    private static final String COLOR_CODE_ALIAS = "COLOR CODE";
    private static final String YARN_ALIAS = "YARN";
    private static final String TOTAL_ALIAS = "TOTAL";
    private static final String GRAND_TOTAL_ALIAS = "GRAND TOTAL";

    private boolean completed = false;


    public DannyTemplate(MultipartFile file) throws IOException {
        super(file, 0);
    }


    @Override
    public void process() throws Exception {
        setExtractFormula(nameExtractFormula);
        getCustomerName(CUSTOMER_NAME_ALIAS);
        getDeliveryDate(DELIVERY_DATE_ALIAS);
        getOrderNo(ORDER_NO_ALIAS);
        addClothes();
    }


    BiFunction<Row, String, String> nameExtractFormula =
            (Row row, String alias) -> {
                String name = null;
                Iterator<Cell> cells = row.cellIterator();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains(alias)) {
                        Cell next = cells.next();
                        next.setCellType(Cell.CELL_TYPE_STRING);
                        name = next.getStringCellValue();
                        break;
                    }
                }
                return name;
            };


    private void addClothes() throws Exception {

        int clothInitNumber = 1;
        Map<Integer, List<Clothes>> clothesMap = new HashMap<>();
        while (true) {
            List<Clothes> clothesForNumber = getClothesForNumber(clothInitNumber);
            if (!completed) {
                clothesMap.put(clothInitNumber, clothesForNumber);
                clothInitNumber++;
            } else {
                validateTotal(clothesMap.values().stream().mapToInt(List::size).sum(), GRAND_TOTAL_ALIAS);
                break;
            }
        }
        for (Integer stepValue : clothesMap.keySet()) {
            clothRepository.save(clothesMap.get(stepValue));
        }
    }

    private List<Clothes> getClothesForNumber(int clothInitNumber) throws Exception {
        String designName = getDesignName(clothInitNumber);
        if (completed) {
            return null;
        }
        currentRow = rowsIterator.next();
        int colorNameIndex = getColorNameIndex();
        int colorCodeIndex = getColorCodeIndex();
        int yarnIndex = getColorYarnIndex();
        Map<Integer, String> sizeIndexes = getSizeIndex(yarnIndex);
        List<Clothes> clothesMap = new ArrayList<>();
        currentRow = rowsIterator.next();
        while (!hasTotal(TOTAL_ALIAS)) {
            clothesMap.addAll(getClothByColorIndexAndSizeIndexAndSizeIndex(colorCodeIndex, sizeIndexes, yarnIndex, colorNameIndex, designName));
            try {
                currentRow = rowsIterator.next();
            } catch (Exception e) {
                throw new RuntimeException("Total not available for " + clothInitNumber);
            }
        }

        validateTotal(clothesMap.size(), TOTAL_ALIAS);
        return clothesMap;
    }

    private void validateTotal(int size, String alias) {
        Iterator<Cell> iterator = currentRow.iterator();
        int total = 0;
        while (iterator.hasNext()) {
            Cell next = iterator.next();
            if (next != null) {
                try {
                    int t = (int) next.getNumericCellValue();
                    if (t != 0) {
                        total = t;
                    }
                } catch (Exception ignored) {

                }
            }
        }
        if (total != size) {
            throw new RuntimeException(alias + "number  of clothes (" + size + ") is not equal to Given " + alias + " (" + total + ") at row" + currentRow.getRowNum());
        }
    }

    private List<Clothes> getClothByColorIndexAndSizeIndexAndSizeIndex(int colorCodeIndex, Map<Integer, String> sizeIndexes, int yarnIndex, int colorNameIndex, String designName) throws Exception {


        Map<String, Integer> sizeAndNumberMap = new HashMap<>();
        for (Integer sizeEntryColum : sizeIndexes.keySet()) {
            Cell sizeCell = currentRow.getCell(sizeEntryColum);
            if (sizeCell != null) {
                sizeCell.setCellType(Cell.CELL_TYPE_STRING);
                if (sizeCell.getStringCellValue() != null && !sizeCell.getStringCellValue().isEmpty()) {
                    int sizeValue;
                    try {
                        sizeValue = Integer.parseInt(sizeCell.getStringCellValue());
                    } catch (Exception e) {
                        sizeValue = 0;
                    }
                    sizeAndNumberMap.put(sizeIndexes.get(sizeEntryColum), sizeValue);

                }
            }
        }
        if (sizeAndNumberMap.size() == 0) {
            return new ArrayList<>();
        }
        String colorCode = getCellValueByIndex(colorCodeIndex, COLOR_CODE_ALIAS);
        String yarnName = getCellValueByIndex(yarnIndex, YARN_ALIAS);
        String colorName = getCellValueByIndex(colorNameIndex, COLOR_NAME_ALIAS);
        return getCloth(colorCode, colorName, yarnName, sizeAndNumberMap, designName, null);
    }


    private String getCellValueByIndex(int index, String alias) {
        if (index == -1) {
            throw new RuntimeException("Invalid value for " + alias + " at row " + currentRow.getRowNum());
        }
        Cell cell = currentRow.getCell(index);
        if (cell == null) {
            throw new RuntimeException("Invalid value for " + alias + " at row " + currentRow.getRowNum());
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        String stringCellValue = cell.getStringCellValue();
        if (stringCellValue.isEmpty()) {
            throw new RuntimeException("Invalid value for " + alias + " at row " + currentRow.getRowNum());

        }
        return stringCellValue;

    }

    private String getDesignName(int clothInitNumber) {
        while (rowsIterator.hasNext()) {
            currentRow = rowsIterator.next();
            if (!hasTotal(GRAND_TOTAL_ALIAS)) {
                Cell cell = currentRow.getCell(0);
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (cell.getStringCellValue() != null && cell.getStringCellValue().equals(clothInitNumber + "")) {
                        Cell designCell = currentRow.getCell(1);
                        if (designCell == null) {
                            throw new RuntimeException("Error finding design at " + clothInitNumber);
                        }
                        designCell.setCellType(Cell.CELL_TYPE_STRING);
                        if (designCell.getStringCellValue().trim().isEmpty()) {
                            throw new RuntimeException("Error design name at " + clothInitNumber);
                        }
                        return designCell.getStringCellValue();
                    }
                }
            } else {
                completed = true;
                return null;
            }
        }
        throw new RuntimeException("Design for serial number " + clothInitNumber + " not available");
    }

    private boolean hasTotal(String alias) {
        return currentRow.getCell(1) != null && currentRow.getCell(1).getStringCellValue() != null
                && currentRow.getCell(1).getStringCellValue().toUpperCase().contains(alias);
    }

    private int getColorNameIndex() {
        for (Cell cell : currentRow) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains(COLOR_NAME_ALIAS)) {
                return cell.getColumnIndex();
            }
        }
        throw new RuntimeException("Color Name header not found  in  " + currentRow.getRowNum());
    }

    private int getColorCodeIndex() {
        for (Cell cell : currentRow) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains(COLOR_CODE_ALIAS)) {
                return cell.getColumnIndex();
            }
        }
        throw new RuntimeException("Color Code header not found  in " + currentRow.getRowNum());
    }

    private int getColorYarnIndex() {
        for (Cell cell : currentRow) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue() != null && cell.getStringCellValue().toUpperCase().contains(YARN_ALIAS)) {
                return cell.getColumnIndex();
            }
        }
        throw new RuntimeException("Yarn header not found  in " + currentRow.getRowNum());
    }

    private Map<Integer, String> getSizeIndex(int yarnIndex) {
        Map<Integer, String> sizesIndex = new HashMap<>();
        for (Cell cell : currentRow) {
            if (cell.getColumnIndex() > yarnIndex) {
                cell.setCellType(Cell.CELL_TYPE_STRING);

                if (cell.getStringCellValue().equalsIgnoreCase(TOTAL_ALIAS)) {
                    return sizesIndex;
                }
                if (!cell.getStringCellValue().trim().isEmpty()) {
                    sizesIndex.put(cell.getColumnIndex(), cell.getStringCellValue());
                }
            }
        }
        throw new RuntimeException("Total header not available cloth  for row " + currentRow.getRowNum());
    }


}


