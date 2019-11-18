package Model;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

//To export a file, first instantiate an ExcelFileExporter object, providing the file path where the file will be saved to,
//and the UserData to be exported. Then all you need to do is call the exportFile() method!

public class ExcelFileExporter
{
    private String filePath;
    private ExcelFormatter excelFormatter;
    private UserData userData;

    public ExcelFileExporter(String filePath, UserData userData)
    {
        this.filePath = filePath;
        this.userData = userData;
    }

    public void exportFile() throws IOException {
        File file = new File(filePath);
        if (!file.isFile())
            file.createNewFile();
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        excelFormatter = new ExcelFormatter(workbook, userData);
        workbook = excelFormatter.formatFile();
        fileInputStream.close();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
    }

}
