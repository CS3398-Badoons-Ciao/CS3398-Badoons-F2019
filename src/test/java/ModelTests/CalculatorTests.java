package ModelTests;

import Model.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CalculatorTests
{

    @Test
    public void testGetAverage()
    {
        Calculator calculator = new Calculator();

        //3 types of inputs we can expect to receive
        double[] zeroArray = new double[] {0.0, 0.0};
        double[] ordinaryArray = new double[] {70.0, 50.0, 30.0};
        double[] fancyArray = new double[] {30.4, 67.9, 100.0, 25.6, 0.0};

        Assert.assertTrue("calculator.getAverage() fails for an input of all zeros", calculator.getAverage(zeroArray) == 0.0);
        Assert.assertTrue("calculator.getAverage() fails for an ordinary input", calculator.getAverage(ordinaryArray) == 50.0);
        Assert.assertTrue("calculator.getAverage() fails for a fancy input", calculator.getAverage(fancyArray) == 44.78);
    }

    @Test
    public void testGetGPAOverall()
    {
        Calculator calculator = new Calculator();
        UserData userData = ModelHelper.generateUserData();

        Assert.assertTrue("calculator.getGPAOverall() fails", calculator.getGPAOverall(userData.getPastCourses(), userData.getPresentCourses()) == 0.5);
    }

    @Test
    public void testGetGPASingle()
    {
        Calculator calculator = new Calculator();
        UserData userData = ModelHelper.generateUserData();

        Assert.assertTrue("calculator.getGPASingle() fails", calculator.getGPASingle(userData.getPastCourses()) == 1.0);
        Assert.assertTrue("calculator.getGPASingle() fails", calculator.getGPASingle(userData.getPresentCourses()) == 0.0);
    }

    @Test
    public void testGetGradePoints()
    {
        Calculator calculator = new Calculator();

        Assert.assertTrue("calculator.getGradePoints() fails for an input of 0.0", calculator.getGradePoints(0.0) == 0.0);
        Assert.assertTrue("calculator.getGradePoints() fails for an input of 59.9", calculator.getGradePoints(59.9) == 0.0);
        Assert.assertTrue("calculator.getGradePoitns() fails for an input of 60.0", calculator.getGradePoints(60.0) == 1.0);
        Assert.assertTrue("calculator.getGradePoints() fails for an input of 69.9", calculator.getGradePoints(69.9) == 1.0);
        Assert.assertTrue("calculator.getGradePoints() fails for an input of 70.0", calculator.getGradePoints(70.0) == 2.0);
        Assert.assertTrue("calculator.getGradePoints() fails for an input of 79.9", calculator.getGradePoints(79.9) == 2.0);
        Assert.assertTrue("calculator.getGradePoitns() fails for an input of 80.0", calculator.getGradePoints(80.0) == 3.0);
        Assert.assertTrue("calculator.getGradePoints() fails for an input of 89.9", calculator.getGradePoints(89.9) == 3.0);
        Assert.assertTrue("calculator.getGradePoints() fails for an input of 90.0", calculator.getGradePoints(90.0) == 4.0);
        Assert.assertTrue("calculator.getGradePoints() fails for an input of 99.9", calculator.getGradePoints(99.9) == 4.0);
        Assert.assertTrue("calculator.getGradePoints() fails for an input of 100.0", calculator.getGradePoints(100.0) == 4.0);
    }

    @Test
    public void testGetCategoryGrade()
    {
        Calculator calculator = new Calculator();

        UserData userData = ModelHelper.generateUserData();

        ArrayList<Assignment> assignments1 = userData.getPresentCourses().get(0).getCategories().get(0).getAssignments();
        ArrayList<Assignment> assignments2 = userData.getPresentCourses().get(0).getCategories().get(1).getAssignments();
        ArrayList<Assignment> assignments3 = userData.getPastCourses().get(0).getCategories().get(0).getAssignments();
        ArrayList<Assignment> assignments4 = userData.getPresentCourses().get(0).getCategories().get(1).getAssignments();

        Assert.assertTrue("calculator.getCategoryGrade() failed for assignments1", calculator.getCategoryGrade(assignments1) == 0.5);
        Assert.assertTrue("calculator.getCategoryGrade() failed for assignments2", calculator.getCategoryGrade(assignments2) == 0.5);
        Assert.assertTrue("calculator.getCategoryGrade() failed for assignments3", calculator.getCategoryGrade(assignments3) == 0.7);
        Assert.assertTrue("calculator.getCategoryGrade() failed for assignments4", calculator.getCategoryGrade(assignments4) == 0.5);
    }

    @Test
    public void testGetCourseGrade()
    {
        Calculator calculator =  new Calculator();

        UserData userData = ModelHelper.generateUserData();

        Course course1 = userData.getPresentCourses().get(0);
        Course course2 = userData.getPastCourses().get(0);

        Assert.assertTrue("calculator.getCourseGrade() failed for course1", calculator.getCourseGrade(course1) == 50.0);
        Assert.assertTrue("calculator.getCourseGrade() failed for course2", calculator.getCourseGrade(course2) == 60.0);
    }

    @Test
    public void testGetGradeImportance()
    {
        Calculator calculator = new Calculator();

        UserData userData = ModelHelper.generateUserData();

        Course course1 = userData.getPresentCourses().get(0);
        Assignment assignment1 = course1.getCategories().get(0).getAssignments().get(0);

        Course course2 = userData.getPastCourses().get(0);
        Assignment assignment2 = course2.getCategories().get(0).getAssignments().get(0);

        System.out.println(calculator.getGradeImportance(course2, assignment2));

        Assert.assertTrue("calculator.getGradeImportance() failed for assignment1", calculator.getGradeImportance(course1, assignment1) == 0.125);
        Assert.assertTrue("calculator.getGradeImportance() failed for assignment2", calculator.getGradeImportance(course2, assignment2) == 0.225);
    }
}
