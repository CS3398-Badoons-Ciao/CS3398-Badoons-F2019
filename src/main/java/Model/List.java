package Model;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class List{
  private ArrayList<Assignment> assignmentList = new ArrayList<>();
  private Date dueDate, currentDate;
  private boolean isPast = false;
  private Model model = null;

  public List(Model model) {
    this.model = model;
    //Code to look for what assignments have dates associated with them
    //and add them to the assignments list.
    ArrayList<Course> courses = model.user.getPastCourses();
    for (int i = 0; i < courses.size(); i++)
      for (int j = 0; j < courses.get(i).getCategories().size(); j++)
        for (int k = 0; k < courses.get(i).getCategories().get(j).getAssignments().size(); k++)
          if (courses.get(i).getCategories().get(j).getAssignments().get(k).getDate() != null){
            dueDate = courses.get(i).getCategories().get(j).getAssignments().get(k).getDate();
            courses.get(i).getCategories().get(j).getAssignments().get(k).setIsPast(pastDue());
            assignmentList.add(courses.get(i).getCategories().get(j).getAssignments().get(k));
          }
  }
  //Needs isPast value in Assignments.
  
/*  public boolean pastDue() throws ParseException{
    currentDate = new Date();
    if (dueDate.after(currentDate)) {
      isPast = true;
    }
    else if (dueDate.equals(currentDate)) {
      isPast = true;
    }
    return isPast;
  }
  */
}
