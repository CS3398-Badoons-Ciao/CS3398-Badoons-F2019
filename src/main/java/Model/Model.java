package Model;


/*
To do list:
Finish deleteCurrentUser() needs to be able to delete files
 */

import java.util.*;
/**
 Model: The back end of EZGrader
 @author Bailey H.
 */
public class Model
{
    private Calculator calculator;
    private DatabaseManager dbManager;
    private UserData user = null;



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
        user = new UserData(name,id,password);
        dbManager.addUserListEntry(id + "|" + name);
        saveUser();
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
    private void saveUser(){
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












    //-----------------------------------------------------------------------------------------------------------------

    public String getName(){
        return(user.getName());
    }

    public String getId(){ return(
            user.getId());
    }

    public void setName(String n){
        user.setName(n);
        dbManager.changeUserListEntry(user.getId() + "|" + user.getName());
        saveUser();
    }

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

    public boolean loggedIn(){
        if (user == null){
            return false;
        }
        return true;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void update(){

    }

    public void safeShutDown(){
        saveUser();
        System.exit(0);
    }












}
