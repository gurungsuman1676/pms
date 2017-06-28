package com.pms.app.upload;

import com.pms.app.domain.Clothes;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by arjun on 6/14/2017.
 */

@Component
@Scope("prototype")
@Transactional
public class PhillipeTemplate extends AbstractTemplate implements TemplateService {
    private static final String CUSTOMER_NAME_ALIAS = "CUSTOMER NAME";
    private static final String DELIVERY_DATE_ALIAS = "DELIVERY DATE";
    private static final String ORDER_NO_ALIAS = "ORDER NO";
    private static final String DESIGN_ALIAS = "DESIGN";
    private static final String NAME_ON_BAG_ALIAS = "NAME ON THE BAG";
    private static final String COLOR_ALIAS = "COLOR CODE";
    private static final String COLOR_NAME_ALIAS = "COLOR NAME";
    private static final String SIZE_ALIAS = "SIZE";
    private static final String YARN_ALIAS = "YARN";
    private static final String BARCODE_ALIAS = "BARCODE";
    private static final String QUANTITY_ALIAS = "QTY";

    boolean completed = false;

    public PhillipeTemplate(MultipartFile file) throws IOException {
        super(file, 0);
    }

    @Override
    public void process() throws IOException {
        setExtractFormula(nameExtractFormula);
        getCustomerName(CUSTOMER_NAME_ALIAS);
        getDeliveryDate(DELIVERY_DATE_ALIAS);
        getOrderNo(ORDER_NO_ALIAS);
        addClothes();

    }

    private void addClothes() {
        int designIndex = -1;
        while (designIndex == -1 && rowsIterator.hasNext()) {
            currentRow = rowsIterator.next();
            designIndex = getIndexForAlias(DESIGN_ALIAS, false);
        }
        if (designIndex == -1) {
            throw new RuntimeException("Design Name Header not available");
        }
        int bagNameIndex = getIndexForAlias(NAME_ON_BAG_ALIAS, false);
        int colorIndex = getIndexForAlias(COLOR_ALIAS, true);
        int colorNameIndex = getIndexForAlias(COLOR_NAME_ALIAS, true);
        int sizeIndex = getIndexForAlias(SIZE_ALIAS, true);
        int yarnIndex = getIndexForAlias(YARN_ALIAS, true);
        int barcodeIndex = getIndexForAlias(BARCODE_ALIAS, false);
        int quantityIndex = getIndexForAlias(QUANTITY_ALIAS, true);
        List<Clothes> clothes = new ArrayList<>();
        while (rowsIterator.hasNext() && !completed) {
            currentRow = rowsIterator.next();
            clothes.addAll(getClothForRow(designIndex, bagNameIndex, colorIndex, sizeIndex, yarnIndex, barcodeIndex, quantityIndex, colorNameIndex));
        }
        try {
            if (Integer.valueOf(getCellValueByIndex(quantityIndex, QUANTITY_ALIAS, true)) != clothes.size()) {
                throw new RuntimeException("Total " + clothes.size() + " does not match with given total " + Integer.valueOf(getCellValueByIndex(quantityIndex, QUANTITY_ALIAS, true)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Total is not available ");
        }
        clothRepository.save(clothes);

    }


    private List<Clothes> getClothForRow(int designIndex, int bagNameIndex, int colorIndex, int sizeIndex, int yarnIndex, int barcodeIndex, int quantityIndex, int colorNameIndex) {
        String quantity = getCellValueByIndex(quantityIndex, QUANTITY_ALIAS, false);
        String designName = getCellValueByIndex(designIndex, DESIGN_ALIAS, false);

        if (designName.length() == 0) {
            if (quantity.length() > 0) {
                completed = true;
                return new ArrayList<>();
            } else {
                return new ArrayList<>();
            }
        }

        String bagName = getCellValueByIndex(bagNameIndex, NAME_ON_BAG_ALIAS, false);
        String colorCode = getCellValueByIndex(colorIndex, COLOR_ALIAS, true);
        String size = getCellValueByIndex(sizeIndex, SIZE_ALIAS, true);
        String yarn = getCellValueByIndex(yarnIndex, YARN_ALIAS, true);
        String barcode = getCellValueByIndex(barcodeIndex, BARCODE_ALIAS, false);
        String colorName = getCellValueByIndex(colorNameIndex, COLOR_NAME_ALIAS, true);

        Map<String, Integer> sizes = new HashMap<>();
        sizes.put(size, Integer.parseInt(quantity));
        return getCloth(colorCode, colorName, yarn, sizes, designName, null, bagName.isEmpty() ? "" : "Name ::" + bagName, barcode.isEmpty() ? "" : "Barcode::" + barcode);
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

    private int getIndexForAlias(String alias, boolean required) {
        for (Cell cell : currentRow) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue().toUpperCase().contains(alias)) {
                return cell.getColumnIndex();
            }
        }
        if (required) {
            throw new RuntimeException("Header " + alias + " not found");
        }
        return -1;
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

}
