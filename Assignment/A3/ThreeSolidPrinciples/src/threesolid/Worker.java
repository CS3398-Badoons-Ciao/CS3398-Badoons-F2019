package threesolid;

import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.io.*;

//Alex Morman
//Open / Close Principle: We seperated the Worker class from the IWorker and RWorker 
//class so that changes to the Worker class would not affect the interfaces it implements

//Single Responsibility Principle: By putting Worker class in its own file, the only reason
//this file has to change is if something in the Worker class needs to change

class Worker implements IWorker, RWorker{
 public void work() {
  // ....working
 }

 public void eat() {
  //.... eating in launch break
 }
}
