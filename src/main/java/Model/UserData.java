package Model;

import java.util.ArrayList;

import Exception.*;

/**
 * Manages Course list.
 * Courses must have unique names.
 */
public class UserData implements java.io.Serializable {
    private String name;
    private String id;
    private String password;
    private ArrayList<Course> pastCourses;
    private ArrayList<Course> presentCourses;
    private double GPA;

    public UserData(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
        pastCourses = new ArrayList<Course>();
        presentCourses = new ArrayList<Course>();
    }

    /**
     * @param newCourse to add to past courses list
     * @throws DuplicateNameException
     */
    public void addPastCourse(Course newCourse) throws DuplicateNameException {
        if (verifyCourseUnique(newCourse.getName()))
            pastCourses.add(newCourse);
        else
            throw new DuplicateNameException(newCourse.getName());
    }

    /**
     * @param newCourses new past courses to add to existing past courses list
     * @throws DuplicateNameException
     */
    public void addPastCourses(ArrayList<Course> newCourses) throws DuplicateNameException {
        ArrayList<Course> temp = new ArrayList<Course>();

        for (Course c : newCourses) {
            if (verifyCourseUnique(c.getName())) {
                temp.add(c);
            }
            else
                throw new DuplicateNameException(c.getName());
        }

        pastCourses.addAll(temp);
    }

    /**
     * @param pastCourses list of past courses to replace existing past courses list
     * @throws DuplicateNameException
     */
     public void setPastCourses(ArrayList<Course> pastCourses) throws DuplicateNameException {
         ArrayList<Course> temp = new ArrayList<Course>();

         for (int i = 0; i < pastCourses.size(); i++) {
             for (int j = i + 1; j < pastCourses.size(); j++) {
                 if (!pastCourses.get(i).getName().equals(pastCourses.get(j).getName())) {
                     temp.add(pastCourses.get(i));
                 }
                 else throw new DuplicateNameException(pastCourses.get(i).getName());
             }
         }
        this.pastCourses = pastCourses;
     }

    /**
     * @param newCourse to add to present courses list
     * @throws DuplicateNameException
     */
    public void addPresentCourse(Course newCourse) throws DuplicateNameException {
        if (verifyCourseUnique(newCourse.getName()))
            presentCourses.add(newCourse);
        else
            throw new DuplicateNameException(newCourse.getName());
    }

    /**
     * @param newCourses new present course list to add to existing present course list
     * @throws DuplicateNameException if a Course name exists, no Courses are added.
     */
    public void addPresentCourses(ArrayList<Course> newCourses) throws DuplicateNameException {
        ArrayList<Course> temp = new ArrayList<Course>();

        for (Course c : newCourses) {
            if (verifyCourseUnique(c.getName())) {
                temp.add(c);
            }
            else
                throw new DuplicateNameException(c.getName());
        }

        presentCourses.addAll(temp);
    }

    public void movePresentCoursesToPastCourses() {
        pastCourses.addAll(presentCourses);

        for (Course course : presentCourses)
        {
            presentCourses.remove(course);
        }
    }

    /**
     * @param name String new name
     * @throws DuplicateNameException Course target of name change
     */
    public void changeCourseName(Course course, String name) throws DuplicateNameException {
        if (course.getName().equals(name))
            return;

        ArrayList<Course> allCourses = getAllCourses();

        for (Course c : allCourses) {
            if (c.getName().equals(name)) {
                throw new DuplicateNameException(name);
            }
        }

        course.setName(name);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public boolean checkPassword(String n){
        if (n.equals(password)){
            return true;
        }
        return false;
    }

    public void setPassword(String n) {this.password = n;}

    public ArrayList<Course> getPastCourses()
    {
        return pastCourses;
    }

    public ArrayList<Course> getPresentCourses()
    {
        return presentCourses;
    }

    public void removeCourse(int i){
        presentCourses.remove(i);
    }

    public double getGPA()
    {
        return GPA;
    }

    public void setGPA(double GPA)
    {
        this.GPA = GPA;
    }

    /**
     * @param name
     * @return true if course name is unique
     */
    public boolean verifyCourseUnique(String name) {
        ArrayList<Course> allCourses = getAllCourses();

        for (Course course : allCourses) {
            if (course.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return list of past + present Courses
     */
    private ArrayList<Course> getAllCourses() {
        ArrayList<Course> allCourses = new ArrayList<Course>();
        allCourses.addAll(getPresentCourses());
        allCourses.addAll(getPastCourses());
        return allCourses;
    }

}
