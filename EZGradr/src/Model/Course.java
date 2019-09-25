package Model;

import java.util.ArrayList;

public class Course
{
    private String name;
    private float grade;
    private School school;
    private ArrayList<Category> categories;

    public Course(String name, School school)
    {
        this.name = name;
        this.school = school;
        categories = new ArrayList<Category>();
    }

    public Course(String name, School school, ArrayList<Category> categories)
    {
        this.name = name;
        this.school = school;
        this.categories = categories;
    }

    //adds a single Category to categories
    public void addCategory(Category newCategory)
    {
        categories.add(newCategory);
    }

    //adds multiple Categories to categories
    public void addCategories(ArrayList<Category> newCategories)
    {
        categories.addAll(newCategories);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getGrade()
    {
        return grade;
    }

    public void setGrade(float grade)
    {
        this.grade = grade;
    }

    public School getSchool()
    {
        return school;
    }

    public void setSchool(School school)
    {
        this.school = school;
    }

    public ArrayList<Category> getCategories()
    {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories)
    {
        this.categories = categories;
    }
}
