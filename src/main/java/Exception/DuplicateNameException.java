package Exception;

public class DuplicateNameException extends Throwable {
    public final String title = "User Error";
    public final String header = "Duplicate Name Exception";
    public final String duplicateName;

    public DuplicateNameException(String s) {
        duplicateName = s;
    }

    public String toString() {
        return "Names must be unique: '" + duplicateName + "'" + " already exists.";
    }
}
