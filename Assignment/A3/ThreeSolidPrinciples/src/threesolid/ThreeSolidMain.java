package threesolid;

import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.io.*;

import threesolid.IWorker;
import threesolid.RWorker;

/*This is the main file where the program runs from
* All the extra classes and interfaces have been removed and separated into their files to allow multi able users to work on the program at the same time.
* This has been done to implement the following principle...
*  -Open Closed principle: This principle does not apply as nothing would be extending the main. To see this Principle in action refer to the interface classes.
*  -Single Responsibility: By separating these files one user can reasonably manage the one class thus making the user responsible for that program alone. This all then follows this principle.
*  -Interface segregation: This principle was not used in the main class because the main does not implement any interfaces, to see this in action refer to the interface class in IWorker.java
*
* -Bailey Hubbard
*/



public class ThreeSolidMain
{   

   public static Manager tsManager = new Manager();

   // The entry main() method
   public static void main(String[] args) 
   {
 
      try 
      {
         System.out.format("Starting ... \n");               
      } 
      catch (Exception main_except)
      {
         main_except.printStackTrace();
      }

            try 
      {
         System.out.format("Stopping ... \n");               
      } 
      catch (Exception main_except)
      {
         main_except.printStackTrace();
      }

      System.exit(0);

   }
 }

