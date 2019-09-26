package Model;

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
    
    public double getGPA(){
      //To be implmented at a later date
      return(0.0);
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
