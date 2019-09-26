package Model;

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
    
    public double getCatagoryGrade(double[] n, double weight){
      return (getAverage(n)*weight);
    }
    
    public double getCourseGrade(Course c){
      //Further look into how courses have been impmented needed before this fuction can be made
      return(0.0);
    }
    
    public double getGradeImportance(){
      //Further look into how courses have been impmented needed before this fuction can be made
      return(0.0);
    }
}
