package Model;


/*
To do list:
Finish deleteCurrentUser() needs to be able to delete files
 */

import Interfaces.SaveUserInterface;

import java.util.*;
/**
 Model: The back end of EZGrader
 @author Bailey H.
 */
public class Model implements SaveUserInterface
{
    private Calculator calculator = new Calculator();
    private DatabaseManager dbManager;
    public UserData user = null;



    //private CML cml;

    public Model() {
        dbManager = new DatabaseManager();

    }
    //--------------------------------------------------------------------------
    /**
     * Creates a new user
     * @param name The user name of the user, Note: no spaces are allowed use _
     * @param id The users id
     * @param password The users password
     */
    public void createNewUser(String name, String id, String password){
        if (checkUser(id) == -1){
            user = new UserData(name,id,password);
            dbManager.addUserListEntry(id + "|" + name);
            saveUser();
            System.out.println("New User " + id + " created.");
        }else{
            System.out.println("User " + id + " already exist.");
        }

    }

    /**
     * Checks the given user id with the data base to see if its all ready used
     * @param id The stirng id of the user
     * @return A positve index of the user if found and -1 if the user is not found
     */
    public int checkUser(String id){
        return dbManager.checkUser(id);
    }


    /**
     * Given the correct parameters it logs a user in and loads in their data
     * @param id The users id
     * @param password THe users password
     */
    public void login(String id, String password){
        if (dbManager.checkUser(id) != -1){
            loadUser(id);
            if(!user.checkPassword(password)){
                user = null;
                System.out.println("Login attempt failed, inncorect password");
            }
            System.out.println("You are now logged as " + dbManager.lookUpUserName(id));
        }else{
            System.out.println("User ID not found, please check your spelling");
        }

    }


    /**
     * Saves user data to file
     */
    public void saveUser(){
        try {
            dbManager.serialize(user);
        }catch(Exception e){
            System.out.println("[Model -> saveUser()] Exception caught");
            System.out.println(e);
        }
        dbManager.saveUserList();
    }

    /**
     * Called by Login() this loads the user in to memory
     * @param id The users id
     */
    private void loadUser(String id){
        try {
            user = dbManager.deserialize(id);
        }catch(Exception e){
            System.out.println("[Model -> loadUser()] Exception caught");
            System.out.println(e);
        }
        System.out.println("User \'" + user.getName() + "\" found.");

    }

    /**
     * Deletes the current logged in user if the given password is correct
     * @param password The users password
     */
    public void deleteCurrentUser(String password){
        if (!loggedIn()){
            System.out.println("No user logged in, unable to delete");
        }else{
            if (user.checkPassword(password)) {
                dbManager.deleteFile(user.getId() + ".ser");
                dbManager.removeUserListEntry(user.getId() + "|" + user.getName());
                user = null;
            }
        }

        //Still need to add part to delete files here
    }

    /**
     * A function for test and development, should never be called from the GUI
     */
    public void test(){
        //dbManager.removeUserListEntry(user.getId() + "|" + user.getName());
        dbManager.removeUserListEntry("CW60|Charlie_Walker");
    }

    /**
     * Runs through the database of the current logged in user and updates and recalulateds all their grades
     */
    public void cal(){
        ArrayList<Course> c = user.getPresentCourses();
        for (int i = 0; i < c.size(); i++) {
            c.get(i).setGrade(calculator.getCourseGrade(c.get(i))*100);
        }
        user.setGPA(calculator.getGPAOverall(user.getPastCourses(),user.getPresentCourses()));
    }

    /**
     * Given a grade value form 1-100 it returns a string letter grade
     * (Example 100 = A)
     * @param grade A double value of the users grade
     * @return String of the users current grade
     */
    public String getGradeLetter(double grade){
        if (grade >= 90.0)
            return "A";
        else if (grade >= 80.0)
            return "B";
        else if (grade >= 70.0)
            return "C";
        else if (grade >= 60.0)
            return "F";
        else
            return "U";
    }












    //-----------------------------------------------------------------------------------------------------------------

//    public String getName(){
//        return(user.getName());
//    }

//    public String getId(){ return(
//            user.getId());
//    }

    /**
     * Changes the name of the current logged in user, only call if a user is all ready logged in.
     * @param n The string of the name you wish to change the to
     */
    public void setName(String n){
        user.setName(n);
        dbManager.changeUserListEntry(user.getId() + "|" + user.getName());
        saveUser();
    }

    /**
     * Changes the password given the old password
     * @param oldPS The previous password
     * @param newPS The new password the user wishes the change it too
     */
    public void setPassword(String oldPS, String newPS){
        if (oldPS.equals(newPS)){
            System.out.println("Passwords are the same, choose a diffrent password");
        }else{
            if (loggedIn()){
                if (user.checkPassword(oldPS)){
                    user.setPassword(newPS);
                    System.out.println("Password correctly changed.");
                }else{
                    System.out.println("Old password inncorrect, no changes made.");
                }
            }else {
                System.out.println("No users logged in");
            }
        }
    }

    /**
     * Checks if a user is current logged in a returns a boolean
     * @return Boolean if user is logged in in the model
     */
    public boolean loggedIn(){
        if (user == null){
            return false;
        }
        return true;
    }

    /**
     * Given the string of the curse name it will return that course object
     * @param name String name of the course
     * @return Course object matching the name given if found, if not found a null is returned
     */
    public Course findCourse(String name){
        ArrayList<Course> courses = user.getPresentCourses();
        for (int i = 0; i < courses.size(); i++){
            if (courses.get(i).getName().toLowerCase().equals(name.toLowerCase())){
                return courses.get(i);
            }
        }
        return null;
    }

    /**
     * Given both the course and the category it returns the category object with that name
     * @param courseName Stirng of the course name
     * @param categoryName String of the category name
     * @return The catagory object from the given name, if not found returns a null
     */
    public Category findCategory(String courseName, String categoryName){
        Course course =  findCourse((courseName));
        ArrayList<Category> categories = course.getCategories();
        for (int i = 0; i < categories.size(); i++){
            if (categories.get(i).getName().toLowerCase().equals(categoryName.toLowerCase())){
                return categories.get(i);
            }
        }
        return null;
    }
    /**
     * Given the course, category and assignment it returns the assignment object with that name
     * @param courseName Stirng of the course name
     * @param categoryName String of the category name
     * @param assignmentName String of the assignment name
     * @return The assignment object from the given name, if not found returns a null
     */
    public Assignment findAssignment(String courseName, String categoryName, String assignmentName){
        Category category = findCategory(courseName,categoryName);
        ArrayList<Assignment> assignments = category.getAssignments();
        for (int i = 0; i < assignments.size(); i++){
            if (assignments.get(i).getName().toLowerCase().equals(assignmentName.toLowerCase())){
                return assignments.get(i);
            }
        }
        return null;
    }

    /**
     * Returns a boolean if a given course name is in the users database
     * @param name Stirng of the course name
     * @return Boolean if the object appears in the list
     */
    public boolean checkCourseName(String name){
        ArrayList<Course> courses = user.getPresentCourses();
        for (int i = 0; i < courses.size(); i++){
            if (courses.get(i).getName().toLowerCase().equals(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    /**
     * Returns a boolean if a given catagory name is in the users database
     * @param courseName Stirng of the course name
     * @param categoryName String of the category name
     * @return Boolean if the object appears in the list
     */
    public boolean chcekCatagoryName(String courseName, String categoryName){
        Course course =  findCourse((courseName));
        ArrayList<Category> categories = course.getCategories();
        for (int i = 0; i < categories.size(); i++){
            if (categories.get(i).getName().toLowerCase().equals(categoryName.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    /**
     * Returns a boolean if a given assignment name is in the users database
     * @param courseName Stirng of the course name
     * @param categoryName String of the category name
     * @param assignmentName String of the assignment name
     * @return Boolean if the object appears in the list
     */
    public boolean checkAssignmentName(String courseName, String categoryName, String assignmentName){
        Category category = findCategory(courseName,categoryName);
        ArrayList<Assignment> assignments = category.getAssignments();
        for (int i = 0; i < assignments.size(); i++){
            if (assignments.get(i).getName().toLowerCase().equals(assignmentName.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    /**
     * Given the sting name of the course it deletes it from the data base and every thing under it.
     * @param n Stirng name of the desired object you wish to delete
     */
    public void deleteCourse(String n){
        Course c = findCourse(n);
        user.removeCourse(user.getPresentCourses().indexOf(c));
        System.out.println("Course has been deleted");
    }
    /**
     * Given the sting name of the category it deletes it from the data base and every thing under it.
     * @param course Stirng of the course
     * @param n Stirng name of the desired object you wish to delete
     */
    public void deleteCategory(String course, String n){
        Category c = findCategory(course,n);
        findCourse(course).removeCategory(findCourse(course).getCategories().indexOf(c));
        System.out.println("Catagory has been removed");
    }
    /**
     * Given the sting name of the assignment it deletes it from the data base and every thing under it.
     * @param course Stirng of the course
     * @param catagory Stirng of the catagory
     * @param n Stirng name of the desired object you wish to delete
     */
    public void deleteAssignment(String course, String catagory, String n){
        Assignment a = findAssignment(course,catagory,n);
        findCategory(course,catagory).removeAssignment(findCategory(course,catagory).getAssignments().indexOf(a));
        System.out.println("Assignment has been removed");

    }

    //User setters and getters
    //-----------------------------------------------------------------------------------------------------------------
    //getGPA

    //-----------------------------------------------------------------------------------------------------------------

    /**
     * Logs the user out saves data to file and exit(0)'s the program
     */
    public void safeShutDown(){
        if (loggedIn()){
            saveUser();
            System.exit(0);
        }

    }












}
