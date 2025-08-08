package com.manhhoach.JavaTour.helpers.excel;


import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelHelper {

    public static <T> List<T> readExcel(MultipartFile file, Class<T> clazz, int index) throws Exception {
        List<T> result = new ArrayList<>();
        Workbook w = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = w.getSheetAt(index);
        Map<String, Integer> headerMap = getHeaderMap(sheet);
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            T obj = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ExcelColumn.class)) {
                    ExcelColumn exCol = field.getAnnotation(ExcelColumn.class);
                    String colName = exCol.name();
                    int colIndex = exCol.index();
                    int columnIndex = (colName != null && !colName.isEmpty()) ? headerMap.get(colName) : colIndex;
                    if (columnIndex == -1) {
                        continue;
                    }
                    Cell cell = row.getCell(columnIndex);
                    if (cell != null) {
                        field.setAccessible(true);
                        setFieldValue(obj, field, cell);
                    }
                }

            }
            result.add(obj);
        }
        return result;
    }

    private static void setFieldValue(Object obj, Field field, Cell cell) throws Exception {
        if (cell == null) return;
        field.setAccessible(true);
        Class<?> fieldType = field.getType();

        if (fieldType == String.class) {
            cell.setCellType(CellType.STRING);
            field.set(obj, cell.getStringCellValue());
        } else if (fieldType == int.class || fieldType == Integer.class) {
            field.set(obj, (int) cell.getNumericCellValue());
        } else if (fieldType == double.class || fieldType == Double.class) {
            field.set(obj, cell.getNumericCellValue());
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            field.set(obj, cell.getBooleanCellValue());
        } else if (fieldType == java.util.Date.class) {
            field.set(obj, cell.getDateCellValue());
        }
    }

    private static Map<String, Integer> getHeaderMap(Sheet sheet) {
        var headerMap = new HashMap<String, Integer>();
        Row headerRow = sheet.getRow(0);
        if (headerRow != null) {
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    headerMap.put(cell.getStringCellValue(), i);
                }
            }
        }
        return headerMap;
    }
}
