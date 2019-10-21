package Factory;

import Model.Assignment;

public class AssignmentFactory {
    public static Assignment createAssignment(String name, double currentGrade, double potentialGrade) {
        return new Assignment(name, currentGrade, potentialGrade);
    }
}
