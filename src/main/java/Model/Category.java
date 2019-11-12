package Model;

import Interfaces.AssignmentInterface;
import Interfaces.CategoryInterface;
import Exception.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * Category Assignment's must have unique names.
 */
public class Category implements CategoryInterface, java.io.Serializable {

    private String name;
    private double weight; //value should range from 0.0 - 100.0
    private ArrayList<Assignment> assignments = null;

    public Category(String name, double weight, ArrayList<Assignment> assignmentsList) throws DuplicateNameException {
        this.name = name;
        this.weight = weight;
        assignments = new ArrayList<Assignment>();
        addAssignments(assignmentsList);
    }

    public Category(String name, double weight)
    {
        this.name = name;
        this.weight = weight;
        assignments = new ArrayList<Assignment>();
    }

    /**
     * @param newAssignment assignment to add
     * @throws DuplicateNameException
     */
    public void addAssignment(Assignment newAssignment) throws DuplicateNameException {
        if (verifyAssignmentUnique(newAssignment.getName(), getAssignments())) {
            assignments.add(newAssignment);
        }
        else
            throw new DuplicateNameException(newAssignment.getName());
    }

    /**
     *
     * @param newAssignments list of assignments to add
     * @throws DuplicateNameException if assignment name exists, no assignments are added.
     */
    public void addAssignments(ArrayList<Assignment> newAssignments) throws DuplicateNameException {
        ArrayList<Assignment> temp = new ArrayList<Assignment>();

        for (Assignment a : newAssignments) {
            if (verifyAssignmentUnique(a.getName(), getAssignments())) {
                temp.add(a);
            }
            else
                throw new DuplicateNameException(a.getName());
        }

        assignments.addAll(temp);
    }

    /**
     * Sets Assignments equal to parameter
     * @param assignments Assignments to set
     * @throws DuplicateNameException if there are duplicate names in assignments parameter, this.assignments unchanged.
     */
    public void setAssignments(ArrayList<Assignment> assignments) throws DuplicateNameException {
        ArrayList<Assignment> temp = new ArrayList<Assignment>();

        for (int i = 0; i < assignments.size(); i++) {
            for (int j = i + 1; j < assignments.size(); j++) {
                if (!assignments.get(i).getName().equals(assignments.get(j).getName())) {
                    temp.add(assignments.get(i));
                }
                else throw new DuplicateNameException(assignments.get(i).getName());
            }
        }
        this.assignments = assignments;
    }

    /**
     * @param assignmentToChange
     * @param name String of new Assignment name
     * @throws DuplicateNameException if for all Assignments a in list, a.getName() == name
     */
    public void changeAssignmentName(AssignmentInterface assignmentToChange, String name) throws DuplicateNameException {
        if (assignmentToChange.getName().equals(name))
            return;

        for (AssignmentInterface a : getAssignments()) {
            if (a.getName().equals(name)) {
                throw new DuplicateNameException(name);
            }
        }

        ((Assignment) assignmentToChange).setName(name);
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

    public boolean verifyAssignmentUnique(String name, Collection<Assignment> assignments) {
        for (Assignment a : assignments) {
            if (a.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /** To enforce Invariant, call only through Category list container */
    void setName(String name) { this.name = name; }
}
