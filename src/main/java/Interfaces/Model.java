package Interfaces;

public interface Model {
    /**
     * registers a listener for the model
     * @param l Listener to register
     */
    public void addListener(Listener l);

    /**
     * notify all listeners of change
     * by calling update() method for
     * all listeners
     */
    public void notifyChanged();
}
