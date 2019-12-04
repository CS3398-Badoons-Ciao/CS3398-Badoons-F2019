# Grade Manager

## Vision
> Our team built a desktop application for students to assist them in tracking academic performance.

## Description
Welcome to the project. We are students who took CS 3398 - Software Engineering at Texas State University.
Our application is aimed for students of all grade levels who are seeking a user friendly solution for grade management.
This app allows students to save course information and simplifies grade calculation using a friendly user interface.

## Table of contents
* [Vision](#Vision)
* [Description](#description)
* [Sprint 3 Project Status](#Sprint-3-Project-Status)
* [Features](#Sprint-3-Features)
* [Technologies](#technologies)
* [How to Run](#how-to-run)


## Sprint 3 Project Status
Model is implemented, including student data, operations on that data, and data persistence via class serialization to file. User can export data to Excel. Log-in, Account Creation, Main Menu, Options Menu, Courses Overview, and Course Scenes complete. Assignment grades, Category w/ Weight grade, Course grade, GPA w/ Credit Hours display and update correctly. UI/UX is improved - Scenes are centered, resize correctly, and have improved structure. User can update primary and secondary background colors using Color Picker. 
Next Steps:
* Jon - To Do list controller, To Do list scene structure fix
* Bailey - Added logging files and frame workd to more in depth logs as needed
  * Next Step: Add more detailed log messages and impment the cml into a gui window
* Charles - Backend linking with the model and gui is working. Finish adding features to match our backend's functions to the GUI, such as a account creation to the title screen.
* Rebekah -  Continue to clean up my to do list and options scenes, and implement some new features.
* Alex - Created menu scene, added help option in menu bar, added tooltips to important elements, and fixed issues w/ excel exporting
  * Next Step: Add support for the user to input file path for the exported excel file

## Sprint 3 Features
* Jon Pierre
  * Updated layout for Scenes. Scenes centered and resize correctly 
  	* Login.FXML, AccountCreation.FXML, Menu.java, OptionsScene.FXML, CourseOverview.FXML, CourseScene.java
  * Added primary/secondary background colors with user ColorPicker
  	* SceneStyle.java, OptionsScene.java, OptionsSceneController.java
  * Completed Course Grade/Category Grade/GPA/Credit Hours
  	* CourseScene.java, CategoryTable.java, CourseOverviewController.java
  * Added In-Focus/Out-Focus TextField Style for UI/UX
  	* CourseScene.java, CategoryTable.java
* Bailey Hubbard
  * Model and back end -> Java files ["model.java", "CML.java", "dataBaseManager.java" and parts of "calulator.java"]
  * Model: implication and back end program functionality as well as logging
  * DataBaseManager: a simple database manager used for loading in files to the model; A temporary use for testing till the full-on database was implemented 
  * CML: A simple but mostly complete command line implication to access the program in a limited form for testing till the GUI had been full implemented
* Charles Walker
  * Title GUI -> TitleScreen.java, TitleScreenController.java, sample.fxml. 
  * A titlescreen is displayed to the user and promts the user to login. Functionality to the backend of our program is implemented to login to a user.
  * CourseOverview GUI's class is created in the same format as a Screen class and is being implemented.
  * Added a GPA label to the Courseoverview class
  * Tidyed up the scene transitions by starting in the Menu Screen. (Created a back button to move back)
* Rebekah Barber
  * To Do List Scene -> ListScene.java, ListSceneController.java, ListScene.fxml
  * Options Scene -> OptionsScene.java, OptionsSceneController.java, OptionsScene.fxml
  * Created the pages to view the to do list and the options menu.
* Alex
  * Created Menu scene -> Menu.java
  * Added tooltips -> Menu.java, CourseScene.java, CourseOverviewController.java
  * Added help option in file menu -> Menu.java, CourseScene.java, CourseOverviewController.java

## Technologies
* Java- https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html
* JavaFX - https://docs.oracle.com/javafx/2/overview/jfxpub-overview.htm
* Gradle v(5.2.1) - https://gradle.org

## How to run
* Option 1: Run through gradle the default way.
	* gradle clean
	* gradle build
	* gradle jar 
	* gradle run
* Option 2: Running it via the option above will put a loading bar and excution time at the bottom of the scrip that is quite buggie and effects the CML excution, this is a work around for that.
	* Open the power shell or CML of choice in the root of the project and type the following commands
	* gradle clean
	* gradle fatJar
	* java -jar EZGrader-V1.0.jar
* Option 3: Just running the program.
	* The jar file is all ready built and ready to run all you have to do is follow to steps bellow.
	* Open power shell or another command line interface.
		* To open power shell in the root folder you can either..
			* Shift+right click, click "Open powerShell window here"
			* Alt+f, r
	* Type the following "java -jar EZGrader-V1.0.jar"

  
  
