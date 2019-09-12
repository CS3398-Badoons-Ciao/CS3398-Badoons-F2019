package threesolid;

import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.io.*;

// Using the 3 principles, this interface class was sectioned off because of the following.
// 1. The open and closed principle is used by not editing the actual interfaces itself, but by 
//    splitting the interfaces into two to allow more diversity to classes when creating extensions.
//
// 2. The single responsibility principle seperates this interface class into it's own to avoid
//    clutter and allow for extensions in the future. It also makes this class file only relate to the 
//    methods inside the interfaces.
// 
// 3. The interface segregration principle applies to our changes because it splits one interface
//    into two that allows multiple classes with unique dependicies to function with different interfaces.
//    Seperating the interfaces also allows changes to one to be easier in the future.

interface IWorker {
 public void eat();
} 

interface RWorker {
  public void work();
}