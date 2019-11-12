package Interfaces;

import Model.Assignment;
import Exception.*;
import java.util.ArrayList;

public interface CategoryInterface {
    void addAssignment(Assignment newAssignment) throws DuplicateNameException;
    void addAssignments(ArrayList<Assignment> newAssignments) throws DuplicateNameException;
    void setAssignments(ArrayList<Assignment> assignments) throws DuplicateNameException;
    public void removeAssignment(String name);
    void changeAssignmentName(AssignmentInterface assignmentToChange, String name) throws DuplicateNameException;
    String getName();
    double getWeight();
    void setWeight(double weight);
    ArrayList<Assignment> getAssignments();
}
