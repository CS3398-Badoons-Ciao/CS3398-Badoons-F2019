//Using the 3 SOLID principles we seperated this class into its own file because, using the Open Close priciple,
//this class is only responsible for these methods and no others.

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
