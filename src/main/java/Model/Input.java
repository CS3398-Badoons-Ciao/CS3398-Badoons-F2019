//Input from file to application
package Model; //Maybe needs to be put into a different package?
import java.io.*;

public class Input {
    String file = "UserFile.dat";
    FileInputStream fis;
    ObjectInputStream in;

    public void inToApp() {
        try {
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            in.readObject(); //May need to be edited
            in.close();
            fis.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


