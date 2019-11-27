package Model;

public class ListObject {
    private Assignment assignment;
    private boolean isPastDue;
    private boolean isDone;

    public ListObject(Assignment assignment){
        this.assignment = assignment;
    }

    public Assignment getAssignment(){
        return assignment;
    }

    public boolean isPastDue() {
        return isPastDue;
    }

    public void setPastDue(boolean pastDue) {
        isPastDue = pastDue;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
