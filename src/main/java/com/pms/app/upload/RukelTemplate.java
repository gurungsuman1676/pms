package com.pms.app.upload;

import com.pms.app.domain.Clothes;
import com.pms.app.domain.Colors;
import com.pms.app.domain.Customers;
import com.pms.app.domain.Designs;
import com.pms.app.domain.Prices;
import com.pms.app.domain.Sizes;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by arjun on 6/13/2017.
 */

@Component
@Scope("prototype")
public class RukelTemplate extends AbstractTemplate implements TemplateService {
    private static final String CUSTOMER_NAME_ALIAS = "ORDER";
    private static final String DELIVERY_DATE = "DELIVERY DATE";
    private static final String ORDER_NO = "ORDER";
    private static final String DESIGN_ALIAS = "PRODUCT";
    private static final String SIZE_ALIAS = "SIZE";
    private static final String PRINT_ALIAS = "PRINT";
    private static final String QUANTITY_ALIAS = "QTY";
    private static final String TOTAL_ALIAS = "TOTAL";
    private static final String FRINGE_ALIAS = "FRINGE";
    private static final String LABEL_ALIAS = "LABEL";
    private static final String BASE_ALIAS = "BASE";

    private boolean completed = false;

    public RukelTemplate(MultipartFile file, int type, String orderType) throws IOException {
        super(file, type, orderType);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "designs", allEntries = true),
            @CacheEvict(value = "colors", allEntries = true),
            @CacheEvict(value = "yarns", allEntries = true),
            @CacheEvict(value = "sizes", allEntries = true),
            @CacheEvict(value = "prints", allEntries = true),
            @CacheEvict(value = "prices", allEntries = true)
    })
    public void process() throws IOException {
        getCustomerName(CUSTOMER_NAME_ALIAS);
        getDeliveryDate(DELIVERY_DATE);
        getOrderNo(ORDER_NO);
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
        int sizeIndex = getIndexForAlias(SIZE_ALIAS, true);
        int printIndex = getIndexForAlias(PRINT_ALIAS, clothType == 1);
        int baseIndex = getIndexForAlias(BASE_ALIAS, true);
        Map<String, Integer> printFinalIndex = getFinalIndexForPrint(baseIndex);
        int quantityIndex = getIndexForAlias(QUANTITY_ALIAS, true);

        Map<String, List<Clothes>> clothesMap = new HashMap<>();
        while (rowsIterator.hasNext() && !completed) {
            currentRow = rowsIterator.next();
            clothesMap.putAll(getClothListMap(designIndex, sizeIndex, printIndex, printFinalIndex, quantityIndex, baseIndex));
        }
        validateTotal(clothesMap.values().stream().mapToInt(List::size).sum(), quantityIndex);
        clothesMap.keySet().forEach(k -> {
            Long customeIdByName = customerRepository.findCustomeIdByName(k);

            clothesMap.get(k).forEach(c -> {
                if (customeIdByName != null) {
                    c.setCustomer(entityManager.getReference(Customers.class, customeIdByName));
                }
                clothRepository.save(c);
            });
        });

    }

    private Map<String, List<Clothes>> getClothListMap(int designIndex, int sizeIndex, int printIndex, Map<String, Integer> printFinalIndex, int quantityIndex, int baseIndex) {
        if (hasTotal()) {
            completed = true;
            return new HashMap<>();
        }
        Map<String, List<Clothes>> clothMap = new HashMap<>();
        String headerValue = getHeaderValue(designIndex);
        currentRow = rowsIterator.next();
        clothMap.put(headerValue, new ArrayList<>());
        System.out.println(headerValue);
        try {
            List<Clothes> clothForRow = getClothForRow(designIndex, sizeIndex, printIndex, printFinalIndex, quantityIndex, baseIndex);
            clothMap.get(headerValue).addAll(clothForRow);
            return clothMap;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + " at header " + headerValue);
        }

    }

    public List<Clothes> getClothForRow(int designIndex, int sizeIndex, int printIndex, Map<String, Integer> printFinalIndex, int quantityIndex, int baseIndex) {
        List<ClothMini> clothMinis = new ArrayList<>();
        while (true) {
            ClothMini clothes = getClothes(designIndex, sizeIndex, printIndex, printFinalIndex, quantityIndex, baseIndex);
            if (clothes == null) {
                break;
            }
            clothMinis.add(clothes);
        }
        validateTotal(
                clothMinis.stream().mapToInt(cm -> cm.getClothes().size()).sum(), quantityIndex);
        clothMinis.forEach(cm -> cm.getClothes().forEach(c -> {
            c.setFringe(cm.getFringe());
        }));
        List<Clothes> clothes = new ArrayList<>();
        clothMinis.forEach(cm -> {
            clothes.addAll(cm.getClothes());
        });
        return clothes;
    }

    private ClothMini getClothes(int designIndex, int sizeIndex, int printIndex, Map<String, Integer> printFinalIndex, int quantityIndex, int baseIndex) {
        if (hasTotal()) {
            return null;
        }
        ClothMini clothMini = new ClothMini();
        while (!clothMini.currentBlockCompleted && !hasTotal()) {
            String designName = getCellValueByIndex(designIndex, DESIGN_ALIAS, false);
            if (designName.isEmpty() && clothMini.getDesignName() == null) {
                currentRow = rowsIterator.next();
                continue;
            }
            if (hasTotal()) {
                clothMini.setCurrentBlockCompleted(true);
                continue;
            }
            if (!designName.isEmpty()) {
                if (!clothMini.isDesignColumnCompleted()) {
                    if (clothMini.getDesignName() == null) {
                        clothMini.setDesignName(designName);
                        Row yarnRowSheet = sheet.getRow(currentRow.getRowNum() + 1);
                        if (yarnRowSheet == null || yarnRowSheet.getCell(designIndex) == null) {
                            throw new RuntimeException("Invalid Yarn Name");
                        }

                        Cell yarnCell = yarnRowSheet.getCell(designIndex);
                        yarnCell.setCellType(Cell.CELL_TYPE_STRING);
                        if (yarnCell.getStringCellValue() == null || yarnCell.getStringCellValue().isEmpty() || yarnCell.getStringCellValue().toUpperCase().contains(FRINGE_ALIAS)) {
                            throw new RuntimeException("Invalid Yarn Name");
                        }
                        clothMini.setYarnName(yarnCell.getStringCellValue());
                    } else if (designName.toUpperCase().contains(FRINGE_ALIAS)) {
                        clothMini.setFringe(designName);
                    }
                } else {
                    clothMini.setCurrentBlockCompleted(true);
                    continue;

//                    throw new RuntimeException("Invalid Data.Cannot find total for design name " + clothMini.getDesignName());
                }
            } else {
                clothMini.setDesignColumnCompleted(true);
            }



            String colorName = getCellValueByIndex(baseIndex, BASE_ALIAS, false);
            if (colorName.isEmpty()) {
                currentRow = rowsIterator.next();
                continue;
            }
            String printName = getCellValueByIndex(printIndex, PRINT_ALIAS, clothType == 1);

            if (clothMini.getSize() == null) {
                String sizeName = getCellValueByIndex(sizeIndex, SIZE_ALIAS, true);
                clothMini.setSize(sizeName);
            }
            String extraField = getCellValuesForAdditionalPrint(printFinalIndex);
            String quantity = getCellValueByIndex(quantityIndex, QUANTITY_ALIAS, true);
            if (colorName.isEmpty()) {
                currentRow = rowsIterator.next();
                continue;
            }
            Map<String, Integer> sizes = new HashMap<>();
            sizes.put(clothMini.getSize(), Integer.parseInt(quantity));
            clothMini.getClothes().addAll(getCloth(colorName, colorName, clothMini.getYarnName(), sizes, clothMini.getDesignName(), printName, extraField));
            currentRow = rowsIterator.next();
        }
        return clothMini;
    }

    private void validateTotal(int size, int qtyIndex) {
        Cell cell = currentRow.getCell(qtyIndex);
        if (cell == null) {
            throw new RuntimeException("Error in total value " + currentRow.getRowNum());
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        int total;
        try {
            total = Integer.valueOf(cell.getStringCellValue());
        } catch (Exception e) {
            throw new RuntimeException("Invalid total value at " + currentRow.getRowNum());
        }
        if (total != size) {
            throw new RuntimeException(" Total number  of clothes (" + size + ") is not equal to Given  total" + " (" + total + ") at row " + currentRow.getRowNum());
        }

    }

    private String getHeaderValue(int designIndex) {
        while (rowsIterator.hasNext()) {
            currentRow = rowsIterator.next();
            Cell cell = currentRow.getCell(designIndex);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (!cell.getStringCellValue().isEmpty()) {
                    return cell.getStringCellValue();
                }
            }
        }
        throw new RuntimeException("No header value available ");
    }

    private boolean hasTotal() {
        Iterator<Cell> cellIterator = currentRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getStringCellValue().toUpperCase().trim().equals(TOTAL_ALIAS)) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Integer> getFinalIndexForPrint(int printIndex) {
        Map<String, Integer> printMap = new LinkedHashMap<>();
        for (Cell cell : currentRow) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getColumnIndex() > printIndex) {
                if (cell.getStringCellValue().toUpperCase().contains(DELIVERY_DATE) || cell.getStringCellValue().toUpperCase().contains(LABEL_ALIAS) || cell.getStringCellValue().toUpperCase().contains(QUANTITY_ALIAS)) {
                    return printMap;
                }
                printMap.put(cell.getStringCellValue(), cell.getColumnIndex());
            }
        }

        throw new RuntimeException("Can not determine print headers");
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


    private String getCellValuesForAdditionalPrint(Map<String, Integer> printFinalIndex) {
        return printFinalIndex
                .keySet()
                .stream()
                .filter(k -> !getCellValueByIndex(printFinalIndex.get(k), k, false).isEmpty())
                .map((k) -> k + " : " + getCellValueByIndex(printFinalIndex.get(k), k, true))
                .collect(Collectors.joining(" ,"));
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


    @Data
    class ClothMini {
        String designName;
        String fringe;
        String yarnName;
        boolean designColumnCompleted = false;
        boolean currentBlockCompleted = false;
        String colorCode;
        String size;
        List<Clothes> clothes = new ArrayList<>();
    }
}
