package Model;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

//this class will format UserData into an excel spreadsheet
public class ExcelFormatter
{
    private UserData userData;
    private Workbook workbook;

    public ExcelFormatter(Workbook workbook, UserData userData)
    {
        this.userData = userData;
        this.workbook = workbook;
    }

    public Workbook formatFile()
    {
        ArrayList<Course> courses = userData.getPresentCourses();
        courses.addAll(userData.getPastCourses());

        //clears all previously saved data
        while (workbook.getNumberOfSheets() > 0)
        {
            workbook.removeSheetAt(0);
        }

        //format each sheet in the workbook
        for (int i = 0; i < courses.size(); i++)
        {
            formatCourseSheet(i, courses.get(i));
        }

        return workbook;
    }

    public void formatCourseSheet(int index, Course course)
    {
        //creates a new sheet and sets its name
        workbook.createSheet();
        workbook.setSheetName(index, course.getName());

        //gets the Sheet
        Sheet sheet = workbook.getSheetAt(index);

        //Must be calculated to determine the size of the 2D array for modeling the Sheet
        int longestCategorySize = 0;

        for (Category category : course.getCategories())
        {
            if (category.getAssignments().size() > longestCategorySize)
                longestCategorySize = category.getAssignments().size();
        }

        //To understand these calculations, check the demo file
        int rowLength = 8 + longestCategorySize;
        int colLength = 3 + 2 * course.getCategories().size();

        //It is easier to calculate the locations of each element in a 2-Dimensional array than to do it using the Workbook's functionality, so
        //I chose to organize the data in this sheetArray, before copying into the Sheet itself :)
        String[][] sheetArray = new String[rowLength][colLength];

        sheetArray[0][0] = "School";                    sheetArray[0][1] = course.getSchool().getName();
        sheetArray[1][0] = "Credit Hours";              sheetArray[1][1] = course.getCreditHours() + "";
        sheetArray[2][0] = "Overall Grade";             sheetArray[2][1] = course.getGrade() + "";

        sheetArray[4][0] = "Categories:";

        ArrayList<Category> categories = course.getCategories();

        //for each category in the course
        for (int i = 0; i <  categories.size(); i++)
        {
            sheetArray[6][i * 2] = categories.get(i).getName(); //category name
            sheetArray[7][i * 2] = "Assignment Name";       sheetArray[7][i * 2 + 1] = "Assignment Grade";

            //for each assignment in the category
            for (int j = 0; j < categories.get(i).getAssignments().size(); j++)
            {
                //sets assignment name
                String name = categories.get(i).getAssignments().get(j).getName();
                String grade = categories.get(i).getAssignments().get(j).getCurrentGrade() + "";

                sheetArray[8 + j][i * 2] = name;            sheetArray[8 + j][i * 2 + 1] = grade;
            }
        }

        for (int r = 0; r < rowLength; r++)
        {
            //creates a row in the Sheet at r
            Row row = sheet.createRow(r);

            for (int c = 0; c < colLength; c++)
            {
                //creates a cell in the Sheet at (r,c) and sets it's value to the corresponding value in SheetArray
                Cell cell = row.createCell(c);
                cell.setCellValue(sheetArray[r][c]);
            }
        }
    }
}
