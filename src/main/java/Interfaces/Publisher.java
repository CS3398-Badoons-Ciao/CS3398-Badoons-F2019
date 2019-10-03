package Interfaces;

public interface Publisher {
    /**
     * registers a listener for the model
     * @param l Listener to register
     */
    void addListener(Listener l);

    /**
     * notify all listeners of change
     * by calling update() method for
     * all listeners
     */
    void notifyChanged();
}
