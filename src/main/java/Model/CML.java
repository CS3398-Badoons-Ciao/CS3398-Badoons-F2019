package Model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Interface to the command line
 * @author Bailey H.
 */
public class CML{

    Model model;
    Scanner console;
    Boolean running = false;

    /**
     * Constructor of the CML class
     * @param c The scanner from main
     * @param model A copy of model to be able to access its methods
     */
    public CML(Scanner c, Model model){
        this.model = model;
        System.out.println("Starting up command line interface V0.2");
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
        n.add("Create         *name *id *password        Creates a new user. Note: Spcaes are not allowed in the user name, use a \'_\' instead.");
        n.add("Delete         *password                  Deletes the current logged in user.");
        n.add("Login          *Id   *password            Login to an existing user");
        n.add("ChangeName     *name                      Change the users on screen name");
        n.add("ChangePasswrod *old ps *new ps            Changes the users password");
        n.add("");
        n.add("Dashboard                                 Displays the users dashboard");
        n.add("");
        n.add("addCourse      *name *school              Creates a new course objects and adds it to the currently logged in user.");
        n.add("addcategory    *CourseName *name *weight  Creates a new category under the given course");
        n.add("addAssginment  *courseName *CatagoryName *name *grade *potential    Sets a users grade");
        n.add("");
        n.add("Test                                      Used for testing purpouses.");
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
                case "help"           : help(); break;
                case "exit"           : stop(); break;
                case "create"         : createNewUser(n); break;
                case "delete"         : deleteUser(n); break;
                case "changename"     : changeName(n); break;
                case "changepassword" : changePassword(n); break;
                case "login"          : login(n); break;

                case "dashboard"      :  displayDashboard(); break;

                case "addcourse"      : addCourse(n); break;
                case "addcategory"    : addCategory(n); break;
                case "addassginments" : addAssginments(n); break;


                case "test"           : test(); break;
                default               : System.out.println("Command not reconized"); break;
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
        if (checkDefaults(n,4)){
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
     * Displays the current users dash board and all needed info
     */
    public void displayDashboard() {
        if (model.loggedIn()){
            ArrayList<String> n = new ArrayList<String>();
            n.add("Name: " + model.user.getName());
            n.add("ID: " + model.user.getId());
            n.add("-");
            n.add(model.user.getPresentCourses().size() + " active corses detected");
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

            System.out.println("\nCorse: " + course.getName() + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tGrade: " + course.getGrade());
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("| Credit Hours: " + course.getCreditHours() + "\tSchool: " + course.getSchool().getName());
            System.out.println("-------------------------------------------------------------------------------");
            for (int j = 0; j < numOfCatagories; j++){
                Category category = course.getCategories().get(j);
                int numOfAssignments = category.getAssignments().size();

                System.out.println("Catagory: " + category.getName() + "\t\t\t\t\t\t\t\t\t\tCatagory weight: " + category.getWeight());
                for (int k = 0; k < numOfAssignments; k++){
                    Assignment assignment = category.getAssignments().get(k);
                    System.out.println("\t\t*" + assignment.getName() + ": " + assignment.getCurrentGrade() + "/" + assignment.getPotentialGrade());
                }

            }
        }
    }

    public void addCourse(String[] n){
        if (checkDefaults(n,3)){
            Course course = new Course(n[1],new School(n[2]));
            model.user.addPresentCourse(course);
            System.out.println("Course \"" + n[1] + "\" has been added.");
        }
    }

    public void addCategory(String[] n){
        if (checkDefaults(n,4)){
            Course course = model.findCourse(n[1]);
            if (checkCourse(course)){
                Category category = new Category(n[2],Double.parseDouble(n[3]));
                course.addCategory(category);
                System.out.println("Category \"" + n[1] + "\" has been added to Course \"" + n[2]);
            }
        }
    }

    public void addAssginments(String[] n){
        if(checkDefaults(n, 6)){
            Course course = model.findCourse(n[1]);
            Category category = model.findCategory(n[1],n[2]);
            if (checkCourse(course) && checkCatagory(category)){
                Assignment assignment = new Assignment(n[3],Double.parseDouble(n[4]),Double.parseDouble(n[5]));
                category.addAssignment(assignment);
                System.out.println("Assignment \"" + n[3] + "\" has been added to [" + course.getName() + "->" + category.getName() + "]");
            }
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
        //┌ ─ ┐ │ └ ─ ┘ ├ ┤
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
        runCommand("Login bkh60 500946");
        runCommand("addCourse Math TxState");
        runCommand("addCategory Math Dailey_Assignments 0.3");
        runCommand("addAssginments Math Dailey_Assignments Quiz_1 20 30");
        runCommand("addAssginments Math Dailey_Assignments Quiz_2 5 30");
        runCommand("addCategory Math Test 0.7");
        runCommand("addAssginments Math Test Test_1 70 100");


        runCommand("dashboard");


    }
}
