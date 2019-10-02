//Output from application to file

//Depending on how the save system is implemented, the constructor may need to be removed
//and outToFile my need to have UserData user as its argument.
package Model; // Package may change.
import java.io.*;

public class Output {
    private String file = "UserFile.dat";
    private FileOutputStream fos;
    private ObjectOutputStream out;
    private UserData user;

    public Output(UserData user){ 
        this.user = user;
    }

    public void outToFile() {
        try {
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(user);
            out.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

