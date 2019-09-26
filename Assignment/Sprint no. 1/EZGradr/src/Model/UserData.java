package Model;

import java.util.ArrayList;

public class UserData
{
    private String name;
    private int id;
    private ArrayList<Course> pastCourses;
    private ArrayList<Course> presentCourses;
    private float GPA;

    public UserData(String name, int id)
    {
        this.name = name;
        this.id = id;
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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

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


    public float getGPA()
    {
        return GPA;
    }

    public void setGPA(float GPA)
    {
        this.GPA = GPA;
    }
}
