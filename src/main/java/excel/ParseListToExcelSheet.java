package excel;

import mapping.FieldMappingExcelHeaderCell;
import org.apache.poi.ss.usermodel.*;
import reflect.ReflectUtil;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author:      shfq
 * description:
 * create date: 2016/6/13.
 */
public class ParseListToExcelSheet<T> {
    private final List<T> objectList;
    private final FieldMappingExcelHeaderCell[] mappings;
    private final Workbook workbook;
    private final OutputStream outputStream;

    private ParseListToExcelSheet(List<T> objectList, FieldMappingExcelHeaderCell[] mappings, Workbook workbook, OutputStream outputStream) throws Exception {
        this.objectList = objectList;
        this.mappings = mappings;
        this.workbook = workbook;
        this.outputStream = outputStream;
    }

    public void write() throws Exception {
        try {
            Sheet sheet = workbook.createSheet();
            setExcelTableHeader(sheet.createRow(0));

            if (objectList != null && objectList.size() != 0) {
                int rowId = 1;
                for (T object : objectList) {
                    if (object != null) {
                        Row row = sheet.createRow(rowId);
                        int cellId = 0;
                        for (FieldMappingExcelHeaderCell mapping : mappings) {
                            String fieldName = mapping.getFieldName();

                            Cell cell = row.createCell(cellId++);
                            if (fieldName != null) {
                                Object o = ReflectUtil.callMethod(object, ReflectUtil.getGetMethod(object.getClass(), fieldName));
                                setCellValue(cell, o);
                            }
                        }
                    }
                    rowId++;
                }
            }
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }

        }
    }

    private void setCellValue(Cell cell, Object o) {
        if (o instanceof Date) {
            cell.setCellValue((Date) o);
        } else if (o instanceof Calendar) {
            cell.setCellValue((Calendar) o);
        } else if (o instanceof Boolean) {
            cell.setCellValue((Boolean) o);
        } else if (o instanceof Number) {
            if (o instanceof Short || o instanceof Integer || o instanceof Long) {
                CellStyle style = workbook.createCellStyle();
                DataFormat dataFormat = workbook.createDataFormat();
                style.setDataFormat(dataFormat.getFormat("0"));
                cell.setCellStyle(style);
                cell.setCellValue((Double.parseDouble(o.toString())));
                return;
            } else if (o instanceof Float || o instanceof Double) {
                cell.setCellValue(Double.parseDouble(o.toString()));
                return;
            } else {
                cell.setCellValue(o.toString());
                return;
            }
        } else {
            cell.setCellValue(o.toString());
        }

    }

    private void setExcelTableHeader(Row row) {
        int cellId = 0;
        for (FieldMappingExcelHeaderCell mapping : mappings) {
            Cell cell = row.createCell(cellId++);
            cell.setCellValue(mapping.getHeaderCellValue());
        }
    }

    public static class Builder<T> {
        private List<T> objectList;
        private FieldMappingExcelHeaderCell[] mappings;
        private OutputStream outputStream;
        private ExcelType excelType;

        public List<T> getObjectList() {
            return objectList;
        }

        public void setObjectList(List<T> objectList) {
            this.objectList = objectList;
        }

        public FieldMappingExcelHeaderCell[] getMappings() {
            return mappings;
        }

        public void setMappings(FieldMappingExcelHeaderCell[] mappings) {
            this.mappings = mappings;
        }

        public OutputStream getOutputStream() {
            return outputStream;
        }

        public void setOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        public ExcelType getExcelType() {
            return excelType;
        }

        public void setExcelType(ExcelType excelType) {
            this.excelType = excelType;
        }

        public ParseListToExcelSheet build() throws Exception {
            if (mappings == null || mappings.length == 0) {
                throw new IllegalArgumentException("字段名称映射 excel 表头单元格值数组不能为空");
            }
            if (outputStream == null) {
                throw new IllegalArgumentException("输出流不能为空");
            }
            Workbook workbook = WorkbookFactory.create(excelType);
            return new ParseListToExcelSheet(objectList, mappings, workbook, outputStream);
        }
    }


}
