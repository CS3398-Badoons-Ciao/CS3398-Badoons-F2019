package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.*;

/**
 * Handles items that need to be pulled and placed in files
 * @author Bailey Hubbard
 */
public class DatabaseManager {

    //String UserDataPath = "../../bin/resources/User Files/";
    String UserDataPath = "bin/resources/User Files/";
    private ArrayList<String> userList;


    public DatabaseManager() {
        userList = new ArrayList<String>(Arrays.asList(loadFile("-UserList.txt")));
    }

    /**
     * Loads in the given file from "bin/resources/User Files/ + file name"
     * @param fileName The files name
     * @return String[] of the lines from the txt file
     */
    public String[] loadFile(String fileName){
        //-UserList.txt
        ArrayList<String> data = new ArrayList<String>();
        try {
            File file = new File(UserDataPath + fileName);
            Scanner in = new Scanner(file);

            while(in.hasNextLine()){
                data.add(in.nextLine());
            }

            in.close();
        } catch(IOException e) {
            System.err.println("[DataBaseManager -> LoadFile()]IOException is caught ");
            System.out.println(e);
        }
        return (data.toArray(new String[data.size()]));
    }

    /**
     * Saves contents to a given file at "bin/resources/User Files/ + file name"
     * @param n A String[] of contents to be written to file
     * @param fileName The file name
     */
    public void saveFile(String[] n, String fileName){
        try {
            File file = new File(UserDataPath + fileName);
            BufferedWriter out = new BufferedWriter(new FileWriter(file));

            for(int i = 0; i < n.length; i++){
                out.write(n[i]);
                out.newLine();
            }

            out.close();
        } catch(IOException e) {
            System.err.println("[DataBaseManager -> SaveFile()]IOException is caught ");
            System.out.println(e);
        }
    }

    /**
     * Deserializes a given file at "bin/resources/User Files/ + file name"
     * @param id The users id
     * @return Ann UserData object
     * @throws IOException General io exception
     * @throws ClassNotFoundException If the file is not found
     */
    public UserData deserialize(String id) throws IOException, ClassNotFoundException{
        //Load File
        UserData userData = null;
        try {
            String filename =id + ".ser";
            FileInputStream file = new FileInputStream(UserDataPath + filename);
            ObjectInputStream in = new ObjectInputStream(file);

            userData = (UserData)in.readObject();

            in.close();
            file.close();
        } catch(IOException e) {
            System.err.println("[DataBaseManager -> deserialize()]IOException is caught ");
            System.out.println(e);
        } catch(ClassNotFoundException e) {
            System.out.println("[DataBaseManager -> deserialize()] ClassNotFoundException is caught ]");
            System.out.println(e);
        }
        return userData;
    }

    /**
     * Serializes a given user to file at "bin/resources/User Files/ + file name"
     * @param u The UserData object to be serialized
     * @throws IOException General io exception
     */
    public void serialize(UserData u) throws IOException{
        //Save file
        try {
            String filename =u.getId() + ".ser";
            FileOutputStream file = new FileOutputStream(UserDataPath + filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(u);

            out.close();
            file.close();
        }catch(IOException e) {
            System.err.println("[DataBaseManager -> serialize()]IOException is caught");
            System.out.println(e);
        }

    }

    /**
     * Delets a given file path
     * @param fileName The files name
     */
    public void deleteFile(String fileName){
        System.out.print("Deleting: " + fileName + "...             ");
        try {

            Files.delete(Paths.get(UserDataPath + fileName));
            System.out.println("Deletion successful.");

        } catch(NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch(DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch(IOException e) {
            System.out.println("Invalid permissions.");
        }
    }


    //----------------------------------------------------------------------------------------------------------------

    /**
     * Checks n user with users in the data base to see if they exist
     * @param n A string formated as "UserId | UserName"
     * @return -1 if the user is not found and !=-1 for the index of the user if found
     */
    public int checkUser(String n){
        for (int i = 0; i < userList.size(); i++){
            if (getUserListID(userList.get(i)).equals(n)){
               return i;
           }
        }
        return -1;
    }

    /**
     * Adds a given user to the user list
     * @param n A string formated as "UserId|UserName"
     */
    public void addUserListEntry(String n){
        System.out.println(n);
        if (checkUser(getUserListID(n)) == -1){
            userList.add(n);
        }else {
            System.out.println("That user id is all ready in the system, entry rejected");
        }
    }

    /**
     * Removes a user from a user list
     * @param n A string formated as "UserId|UserName"
     */
    public void removeUserListEntry(String n){
        int holder = checkUser(getUserListID(n));
        if (holder == -1){
            System.out.println("That user is not in the database");
        }else{
            userList.remove(holder);
            saveUserList();
        }
    }

    /**
     * Given a user id it changes the users name
     * @param n A string formated as "UserId|UserName" with the name being the new name to be changed
     */
    public void changeUserListEntry(String n){
        int holder = checkUser(getUserListID(n));
        if (holder != 1){
            userList.set(holder,n);
        }else{
            System.out.println("[DBManager -> changeUserListEntry] user not found");
        }

    }

    /**
     * Given "UserId|UserName" it parses out the users ID
     * @param n The users name
     * @return A string of the users ID
     */
    public String getUserListID(String n){
        //return (userList.get(i).substring(0,userList.get(i).indexOf("|")));
        return (n.substring(0,n.indexOf("|")));
    }

    /**
     * Given "UserId|UserName" it parses out the users name
     * @param n The users id
     * @return A string of the users Name
     */
    public String getUserListName(String n){
        //return (userList.get(i).substring(userList.get(i).indexOf("|"),userList.get(i).length()));
        return (n.substring(n.indexOf("|")+1,n.length()));
    }

    /**
     * Looks up a users id given the name
     * @param n The users name
     * @return A string of the users ID
     */
    public String lookUpUserId(String n){
       for (int i = 0; i < userList.size(); i++){
            if (getUserListName(userList.get(i)).equals(n)){
                return(getUserListID(userList.get(i)));
            }
        }
        return "Null";
    }

    /**
     * Looks up a users Name given the Id
     * @param n The users id
     * @return  A string of the users Name
     */
    public String lookUpUserName(String n){
        for (int i = 0; i < userList.size(); i++){
            if (getUserListID(userList.get(i)).equals(n)){
                return(getUserListName(userList.get(i)));
            }
        }
        return "Null";
    }

    /**
     * Saves the users list to file
     */
    public void saveUserList(){
        saveFile(userList.toArray(new String[userList.size()]),"-UserList.txt");
    }












}
