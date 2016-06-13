package mapping;

/**
 * author:      shfq
 * description:
 * create date: 2016/6/13.
 */
public class FieldMappingExcelHeaderCell {
    private String fieldName;
    private String headerCellValue;

    public FieldMappingExcelHeaderCell(String fieldName, String headerCellValue) {
        this.fieldName = fieldName;
        this.headerCellValue = headerCellValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getHeaderCellValue() {
        return headerCellValue;
    }

    public void setHeaderCellValue(String headerCellValue) {
        this.headerCellValue = headerCellValue;
    }
}
