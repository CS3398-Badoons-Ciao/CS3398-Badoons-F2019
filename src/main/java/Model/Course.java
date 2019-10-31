package Model;

import Interfaces.CategoryInterface;
import Interfaces.Listener;
import Interfaces.Publisher;

import java.util.ArrayList;
import java.util.Collection;

public class Course implements Publisher, java.io.Serializable
{
    // skips field when serializing
    transient ArrayList<Listener> listeners = new ArrayList<>();

    public String name;
    private double grade;
    private School school;
    private int creditHours;
    private ArrayList<Category> categories;


    public Course(String name, School school) {
        this.name = name;
        this.school = school;
        categories = new ArrayList<Category>();
    }

    public Course(String name, School school, ArrayList<Category> categories) {
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
        notifyChanged();
    }

    public double getGrade()
    {
        return grade;
    }

    public void setGrade(double grade)
    {
        this.grade = grade;
        notifyChanged();
    }

    public School getSchool()
    {
        return school;
    }

    public void setSchool(School school)
    {
        this.school = school;
        notifyChanged();
    }

    public ArrayList<Category> getCategories()
    {
        return categories;
    }

    public void removeCategory(int i){
        categories.remove(i);
    }

    /**
     * removes a category from the categories collection
     * @param c Category to remove from the collection
     */
    public void removeCategory(CategoryInterface c) {
        categories.remove(c);
        notifyChanged();
    }

    public void setCategories(ArrayList<Category> categories)
    {
        this.categories = categories;
        notifyChanged();
    }

    public int getCreditHours()
    {
        return creditHours;
    }

    public void setCreditHours(int creditHrs)
    {
        this.creditHours = creditHrs;
        notifyChanged();
    }

    @Override
    public void addListener(Listener l) {
        // TODO fix
        listeners = new ArrayList<>();
        listeners.add(l);
    }

    @Override
    public void notifyChanged() {
        for (Listener listener : listeners) {
            listener.update(this);
        }
    }
}
