package threesolid;

import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.io.*;

//Alex Morman
//Open / Close Principle: We seperated the SuperWorker class from the IWorker and RWorker 
//class so that changes to the SuperWorker class would not affect the interfaces it implements

//Single Responsibility Principle: By putting SuperWorker class in its own file, the only reason
//this file has to change is if something in the SuperWorker class needs to change

class SuperWorker implements IWorker, RWorker{
 public void work() {
  //.... working much more
 }

 public void eat() {
  //.... eating in launch break
 }
}
