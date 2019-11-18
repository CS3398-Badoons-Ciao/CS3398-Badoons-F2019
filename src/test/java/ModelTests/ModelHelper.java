package ModelTests;

import Model.*;
import Exception.*;
import org.junit.Assert;

import java.util.ArrayList;

//This class is for methods to assist with testing the model
public class ModelHelper
{
    //generates some "dummy" data to be tested with
    public static UserData generateUserData() {
        UserData userData = new UserData("Bob", "id", "password");

        try
        {
            Calculator calculator = new Calculator();

            ArrayList<Assignment> homeworkListPrevious = new ArrayList<Assignment>();
            homeworkListPrevious.add(new Assignment("Homework 1", 90.0, 100.0));
            homeworkListPrevious.add(new Assignment("Homework 2", 50.0, 100.0));

            ArrayList<Assignment> testsListPrevious = new ArrayList<Assignment>();
            testsListPrevious.add(new Assignment("Test 1", 70.0, 100.0));
            testsListPrevious.add(new Assignment("Test 2", 30.0, 100.0));

            ArrayList<Assignment> homeworkListCurrent = new ArrayList<Assignment>();
            homeworkListCurrent.add(new Assignment("Homework 1", 50.0, 100.0));
            homeworkListCurrent.add(new Assignment("Homework 2", 90.0, 100.0));
            homeworkListCurrent.add(new Assignment("Homework 3", 10.0, 100.0));

            ArrayList<Assignment> testsListCurrent = new ArrayList<Assignment>();
            testsListCurrent.add(new Assignment("Test 1", 50.0, 100.0));

            Category homeworkPrevious = new Category("Homework", 50.0, homeworkListPrevious);
            Category testsPrevious = new Category("Tests", 50.0, testsListPrevious);

            Category homeworkCurrent = new Category("Homework", 70.0, homeworkListCurrent);
            Category testsCurrent = new Category("Tests", 30.0, testsListCurrent);

            ArrayList<Category> categoriesPrevious = new ArrayList<Category>();
            categoriesPrevious.add(homeworkPrevious);
            categoriesPrevious.add(testsPrevious);

            ArrayList<Category> categoriesCurrent = new ArrayList<Category>();
            categoriesCurrent.add(homeworkCurrent);
            categoriesCurrent.add(testsCurrent);

            School school = new School("Texas State University");

            Course course1 = new Course("Math", school, categoriesPrevious);
            course1.setGrade(calculator.getCourseGrade(course1));

            Course course2 = new Course("Science", school, categoriesCurrent);
            course2.setGrade(calculator.getCourseGrade(course2));

            course1.setCreditHours(3);
            course2.setCreditHours(3);

            userData.addPastCourse(course1);
            userData.addPresentCourse(course2);

        }
        catch (Exception | DuplicateNameException e)
        {
            Assert.fail("Failed due to the following exception while generating test data: " + e.toString());
        }

        return userData;
    }
}
