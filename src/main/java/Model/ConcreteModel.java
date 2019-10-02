package Model;

import Interfaces.Listener;
import Interfaces.Model;

import java.util.Collection;

public class ConcreteModel implements Model {
    Collection<Listener> listeners;

    @Override
    public void addListener(Listener l) {

    }

    @Override
    public void notifyChanged() {

    }
}
