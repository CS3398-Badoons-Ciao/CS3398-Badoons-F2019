package Interfaces;

import Model.Assignment;
import Model.Course;

import java.util.ArrayList;

public interface CategoryCalculatorInterface {
    int getGradePoints(double grade);
    double getCategoryGrade(ArrayList<Assignment> assign);
    double getCourseGrade(Course c);
}
