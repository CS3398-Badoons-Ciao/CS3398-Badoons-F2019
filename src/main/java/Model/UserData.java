package Model;

import Interfaces.Publisher;

import java.util.ArrayList;

public class UserData implements java.io.Serializable
{
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

    //adds a single Course to pastCourses
    public void addPastCourse(Course newCourse)
    {
        pastCourses.add(newCourse);
    }

    //adds multiple Courses to pastCourses
    public void addPastCourses(ArrayList<Course> newCourses)
    {
        pastCourses.addAll(newCourses);
    }

    //adds a single Course to presentCourses
    public void addPresentCourse(Course newCourse)
    {
        presentCourses.add(newCourse);
    }

    //adds multiple Courses to presentCourses
    public void addPresentCourses(ArrayList<Course> newCourses)
    {
        presentCourses.addAll(newCourses);
    }

    //removes all elements from presentCourses and puts them into pastCourses
    public void movePresentCoursesToPastCourses()
    {
        pastCourses.addAll(presentCourses);

        for (Course course : presentCourses)
        {
            presentCourses.remove(course);
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

    public void setPastCourses(ArrayList<Course> pastCourses)
    {
        this.pastCourses = pastCourses;
    }

    public ArrayList<Course> getPresentCourses()
    {
        return presentCourses;
    }

    public void setPresentCourses(ArrayList<Course> presentCourses)
    {
        this.presentCourses = presentCourses;
    }


    public double getGPA()
    {
        return GPA;
    }

    public void setGPA(double GPA)
    {
        this.GPA = GPA;
    }
}
