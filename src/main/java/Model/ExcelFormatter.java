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

        for (int i = 0; i < courses.size(); i++)
        {
            formatCourseSheet(i, courses.get(i));
        }

        return workbook;
    }

    public void formatCourseSheet(int index, Course course)
    {
        //sets the Sheet Name
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
        int rowLength = 3 + 2 * course.getCategories().size();
        int colLength = 7 + longestCategorySize;

        //It is easier to calculate the locations of each element in a 2-Dimensional array than to do it using the Workbook's functionality, so
        //I chose to organize the data in this sheetArray, before copying into the Sheet itself :)
        String[][] sheetArray = new String[rowLength][colLength];

        sheetArray[0][0] = "School";                        sheetArray[1][0] = "Credit Hours";                  sheetArray[2][0] = "Overall Grade";
        sheetArray[1][0] = course.getSchool().getName();    sheetArray[1][1] = course.getCreditHours() + "";    sheetArray[1][2] = course.getGrade() + "";

        sheetArray[3][0] = "Categories:";

        ArrayList<Category> categories = course.getCategories();

        //for each category in the course
        for (int i = 0; i <  categories.size(); i++)
        {
            sheetArray[5][i * 2] = categories.get(i).getName(); //category name
            sheetArray[6][i * 2] = "Assignment Name";       sheetArray[6][i * 2 + 1] = "Assignment Grade";

            //for each assignment in the category
            for (int j = 0; j < categories.get(i).getAssignments().size(); j++)
            {
                //sets assignment name
                String name = categories.get(i).getAssignments().get(j).getName();
                String grade = categories.get(i).getAssignments().get(j).getCurrentGrade() + "";

                sheetArray[7 + j][i * 2] = name;            sheetArray[7 + i][i * 2 + 1] = grade;
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
