# Grade Manager

## Sprint 1 Features
* Jon Pierre
  * Course GUI -> courseScene.java
  * Scene visuals include information about a course. Consists of tables representing categories (e.g. "test", "quiz") where each category table holds Assignments (e.g. "test 1", "test 2"). This scene is used on main application stage when user selects a course to edit.
* Bailey Hubbard
  * Java files ["model.java", "CML.java", "dataBaseManager.java" and parts of "calulator.java"]
  * Model: implication and back end program functionality
  * DataBaseManager: a simple database manager used for loading in files to the model; A temporary use for testing till the full-on database was implemented 
  * CML: A simple but mostly complete command line implication to access the program in a limited form for testing till the GUI had been full implemented
* Charles Walker
  * Title GUI -> TitleScreen.java, TitleScreenController.java, sample.fxml. 
  * A titlescreen is displayed to the user and promts the user to login. Functionality to the backend of our program is implemented to login to a user.
  * CourseOverview GUI's class is created in the same format as a Screen class and is being implemented.
* Rebekah Barber
  * Data persistance -> Input.java, Output.java
  * Classes used to save and load data from and to the app and file
* Alex
  * GPA calculations functionality -> Calculator.java
  * Set up the base functionalities of the model related classes -> Calculator.java, Course.java, Assignment.java, Model.java, School.java, UserData.java

## Sprint 1 Project Status
Base Model is implemented, including student data, operations on that data, and data persistence via class serialization to file. The three basic scene GUI visuals have been implemented: Login, Courses Overview, and Course.  
Next Steps:
* Jon - Finish drop down list for adding a course and GUI changes to reflect model
* Bailey - All model functions have been implemented and most tested via the use of CML and DataBaseManager functions to ensure correct functionality
  * Next Step: Get with GUI team and work on tested model implmentation into the GUI
* Charles - Backend linking with the model and gui is working. Finish adding features to match our backend's functions to the GUI, such as a account creation to the title screen.
* Rebekah - Data saves to file and loads to the app successfully.
  * Next Step: Transition to working on the GUI.
* Alex - Got the GPA calculations set up and Organized file structure of the project
  * Next Step: Help get all of the backend functionality that we worked on in the first sprint into the GUI

## Vision
> Our team is building a desktop application for students to assist them in tracking academic performance.

## Table of contents
* [Description](#description)
* [General Info](#general-info)
* [Technologies](#technologies)
* [How to Run](#how-to-run)
* [Features](#features)

## Description
Welcome to the project. We are students at Texas State University enrolled in CS3398 - Software Engineering course.
Our application is aimed for students of all grade levels who are seeking a user friendly solution for grade management.
This app will allow students to save course grade information and will simplify grade calculation using an intuitive user interface.

## General Info
![Example screenshot](./img/EZ_Gradr.PNG)

## Technologies
* Java JKD 12 - https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html
* XML - https://www.w3.org/XML/
* JavaFX - https://docs.oracle.com/javafx/2/overview/jfxpub-overview.htm
* Derby - https://db.apache.org/derby/
* Gradele v(5.2.1) - https://gradle.org

## How to run
* Option 1: Run through gradle the default way.
	* Open the power shell or CML of choice in the root of the project and type the following commands
	* gradle clean
	* gradle build
	* gradle jar 
	* gradle run
* Option 2: Running it via the option above will put a loading bar and excution time at the bottom of the scrip that is quite buggie and effects the CML excution, this is a work around for that.
	* Open the power shell or CML of choice in the root of the project and type the following commands
	* gradle clean
	* gradle fatJar
	* java -jar EZGrader-V1.0.jar
* Opetion 3: Just running the program.
	* The jar file is all ready built and ready to run all you have to do is follow to steps bellow.
	* Open power shell or another command line interface.
		* To open power shell in the root folder you can either..
			* Shift+right click, click "Open powerShell window here"
			* Alt+f, r
	* Type the following "java -jar EZGrader-V1.0.jar"

## Sprint 1 Goals
* User input of courses, grades, categories, and category weights and Persistant data storage - As a student I would like to create a list of my college classes and courses in those classes and save them to access later to keep my course history.
* Course data is displayed to the user - As a student, I would like to see my grades and assignments cleanly together into different categories.
* Intuitive grade calculation - As a student, I would like a program that calculates various averages for my classes in an easy to use way.
  
  
