package Model;

import Interfaces.Listener;
import Interfaces.Publisher;

import java.util.Collection;

public class ConcreteModel implements Publisher {
    Collection<Listener> listeners;

    @Override
    public void addListener(Listener l) {

    }

    @Override
    public void notifyChanged() {

    }
}
