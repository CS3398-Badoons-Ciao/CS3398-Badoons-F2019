//Using the 3 SOLID principles we seperated this class into its own file because, using the Open Close priciple,
//this class is only responsible for these methods so it reduces clutter and interfere with  other classes.
//And, according to the single responsiblity priciple, it will not need to be changed unless one of these methods need to be worked on.

//Rebekah

package threesolid;

import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.io.*;

class Manager {
 RWorker worker;

 public void Manager() {

 }
 public void setWorker(RWorker w) {
  worker=w;
 }

 public void manage() {
  worker.work();
 }
}
