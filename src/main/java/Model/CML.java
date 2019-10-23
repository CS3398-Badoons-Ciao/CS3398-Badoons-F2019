package Model;

import GUI.CourseScene;
import com.sun.glass.ui.Application;
import main.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Interface to the command line
 * @author Bailey H.
 */
public class CML {

    Model model;
    Scanner console;
    Boolean running = false;
    Boolean mainCalled = false;

    String course_s = null;
    String catagory_s = null;


    /**
     * Constructor of the CML class
     * @param c The scanner from main
     * @param model A copy of model to be able to access its methods
     */
    public CML(Scanner c, Model model){
        this.model = model;
        System.out.println("Starting up command line interface V1.0");
        System.out.println("Type \"help\" for help menu");
        System.out.println("----------------------------------------------------");
//        System.out.println("Current functionality:");
//        System.out.println("* User account set up, login, and profile changes");
//        System.out.println("Things still to be added:");
//        System.out.println("* Grade viewing and entry");
//        System.out.println("----------------------------------------------------");
        console = c;

        running = true;
        loop();

    }

    /**
     * Prints out a list of avaliable help commands
     */
    public void help(){
        ArrayList<String> n = new ArrayList<String>();
        n.add("List of help commands");
        n.add("-");
        n.add("Exit                                      Stops the program.");
        n.add("Help                                      List the help menu for the user.");
        n.add("");
        n.add("Create           *name *id *password        Creates a new user. Note: Spcaes are not allowed in the user name, use a \'_\' instead.");
        n.add("DeleteUser       *password                  Deletes the current logged in user.");
        n.add("Login            *Id   *password            Login to an existing user");
        n.add("Logout                                     Logs out the current user and saves thier data");
        n.add("ChangeName       *name                      Change the users on screen name");
        n.add("ChangePasswrod   *old ps *new ps            Changes the users password");
        n.add("ls                                          List a of dirs under current directory");
        n.add("cd               *path                      A path to open a directory");
        n.add("");
        n.add("Dashboard                                   Displays the users dashboard");
        n.add("");
        n.add("Add              *Argumnets                 This is a smart add the adds to correct object base on what directory your on.");
        n.add("Delete           *Arguments                 This is a smart delete that deletes the correct object base on what directory your on.");
        n.add("Edit             *Arguments                 This is a short cut that delets and replaces the old object in one command.");
        n.add("AddCourse        *name *school              Command not available to user, only showed to list parameters");
        n.add("Addcategory      *name *weight              Command not available to user, only showed to list parameters");
        n.add("AddAssginment    *name *grade *potential    Command not available to user, only showed to list parameters");
        n.add("DeleteCourse     *name                      Command not available to user, only showed to list parameters");
        n.add("DeleteCategory   *name                      Command not available to user, only showed to list parameters");
        n.add("DeleteAssignment *name                      Command not available to user, only showed to list parameters");
        n.add("");
        n.add("Test                                        Used for testing purpouses.");
//        n.add("ShowGUI          *CourseName                Shows the given course in the gui");
//        n.add("ShowTitleScreen                             Shows the login title screen");
        n.add("-");
        n.add("*'s are paramaters that the command can take in, to insure that it works correctly you must have a space between paramaters.");

        printBox(n.toArray(new String[n.size()]),0);
    }

    /**
     * The main loop that confines asking the user for commands unless stopped
     */
    public void loop(){
        while (running){
            if(console.hasNextLine()) {
                decode(takeParameters(console.nextLine()));
            }
        }
    }

    /**
     * Takes in the command and returns the parameters of the command as a String[]
     * @param n The listed command
     * @return A String[] of command parameters
     */
    public String[] takeParameters(String n){
        return(n.split("\\s+", 0));
    }

    /**
     * Runs a given command from with in the command line class, used for testing fuctions quickly
     * @param n The string usally passed to the system via Systm.in
     */
    public void runCommand(String n){
        System.err.println(n);
        decode(takeParameters(n));
    }

    /**
     * Decodes the given command and calls the corresponding faction
     * @param n The String[] of the command and its parameters
     */
    public void decode(String[] n){
        try {
            switch(n[0].toLowerCase()){
                case "help"             : help(); break;
                case "exit"             : stop(); break;
                case "create"           : createNewUser(n); break;
                case "deleteuser"           : deleteUser(n); break;
                case "changename"       : changeName(n); break;
                case "changepassword"   : changePassword(n); break;
                case "login"            : login(n); break;
                case "logout"           : logout(n); break;
                case "save"             : model.saveUser(); break;

                case "ls"               : ls(n); break;
                case "cd"               : cd(n); break;

                case "dashboard"        :  displayDashboard(); break;

//                case "addcourse"        : addCourse(n); break;
//                case "addcategory"      : addCategory(n); break;
//                case "addassignment"   : addAssginments(n); break;
//                case "deletecourse"     : deleteCourse(n); break;
//                case "deletecategory"   : deleteCategory(n); break;
//                case "deleteassignment" : deleteAssiginment(n); break;

                case "add"              : add(n); break;
                case "delete"           : delete(n); break;
                case "edit"             : edit(n); break;


                case "test"             : test(); break;
//                case "showgui"          : showGUI(n); break;
//                case "showtitlescreen"  : showTitlescreen(n); break;
                default                 : System.out.println("Command not reconized"); break;
            }
        }catch (Exception e){
            //System.err.println(e.printStackTrace());
            e.printStackTrace();
        }
    }

    /**
     * Stops the command line and exit(0) the program
     */
    public void stop(){
        System.out.println("Quiting Command line...");
        running = false;
        model.safeShutDown();
    }

    /**
     * Creates a new user
     * @param n The String[] *name *id *password
     */
    public void createNewUser(String[] n){
        if (n.length != 4){
            System.out.println("Invalid Number of arguments");
        }else{
            model.createNewUser(n[1],n[2],n[3]);
        }
    }

    /**
     * Deletes the current user given the correct password
     * @param n The String[] *command *password
     */
    public void deleteUser(String[] n){
        if (checkDefaults(n,2)){
            model.deleteCurrentUser(n[1]);
        }
    }

    /**
     * Changes the name of the current user
     * @param n The String[] *command *new name
     */
    public void changeName(String[] n){
        if (checkDefaults(n,2)){
            model.setName(n[1]);
        }
    }

    /**
     * Changes the users currnt password
     * @param n The String[] *command *old passwrod *new password
     */
    public void changePassword(String [] n){
        if (checkDefaults(n,3)){
            model.setPassword(n[1],n[2]);
        }
    }

    /**
     * Logs in the user given the correct credential
     * @param n The String[] *command *id *password
     */
    public void login(String[] n){
        if (n.length != 3){
            System.out.println("Invalid Number of arguments");
        }else{
            model.login(n[1],n[2]);
        }
    }

    /**
     * Logs the current user out and saves their data to file
     * @param n The String[] of parameters, no aruments should be passed
     */
    public void logout(String[] n){
        if(checkDefaults(n,1)){
            model.saveUser();
            model.user = null;
            System.out.println("User has been logged out and data has been saved");
        }
    }

    /**
     * Works like a linx ls command to truvers directories
     * @param n Has no function, is only given by default
     */
    public void ls(String[] n){
        if(checkDefaults(n,1)){
            if (course_s == null){
                ArrayList<Course> c = model.user.getPresentCourses();
                System.out.print(">");
                for (int i = 0; i < c.size(); i++){
                    System.out.print(c.get(i).getName());
                    if (i != 0 || i != c.size()){
                        System.out.print(", ");
                    }
                }
                System.out.println("");
            }else if (catagory_s == null){
                ArrayList<Category> c = model.findCourse(course_s).getCategories();
                System.out.print(">");
                for (int i = 0; i < c.size(); i++){
                    System.out.print(c.get(i).getName());
                    if (i != 0 || i != c.size()){
                        System.out.print(", ");
                    }
                }
                System.out.println("");
            }else{
                ArrayList<Assignment> c = model.findCategory(course_s,catagory_s).getAssignments();
                System.out.print(">");
                for (int i = 0; i < c.size(); i++){
                    System.out.print(c.get(i).getName());
                    if (i != 0 || i != c.size()){
                        System.out.print(", ");
                    }
                }
                System.out.println("");
            }
        }
    }

    /**
     * Works like a linx cd command to truvers directories
     * @param n The String[] *file path
     */
    public void cd(String[] n){
        if (model.loggedIn()){
            if (n[1].equals("root")){
                course_s = null;
                catagory_s = null;
                System.out.println("Moving to [root]");
            }else{
                if (course_s == null){
                    if (n.length == 2){
                        if (n[1].equals("..")){
                            System.out.println("Already at root");
                        }else{
                            if (model.checkCourseName(n[1])){
                                course_s = n[1];
                                System.out.println("Moving to [" + course_s + "]");
                            }else{
                                System.out.println("Course not found");
                            }
                        }
                    }else if (n.length == 3){
                        if (model.checkCourseName(n[1])){
                            course_s = n[1];
                            if (model.chcekCatagoryName(course_s,n[2])){
                                catagory_s = n[2];
                                System.out.println("Moving to [" + course_s + "/" + catagory_s + "]");
                            }else{
                                System.out.println("Catagory not found");
                                course_s = null;
                            }
                        }else{
                            System.out.println("Course not found");
                        }
                    }else{
                        System.out.println("Inncorect number of arugments ");
                    }
                }else if (catagory_s == null){
                    if (n[1].equals("..")){
                        course_s = null;
                        System.out.println("Moving up a level to [root]");
                    }else if (model.chcekCatagoryName(course_s,n[1])){
                        catagory_s = n[1];
                        System.out.println("Moving to [" + course_s + "/" + catagory_s + "]");
                    }else{
                        System.out.println("Catagory not found");
                    }
                }else{
                    if (n[1].equals("..")){
                        catagory_s = null;
                        System.out.println("Moving up a level to [" + course_s + "]");
                    }else{
                        System.out.println("You are at the lowest directory");
                    }

                }
            }

        }
    }

    /**
     * Displays the current users dash board and all needed info
     */
    public void displayDashboard() {
        if (model.loggedIn()){
            model.cal();
            ArrayList<String> n = new ArrayList<String>();
            n.add("Name: " + model.user.getName());
            n.add("ID: " + model.user.getId());
            n.add("-");
            n.add(model.user.getPresentCourses().size() + " active courses detected");
            //n.add("Grades and stuff would go down here");
            printBox(n.toArray(new String[n.size()]),0);

            displayGrades(n);
        }else{
            System.out.println("No user logged in");
        }

    }

    public void displayGrades(ArrayList<String> n){
        int numOfCorses = model.user.getPresentCourses().size();
        for (int i = 0; i < numOfCorses; i++){
            Course course = model.user.getPresentCourses().get(i);
            int numOfCatagories = course.getCategories().size();

            System.out.println("\n");
            System.out.println("\nCorse: " + course.getName() + "\t\t\tGrade: " + String.format("%.2f", course.getGrade()) + " (" + getGradeLetter(course.getGrade()) + ")");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("| Credit Hours: " + course.getCreditHours() + "\tSchool: " + course.getSchool().getName());
            System.out.println("-------------------------------------------------------------------------------");
            for (int j = 0; j < numOfCatagories; j++){
                Category category = course.getCategories().get(j);
                int numOfAssignments = category.getAssignments().size();

                System.out.println("Catagory: " + category.getName() + "(Weight: " + category.getWeight() + ")");
                for (int k = 0; k < numOfAssignments; k++){
                    Assignment assignment = category.getAssignments().get(k);
                    System.out.println("\t\t*" + assignment.getName() + ": " + assignment.getCurrentGrade() + "/" + assignment.getPotentialGrade());
                }

            }
        }
    }

    public void addCourse(String[] n){
        if (checkDefaults(n,3)) {
            if (course_s == null) {
                if (checkDefaults(n, 3)) {
                    Course course = new Course(n[1], new School(n[2]));
                    model.user.addPresentCourse(course);
                    System.out.println("Course \"" + n[1] + "\" has been added.");
                }
            } else {
                System.out.println("Please travel to the root to add a course.");
            }
        }

    }

    public void addCategory(String[] n){
        if (checkDefaults(n,3)){
            if (course_s != null){
                Course course = model.findCourse(course_s);
                if (checkCourse(course)){
                    Category category = new Category(n[1],Double.parseDouble(n[2]));
                    course.addCategory(category);
                    System.out.println("Category \"" + n[1] + "\" has been added to Course \"" + course_s);
                }
            }else{
                System.out.println("You must be in a Course to run this command");
            }

        }
    }

    public void addAssginments(String[] n){
        if(checkDefaults(n, 4)){
            if (course_s != null){
                if (catagory_s != null){
                    Course course = model.findCourse(course_s);
                    Category category = model.findCategory(course_s,catagory_s);
                    if (checkCourse(course) && checkCatagory(category)){
                        Assignment assignment = new Assignment(n[1],Double.parseDouble(n[2]),Double.parseDouble(n[3]));
                        category.addAssignment(assignment);
                        System.out.println("Assignment \"" + n[1] + "\" has been added to [" + course.getName() + "->" + category.getName() + "]");
                    }
                }else{
                    System.out.println("You must be under a catagory to run this command");
                }
            }else{
                System.out.println("You must be under a catagory to run this command");
            }
        }
    }

    public void deleteCourse(String[] n){
        if (model.loggedIn()){
            if (model.checkCourseName(n[1])){
                model.deleteCourse(n[1]);
            }
        }
    }

    public void deleteCategory(String[] n){
        if (model.loggedIn()){
            if (model.chcekCatagoryName(course_s,n[1])){
                model.deleteCategory(course_s,n[1]);
            }
        }
    }

    public void deleteAssiginment(String[] n){
        if (model.loggedIn()){
            if (model.checkAssignmentName(course_s,catagory_s,n[1])){
                model.deleteAssiginment(course_s,catagory_s,n[1]);
            }
        }
    }

    public void add(String[] n){
        if (course_s == null){
            addCourse(n);
        }else if(catagory_s == null){
            addCategory(n);
        }else{
            addAssginments(n);
        }
    }

    public void delete(String[] n){
        if (course_s == null){
            deleteCourse(n);
        }else if(catagory_s == null){
            deleteCategory(n);
        }else{
            deleteAssiginment(n);
        }
    }

    public void edit(String[] n){
        if (course_s == null){
            deleteCourse(n);
            addCourse(n);
        }else if(catagory_s == null){
            deleteCategory(n);
            addCategory(n);
        }else{
            deleteAssiginment(n);
            addAssginments(n);
        }
    }


    public boolean checkCourse(Course c){
        if (c == null){
            System.out.println("A course with that name was not found.");
            return false;
        }
        return true;
    }

    public boolean checkCatagory(Category c){
        if (c == null){
            System.out.println("A course with that name was not found.");
            return false;
        }
        return true;
    }


    public void defaultFunction(String[] n){
        if (checkDefaults(n,3)){
            //Your code goes here
        }
    }

    public boolean checkDefaults(String[] n, int numOfArguments){
        if (model.loggedIn()){
            if (n.length != numOfArguments){
                System.out.println("Invalid Number of arguments");
                return false;
            }else{
                return true;
            }
        }else {
            System.out.println("No user logged in");
            return false;
        }
    }





    //CML special functions
    //----------------------------------------------------------------------------------------------------------------

    /**
     * A decorator function used to draw boxes around text in a CML environment
     * @param n A string[] of text to be decorated
     * @param fixedLength -1 for default, greater than or equal to 0 for a specifed width of the box
     */
    public void printBox(String[] n, int fixedLength){
        //
        //https://en.wikipedia.org/wiki/Box-drawing_character
        int longest = 0;
        int padding = 5;
        int defalut = 50;
        if (fixedLength == -1){
            fixedLength = defalut;
        }
        for (int i = 0; i < n.length; i++){
            if (n[i].length() > longest){
                longest = n[i].length()/*cLength(n[i])*/;
            }
        }
        longest += padding;
        if (fixedLength != 0){
            longest = fixedLength;
        }
        System.out.print("*");
        for (int i = 0; i < longest; i++){
            System.out.print("-");
        }
        System.out.println("*");
        //---------------------------------
        for (int i = 0; i < n.length; i++){
            if (n[i].equals("-")){
                System.out.print("|");
                for (int a = 0; a < longest; a++){
                    System.out.print("-");
                }
                System.out.println("|");
            }else {
                System.out.print("|");

                if (fixedLength != 0) {
                    try {
                        System.out.print(n[i].substring(0, longest));
                    } catch (Exception e) {
                        System.out.print(n[i]);
                    }
                } else {
                    System.out.print(n[i]);
                }
                for (int j = 0; j < (longest - n[i].length()/*cLength(n[i])*/); j++) {
                    System.out.print(" ");
                }
                System.out.println("|");
            }
        }
        //----------------------------------
        System.out.print("*");
        for (int i = 0; i < longest; i++){
            System.out.print("-");
        }
        System.out.println("*");
    }

    /**
     * A test function only to be called by the CML for devolment
     */
    public void test(){
        System.out.println("Starting test Function");
        //model.test();
        //runCommand("Login bkh60 500946");
        runCommand("addCourse Math TxState");
        runCommand("cd Math");
        runCommand("addCategory Dailey_Assignments 0.3");
        runCommand("cd Dailey_Assignments");
        runCommand("addAssginments Quiz_1 30 30");
        runCommand("addAssginments Quiz_2 30 30");
        runCommand("cd ..");
        runCommand("addCategory Test 0.7");
        runCommand("cd Test");
        runCommand("addAssginments Test_1 100 100");

        runCommand("cd root");
        runCommand("addCourse Science TxState");
        runCommand("cd Science");
        runCommand("addCategory Dailey_Assignments 0.3");
        runCommand("cd Dailey_Assignments");
        runCommand("addAssginments Quiz_1 20 30");
        runCommand("addAssginments Quiz_2 5 30");
        runCommand("cd ..");
        runCommand("addCategory Test 0.7");
        runCommand("cd Test");
        runCommand("addAssginments Test_1 70 100");


        runCommand("dashboard");


    }

//    public void showGUI(String[] n){
//        if (checkDefaults(n,2)){
//            if (!mainCalled){
//                if (model.checkCourseName(n[1])){
//                    System.out.println("Launching Gui with course: " + n[1]);
//                    Course c = model.findCourse(n[1]);
//                    mainCalled = true;
//                    //main.setCourse(c);
//                    //main.setStartMode(2);
//                    //main.launchGUI(args);
//                }else{
//                    System.out.println("Course not found");
//                }
//            }else{
//                System.out.println("These functions can only be called once.");
//            }
//
//        }
//    }
//
//    public void showTitlescreen(String[] n){
//        if(!mainCalled){
//            System.out.println("Displaying title screen");
//            mainCalled = true;
//            main.setStartMode(1);
//            main.launchGUI(args);
//        }else{
//            System.out.println("These functions can only be called once.");
//        }
//
//    }

    /**
     * Given the grade value it returns a String of the grade in letter form
     * @param grade The grade of the Course
     * @return A string char of the grade value
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
}
