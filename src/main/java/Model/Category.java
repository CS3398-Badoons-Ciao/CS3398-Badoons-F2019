package Model;

import java.util.ArrayList;

public class Category {

    private String name;
    private double weight; //value should range from 0.0 - 100.0
    private ArrayList<Assignment> assignments;

    public Category(String name, double weight, ArrayList<Assignment> assignments)
    {
        this.name = name;
        this.weight = weight;
        this.assignments = assignments;
    }

    public Category(String name, double weight)
    {
        this.name = name;
        this.weight = weight;
    }

    //adds a single Assignment to the assignments ArrayList
    public void addAssignment(Assignment newAssignment)
    {
        assignments.add(newAssignment);
    }

    //adds multiple Assignments to the assignments ArrayList
    public void addAssignments(ArrayList<Assignment> newAssignments)
    {
        assignments.addAll(newAssignments);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public ArrayList<Assignment> getAssignments()
    {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments)
    {
        this.assignments = assignments;
    }
}