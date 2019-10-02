package Model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculator
{
    public Calculator()
    {

    }
    
    public double getAverage(double[] n){
      double currentSum = 0;
      for (int i = 0; i < n.length; i++){
        currentSum += n[i];
      }
      return (currentSum/n.length);
    }

    //given an ArrayList of past courses and current courses, this function returns the GPA of all of those courses
    public double getGPAOverall(ArrayList<Course> previousCourses, ArrayList<Course> currentCourses){
      int totalCreditHours = 0;
      double totalWeightedPoints = 0;

      //totals up hours and weighted points of previousCourses
      for (Course course : previousCourses)
      {
        totalCreditHours += course.getCreditHours();
        totalWeightedPoints += (course.getCreditHours() * getGradePoints(course.getGrade()));
      }

      //totals up hours and weighted points of currentCourses
      for (Course course : currentCourses)
      {
        totalCreditHours += course.getCreditHours();
        totalWeightedPoints += (course.getCreditHours() * getGradePoints(course.getGrade()));
      }

      //calculates GPA by dividing the total weighted points by the total number of hours taken,
      //then formats the result to 2 decimal points, and finally returns the GPA value as a Double
      return(Double.parseDouble(new DecimalFormat("#.00").format(totalWeightedPoints / totalCreditHours)));
    }

    //given a SINGLE ArrayList of courses, this function returns the GPA returns the GPA of all of those courses
    public double getGPASingle(ArrayList<Course> courses)
    {
        int totalCreditHours = 0;
        double totalWeightedPoints = 0;

        //totals up hours and weighted points of previousCourses
        for (Course course : courses)
        {
            totalCreditHours += course.getCreditHours();
            totalWeightedPoints += (course.getCreditHours() * getGradePoints(course.getGrade()));
        }

        //calculates GPA by dividing the total weighted points by the total number of hours taken,
        //then formats the result to 2 decimal points, and finally returns the GPA value as a Double
        return(Double.parseDouble(new DecimalFormat("#.00").format(totalWeightedPoints / totalCreditHours)));
    }

    //given a course's grade, this function returns the grade points
    //ex)
    //90-100 = 4
    //80-89.99 = 3
    //70-79.99 = 2
    //60-69.99 = 1
    //0-59.99 = 0
    public int getGradePoints(double grade)
    {
        if (grade >= 90.0)
            return 4;
        else if (grade >= 80.0)
            return 3;
        else if (grade >= 70.0)
            return 2;
        else if (grade >= 60.0)
            return 1;
        else
            return 0;
    }
    
    public double getCatagoryGrade(ArrayList<Assignment> assign, double weight){
      double[] n = new double[assign.size()];
      for (int i = 0; i < assign.size(); i++){
        n[i] = (assign.get(i).getCurrentGrade() / assign.get(i).getPotentialGrade());
      }
      return (getAverage(n)*weight);
    }
    
    public double getCourseGrade(Course c){
      ArrayList<Category> categories = c.getCategories();
      double currentSum = 0;
      for (int i = 0; i < categories.size(); i++){
        currentSum += getCatagoryGrade(categories.get(i).getAssignments(),categories.get(i).getWeight());
      }
      return(currentSum);
    }
    
    public double getGradeImportance(Course c, Assignment a){
      ArrayList<Category> categories = c.getCategories();
      double totalPoints = 0;
      for (int i = 0; i < categories.size(); i++){
       ArrayList<Assignment> assign = categories.get(i).getAssignments();
        for (int j = 0; j < assign.size(); j++){
          totalPoints += assign.get(i).getPotentialGrade();
        } 
      }
      return(a.getCurrentGrade()/totalPoints);
    }
    
}
