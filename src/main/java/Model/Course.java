package Model;

import Interfaces.CategoryInterface;
import Exception.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * Course Categories must have unique names.
 */
public class Course implements java.io.Serializable
{
    private String name;
    private double grade;
    private School school;
    private int creditHours;
    private ArrayList<Category> categories;

    public Course(String name, School school) {
        this.name = name;
        this.school = school;
        categories = new ArrayList<Category>();
    }

    public Course(String name, School school, ArrayList<Category> categoriesList) throws DuplicateNameException {
        this.name = name;
        this.school = school;
        categories = new ArrayList<Category>();
        addCategories(categoriesList);
    }

    /**
     * @param newCategory category to add
     * @throws DuplicateNameException if category name exists
     */
    public void addCategory(Category newCategory) throws DuplicateNameException {
        if (verifyCategoryUnique(newCategory.getName(), getCategories())) {
            categories.add(newCategory);
        }
        else
            throw new DuplicateNameException(newCategory.getName());
    }

    /**
     * @param newCategories Category List to add
     * @throws DuplicateNameException if a category name exists, no Categories are added.
     */
    public void addCategories(ArrayList<Category> newCategories) throws DuplicateNameException {
        ArrayList<Category> temp = new ArrayList<Category>();

        for (Category c : newCategories) {
            if (verifyCategoryUnique(c.getName(), getCategories())) {
                temp.add(c);
            }
            else
                throw new DuplicateNameException(c.getName());
        }

        categories.addAll(temp);
    }

    /**
     * @param categoryToChange Category target to change name
     * @param name String for new name
     * @throws DuplicateNameException if for all Category c in Category list, c.getName().equals(name)
     */
    public void changeCategoryName(CategoryInterface categoryToChange, String name) throws DuplicateNameException {
        if (categoryToChange.getName().equals(name))
            return;

        for (Category category : getCategories()) {
            if (category.getName().equals(name)) {
                throw new DuplicateNameException(name);
            }
        }

        ((Category) categoryToChange).setName(name);
    }

    public String getName()
    {
        return name;
    }

    public double getGrade()
    {
        return grade;
    }

    public void setGrade(double grade)
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

    public void removeCategory(int i){
        categories.remove(i);
    }

    /**
     * removes a category from the categories collection
     * @param c Category to remove from the collection
     */
    public void removeCategory(CategoryInterface c) {
        categories.remove(c);
    }

    public void setCategories(ArrayList<Category> categories)
    {
        this.categories = categories;
    }

    public int getCreditHours()
    {
        return creditHours;
    }

    public void setCreditHours(int creditHrs)
    {
        this.creditHours = creditHrs;
    }

    public boolean verifyCategoryUnique(String name, Collection<Category> categories) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /** To enforce unique Course names, call method through Course list container only.*/
    void setName(String name) {this.name = name;};
}
