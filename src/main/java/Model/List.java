package Model;

import java.util.ArrayList;
import java.util.Date;

public class List {
  private ArrayList<ListObject> assignmentList = new ArrayList<>();
  private Assignment assignment;
  private Date dueDate, currentDate;
  private Model model = null;
  private boolean isPast = false;

  ArrayList<Course> courses = model.user.getPastCourses();

  public List(Model model) {
    this.model = model;
    for (int i = 0; i < courses.size(); i++)
      for (int j = 0; j < courses.get(i).getCategories().size(); j++)
        for (int k = 0; k < courses.get(i).getCategories().get(j).getAssignments().size(); k++)
          if (courses.get(i).getCategories().get(j).getAssignments().get(k).getDate() != null){
            assignmentList.add(new ListObject(courses.get(i).getCategories().get(j).getAssignments().get(k)));
          }
  }
  public void determineIsPastDue(){
    for (int i = 0; i < assignmentList.size(); i++) {
      dueDate = assignmentList.get(i).getAssignment().getDate();
      assignmentList.get(i).setPastDue(pastDue(dueDate));
    }
  }
  public boolean pastDue(Date dueDate) {
    currentDate = new Date();
    if (dueDate.after(currentDate) || dueDate.equals(currentDate))
      isPast = true;
    return isPast;
  }
  public ArrayList getAssignmentList() {
    return assignmentList;
  }
}
