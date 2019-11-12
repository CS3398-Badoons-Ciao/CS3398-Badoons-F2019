package Model;

import Interfaces.AssignmentInterface;
import java.util.Date;

public class Assignment implements AssignmentInterface, java.io.Serializable
{
    private String name;
    private double potentialGrade;
    private double currentGrade;
    private Date   dueDate;


    public Assignment(String name, double currentGrade , double potentialGrade)
    {
        this.name = name;
        this.potentialGrade = potentialGrade;
        this.currentGrade = currentGrade;
    }

    public Assignment(String name, double currentGrade)
    {
        this.name = name;
        this.potentialGrade = currentGrade;
    }

    public String getName()
    {
        return name;
    }

    public double getPotentialGrade()
    {
        return potentialGrade;
    }

    public void setPotentialGrade(double potentialGrade)
    {
        this.potentialGrade = potentialGrade;
    }

    public double getCurrentGrade()
    {
        return currentGrade;
    }

    public void setCurrentGrade(double currentGrade)
    {
        this.currentGrade = currentGrade;
    }

    public void setDate(Date date){ dueDate = date; }

    public Date getDate(){return dueDate; }

    /** to enforce invariant, call only through Assignment container */
    void setName(String name) { this.name = name; }
}
