package Factory;

import Model.Assignment;

public class AssignmentFactory {
    public static Assignment createAssignment(String name, double potentialGrade, double currentGrade) {
        return new Assignment(name, potentialGrade, currentGrade);
    }
}
