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