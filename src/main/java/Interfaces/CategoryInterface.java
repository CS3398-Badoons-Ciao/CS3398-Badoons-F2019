package Interfaces;

import Model.Assignment;

import java.util.ArrayList;

public interface CategoryInterface {
    void addAssignment(Assignment newAssignment);
    void addAssignments(ArrayList<Assignment> newAssignments);
    String getName();
    void setName(String name);
    double getWeight();
    void setWeight(double weight);
    ArrayList<Assignment> getAssignments();
    void setAssignments(ArrayList<Assignment> assignments);
}
