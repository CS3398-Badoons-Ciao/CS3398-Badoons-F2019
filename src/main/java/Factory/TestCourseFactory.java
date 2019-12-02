package Factory;

import Model.Assignment;
import Model.Category;
import Model.Course;
import Model.School;
import Exception.*;

import java.util.ArrayList;

public class TestCourseFactory {

    public static Course buildCourse() throws DuplicateNameException {

        // Quiz
        ArrayList<Assignment> quizAssignments = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            Assignment a1 = new Assignment("Quiz " + i, 90 + i, 100);
            quizAssignments.add(a1);
        }
        Category quiz = new Category("Quiz", 0.20, quizAssignments);

        // Test
        ArrayList<Assignment> testAssignments = new ArrayList<>();
        Assignment b1 = new Assignment("Test 1", 90, 100);
        Assignment b2 = new Assignment("Test 2", 80, 90);
        testAssignments.add(b1);
        testAssignments.add(b2);
        Category test = new Category("Test", 0.40, testAssignments);

        // Project
        ArrayList<Assignment> projectAssignments = new ArrayList<>();
        Assignment c1 = new Assignment("Project 1", 99, 99);
        projectAssignments.add(c1);
        Category project = new Category("Project", 0.40, projectAssignments);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(quiz);
        categories.add(test);
        categories.add(project);
        return new Course("CS 3398", new School("Texas State"), categories);
    }

    public static Course buildCourse2() throws DuplicateNameException {

        // Homework
        ArrayList<Assignment> homeworkAssignments = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            Assignment a1 = new Assignment("Homework " + i*2, 90 + i, 100);
            homeworkAssignments.add(a1);
        }
        Category homeWork = new Category("Homework", 0.60, homeworkAssignments);

        // Test
        ArrayList<Assignment> testAssignments = new ArrayList<>();
        Assignment b1 = new Assignment("Test 1", 100, 100);
        Assignment b2 = new Assignment("Test 2", 95, 90);
        testAssignments.add(b1);
        testAssignments.add(b2);
        Category test = new Category("Test", 0.40, testAssignments);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(homeWork);
        categories.add(test);
        return new Course("CS 4371", new School("Texas State"), categories);
    }

    public static Course buildCourse3() throws DuplicateNameException {

        // Homework
        ArrayList<Assignment> homeworkAssignments = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            Assignment a1 = new Assignment("Homework " + i*2, 90 + i, 100);
            homeworkAssignments.add(a1);
        }
        Category homeWork = new Category("Homework", 0.60, homeworkAssignments);

        // Test
        ArrayList<Assignment> testAssignments = new ArrayList<>();
        Assignment b1 = new Assignment("Test 1", 100, 100);
        Assignment b2 = new Assignment("Test 2", 100, 90);
        testAssignments.add(b1);
        testAssignments.add(b2);
        Category test = new Category("Test", 0.40, testAssignments);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(homeWork);
        categories.add(test);
        return new Course("MATH 3398", new School("Texas State"), categories);
    }

    public static Course buildCourse4() throws DuplicateNameException {

        // Homework
        ArrayList<Assignment> homeworkAssignments = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            Assignment a1 = new Assignment("Homework " + i*2, 80 + i, 100);
            homeworkAssignments.add(a1);
        }
        Category homeWork = new Category("Homework", 0.60, homeworkAssignments);

        // Test
        ArrayList<Assignment> testAssignments = new ArrayList<>();
        Assignment b1 = new Assignment("Test 1", 86, 100);
        Assignment b2 = new Assignment("Test 2", 80, 100);
        testAssignments.add(b1);
        testAssignments.add(b2);
        Category test = new Category("Test", 0.40, testAssignments);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(homeWork);
        categories.add(test);
        return new Course("MATH 101", new School("Texas State"), categories);
    }

    public static Course buildCourse5() throws DuplicateNameException {

        // Homework
        ArrayList<Assignment> homeworkAssignments = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            Assignment a1 = new Assignment("Homework " + i*2, 90 + i, 100);
            homeworkAssignments.add(a1);
        }
        Category homeWork = new Category("Homework", 0.60, homeworkAssignments);

        // Test
        ArrayList<Assignment> testAssignments = new ArrayList<>();
        Assignment b1 = new Assignment("Test 1", 100, 100);
        Assignment b2 = new Assignment("Test 2", 100, 90);
        testAssignments.add(b1);
        testAssignments.add(b2);
        Category test = new Category("Test", 0.40, testAssignments);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(homeWork);
        categories.add(test);
        return new Course("PHIL 1305", new School("Texas State"), categories);
    }

}
