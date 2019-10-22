package Model;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class List{
  ArrayList<Assignment> assignments = new ArrayList<>();
  private Date dueDate, currentDate;
  private boolean isPast = false;

  public void List(){
    //Code to look for what assignments have dates associated with them
    //and add them to the assignments list.
  }

  public void pastDue() throws ParseException{
    //Get assignment due date and current date.
    currentDate = new Date();
    if (dueDate.after(currentDate)) {
      isPast = true;
    }
    else if (dueDate.equals(currentDate)) {
      isPast = true;
    }
  }
}
