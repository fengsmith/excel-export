package vo;

import excel.ExcelType;
import excel.ParseListToExcelSheet;
import mapping.FieldMappingExcelHeaderCell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author:      shfq
 * description:
 * create date: 2016/6/13.
 */
public class ExportStudentsToExcelTest {
    List<Student> students;
    FileOutputStream fileOutputStream;
    ExcelType excelType;
    FieldMappingExcelHeaderCell[] mappings;

    @Before
    public void setUp() throws Exception {
        addStudents();
        setMappings();

        excelType = ExcelType.XLS;
        fileOutputStream = new FileOutputStream(new File("c:\\test.xls"));




    }

    @Test
    public void test() throws Exception {
        ParseListToExcelSheet.Builder<Student> builder = new ParseListToExcelSheet.Builder<Student>();
        builder.setExcelType(excelType);
        builder.setMappings(mappings);
        builder.setObjectList(students);
        builder.setOutputStream(fileOutputStream);

        ParseListToExcelSheet<Student> parseListToExcelSheet = builder.build();
        parseListToExcelSheet.write();


    }

    @After
    public void tearDown() throws Exception {
        File file = new File("c:\\test.xls");
        if (file.exists()) {
            file.delete();
        }


    }

    private void addStudents() {
        Student student = new Student();
        student.setAge(10);
        student.setHeight(120.0123);
        student.setName("shfq");
        student.setNo(10);
        students = new ArrayList<Student>();
        students.add(student);
    }

    private void setMappings() {
        mappings = new FieldMappingExcelHeaderCell[4];
        FieldMappingExcelHeaderCell mapping1 = new FieldMappingExcelHeaderCell("no", "学号");
        FieldMappingExcelHeaderCell mapping2 = new FieldMappingExcelHeaderCell("name", "姓名");
        FieldMappingExcelHeaderCell mapping3 = new FieldMappingExcelHeaderCell("age", "年龄");
        FieldMappingExcelHeaderCell mapping4 = new FieldMappingExcelHeaderCell("height", "身高");
        mappings[0] = mapping1;
        mappings[1] = mapping2;
        mappings[2] = mapping3;
        mappings[3] = mapping4;
    }
}