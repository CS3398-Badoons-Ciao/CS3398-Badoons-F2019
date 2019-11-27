package Interfaces;

import java.util.Date;

public interface AssignmentInterface {
    String getName();
    double getPotentialGrade();
    void setPotentialGrade(double potentialGrade);
    double getCurrentGrade();
    void setCurrentGrade(double currentGrade);
    void setDate(Date date);
    Date getDate();
}

