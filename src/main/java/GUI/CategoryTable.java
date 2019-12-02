package GUI;

import Interfaces.AssignmentInterface;
import Interfaces.CategoryCalculatorInterface;
import Interfaces.CategoryInterface;
import Model.Course;
import Exception.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.converter.DoubleStringConverter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * a Node for displaying a Category
 */
public class CategoryTable extends TableView<AssignmentInterface> {
        /** the Course in which this CategoryTable belongs */
        Course course;

        /** the Category to represent */
        CategoryInterface category;

        /** calculator for category data */
        CategoryCalculatorInterface categoryCalculator;

        /** courseScene using this CategoryTable */
        CourseScene courseScene;

        /** formatter for displaying doubles */
        NumberFormat doubleFormatter = new DecimalFormat("#0.00");

        /** ObservableList representation of the Category list of Assignment */
        ObservableList<AssignmentInterface> assignments = FXCollections.observableArrayList();

        /** root layout for the Category */
        VBox categoryTableLayout = new VBox();

        /** child header layout contains summary data for the Category */
        GridPane headerLayout = new GridPane();

        /** child layout for headerLayout displays title */
        HBox titleLayout = new HBox();

        /** editable TextField displays Category Name*/
        TextField categoryNameField;

        /** editable TextField displays Category Weight */
        TextField weightField;

        /** editable TextField displays Category Grade */
        TextField categoryGrade;

        String tableNameStyle =
                "-fx-text-box-border: transparent; " +
                "-fx-background-color: transparent;" +
                "-fx-font-size: 22";

        String tableNameStyleOnFocus = "-fx-font-size: 22";

        String tableWeightStyle =
                "-fx-text-box-border: transparent; " +
                "-fx-background-color: transparent;" + "-fx-font-size: 20";

        String tableWeightStyleOnFocus = "-fx-font-size: 20";

        CategoryTable(Course course,
                      CategoryInterface category,
                      CategoryCalculatorInterface categoryCalculator,
                      CourseScene courseScene) {

            this.course = course;
            this.courseScene = courseScene;
            this.category = category;
            this.categoryCalculator = categoryCalculator;
            this.assignments.setAll(category.getAssignments());
            setItems(assignments);
            setId(category.getName());

            // creates titleLayout
            Label titleLabel = new Label("Category:");
            titleLabel.setStyle(tableNameStyle);
            categoryNameField = new TextField(category.getName());

            categoryNameField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
                if (lostFocus) {
                    categoryNameField.setStyle(tableNameStyle);
                    categoryNameField.setText(category.getName());
                }

                if (gainFocus) {
                    categoryNameField.setStyle(tableNameStyleOnFocus);
                }
            });

            categoryNameField.setOnAction(event -> {
                categoryNameField.setStyle(tableNameStyle);

                try {
                    course.changeCategoryName(category, categoryNameField.getText());
                    categoryNameField.setText(category.getName());
                } catch (DuplicateNameException d) {
                    AlertPopUp.alert(d.title, d.header, d.toString());
                }

                titleLayout.requestFocus();
            });

            categoryNameField.setStyle(tableNameStyle);
            categoryNameField.setAlignment(Pos.BASELINE_LEFT);
            titleLayout.getChildren().addAll(titleLabel, categoryNameField);
            titleLayout.setAlignment(Pos.BASELINE_LEFT);

            // creates weightLayout
            Label weightLabel = new Label("Weight:");
            weightLabel.setFont(Font.font(20));

            weightField = new TextField(String.valueOf( category.getWeight() ));
            weightField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
                        if (lostFocus) {
                            try {
                                weightField.setStyle(tableWeightStyle);
                                category.setWeight(Double.parseDouble(weightField.getText()));
                                courseScene.updateCourseGrade();
                            }
                            catch (Exception e) {
                            }
                        }
                        if (gainFocus) {
                            weightField.setStyle(tableWeightStyleOnFocus);
                        }
                    });

            weightField.setOnAction(event -> {
                weightField.setStyle(tableWeightStyle);
                category.setWeight(Double.parseDouble(weightField.getText()));
                courseScene.updateCourseGrade();
            });

            weightField.setStyle(tableWeightStyle);
            weightField.setFont(Font.font(20));

            // creates gradeLayout
            Label gradeLabel = new Label("Grade:");
            gradeLabel.setFont(Font.font(20));

            categoryGrade = new TextField(
                    doubleFormatter.format(
                            categoryCalculator.getCategoryGrade(category.getAssignments())));
            categoryGrade.setEditable(false);

            categoryGrade.setStyle(
                    "-fx-text-box-border: transparent; " +
                    "-fx-background-color: transparent;"
            );

            categoryGrade.setFont(Font.font(20));
            categoryGrade.setAlignment(Pos.BASELINE_LEFT);

            // configure headerLayout

            headerLayout.setBackground(SceneStyle.getSecondaryBackground());
            headerLayout.getColumnConstraints().add(new ColumnConstraints(110));
            headerLayout.add(titleLayout, 0, 0,4,1);
            headerLayout.add(gradeLabel, 0,1);
            headerLayout.add(categoryGrade, 1,1);
            headerLayout.add(weightLabel, 0, 2);
            headerLayout.add(weightField, 1, 2);

            headerLayout.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            headerLayout.setPadding(new Insets(0,0,5,0));

            categoryTableLayout.setPadding(new Insets(20,0,20,0));
            categoryTableLayout.setSpacing(5);
            categoryTableLayout.getChildren().add(headerLayout);
            categoryTableLayout.getChildren().add(this);

            buildTable();
        }

        /** builds TableView base class to display all Category Assignment */
        private void buildTable() {
            setEditable(true);

            /*
             * creates a name Column
             */
            TableColumn<AssignmentInterface, String> nameColumn = new TableColumn<>("Assignment");
            nameColumn.setMinWidth(100);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // property must match object
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            nameColumn.setOnEditCommit( event -> {
                // TODO Ask Model developer to throw exceptions with bad input
                try {
                    AssignmentInterface a = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    category.changeAssignmentName(a, event.getNewValue());
                } catch (DuplicateNameException d) {
                    AlertPopUp.alert(d.title, d.header, d.toString());
                    refresh();
                }
                catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    refresh();
                }
            });

            /*
             * creates a grade Column
             */
            TableColumn<AssignmentInterface, Double> gradeColumn = new TableColumn<>("Grade");
            gradeColumn.setMinWidth(130);
            gradeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            gradeColumn.setCellValueFactory(new PropertyValueFactory<>("currentGrade"));
            gradeColumn.setOnEditCommit( event -> {
                try {
                    // update assignment current grade in model
                    AssignmentInterface assignment = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    assignment.setCurrentGrade(event.getNewValue());

                    // replace table items with new assignments from model
                    getItems().setAll(category.getAssignments());

                    // re-calculate Category grade
                    Double updatedCategoryGrade = categoryCalculator.getCategoryGrade(category.getAssignments());
                    categoryGrade.setText(doubleFormatter.format(updatedCategoryGrade));

                    courseScene.updateCourseGrade();

                } catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    // updates table with data model if exception is caught
                    refresh();
                }
            });

            /*
             * creates a PotentialGrade Column
             */
            TableColumn<AssignmentInterface, Double> potentialGradeColumn = new TableColumn<>("Potential Grade");
            potentialGradeColumn.setMinWidth(130);
            potentialGradeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            potentialGradeColumn.setCellValueFactory(new PropertyValueFactory<>("potentialGrade"));
            potentialGradeColumn.setOnEditCommit( event -> {
                try {
                    // update assignment potential grade in model
                    AssignmentInterface assignment = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    assignment.setPotentialGrade(event.getNewValue());

                    // replace table items with new assignments from model
                    getItems().setAll(category.getAssignments());

                    // re-calculate Category grade
                    Double updatedCategoryGrade = categoryCalculator.getCategoryGrade(category.getAssignments());
                    categoryGrade.setText(doubleFormatter.format(updatedCategoryGrade));

                    courseScene.updateCourseGrade();

                } catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    // this updates table with data model if exception is caught
                    refresh();
                }
            });

            /*
             * 'delete assignment with delete key' feature
             */
            setOnKeyPressed(keyEvent -> {
                AssignmentInterface selectedAssignment = getSelectionModel().getSelectedItem();
                if (selectedAssignment !=  null) {
                    if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                        // TODO potential undo feature for deleted assignments

                        // remove assignment from category (model)
                        category.removeAssignment(selectedAssignment.getName());

                        // delete from table
                        getItems().remove(selectedAssignment);
                    }
                }
            });


            getColumns().addAll(nameColumn, gradeColumn, potentialGradeColumn);
            setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            /*
             * removes blank rows
             */

            setFixedCellSize(32);
            prefHeightProperty().bind(
                    fixedCellSizeProperty().multiply(Bindings.size(getItems()).add(1.01)));
            minHeightProperty().bind(prefHeightProperty());
            maxHeightProperty().bind(prefHeightProperty());

            /*
             * removes lines between rows
             */
            //setStyle("-fx-table-cell-border-color: transparent; -fx-focus-color: transparent;");
        }


}
