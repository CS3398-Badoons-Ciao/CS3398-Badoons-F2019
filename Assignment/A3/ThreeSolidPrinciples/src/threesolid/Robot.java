// Jon Pierre
// Single-Responsibility: Robot class will not be changed for more than one reason (to change work() - in this example.
// Open-Closed: Open to change by deriving from Robot and overriding work(), without modification.
// Interface Segregation: The original IWorker interface was split to accomodate this Robot class,
//                        as a Robot does not need to eat(). Thus, RWorker interface was segregated.

package threesolid;

import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import java.io.*;


class Robot implements RWorker{
 public void work() {
  // ....working
 }

}
