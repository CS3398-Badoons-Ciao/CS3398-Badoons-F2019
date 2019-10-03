package Model;

import Interfaces.AssignmentInterface;

public class Assignment implements AssignmentInterface
{
    private String name;
    private double potentialGrade;
    private double currentGrade;

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

    public void setName(String name)
    {
        this.name = name;
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
}
