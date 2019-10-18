package Model;

import Interfaces.CategoryInterface;
import Interfaces.Publisher;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Category implements CategoryInterface, java.io.Serializable {

    private String name;
    private double weight; //value should range from 0.0 - 100.0
    private ArrayList<Assignment> assignments = null;

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
        assignments = new ArrayList<Assignment>();
    }

    //adds a single Assignment to the assignments ArrayList
    public void addAssignment(Assignment newAssignment) {
        assignments.add(newAssignment);
    }

    //adds multiple Assignments to the assignments ArrayList
    public void addAssignments(ArrayList<Assignment> newAssignments)
    {
        assignments.addAll(newAssignments);
    }

    // removes a single Assignment
    public void removeAssignment(String name) {
        Assignment assignmentToRemove = null;
        for (Assignment a : assignments) {
            if (name.equals(a.getName())) {
                assignmentToRemove = a;
            }
        }
        if (assignmentToRemove != null) {
            assignments.remove(assignmentToRemove);
        }
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

    public void removeAssignment(int i){
        assignments.remove(i);
    }

    public void setAssignments(ArrayList<Assignment> assignments)
    {
        this.assignments = assignments;
    }
}
