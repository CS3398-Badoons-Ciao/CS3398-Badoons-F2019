package GUI;

import Interfaces.AssignmentInterface;
import Interfaces.CategoryCalculatorInterface;
import Interfaces.CategoryInterface;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * a Node for displaying a Category
 */
public class CategoryTable extends TableView<AssignmentInterface> {
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
        VBox headerLayout = new VBox();

        /** child layout for headerLayout displays title */
        HBox titleLayout = new HBox();

        /** child layout for headerLayout displays Category Weight */
        HBox weightLayout = new HBox();

        /** child layout for headLayout displays Category Grade */
        HBox gradeLayout = new HBox();

        /** editable TextField displays Category Name*/
        TextField CategoryNameField;

        /** editable TextField displays Category Weight */
        TextField weightField;
        Label gradeLabel;

        CategoryTable(CategoryInterface category,
                      CategoryCalculatorInterface categoryCalculator,
                      CourseScene courseScene) {
            this.category = category;
            this.categoryCalculator = categoryCalculator;
            this.assignments.setAll(category.getAssignments());
            setItems(assignments);
            setId(category.getName());

            // creates titleLayout
            Label titleLabel = new Label("Category:");
            CategoryNameField = new TextField(category.getName());

            CategoryNameField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
                        if (lostFocus) {
                            category.setName(CategoryNameField.getText());
                            courseScene.updateCategoryNames();
                        }
                    });

            CategoryNameField.setStyle("-fx-text-box-border: transparent; " +
                                "-fx-background-color: transparent;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 16");
            CategoryNameField.setAlignment(Pos.CENTER);
            titleLayout.getChildren().addAll(CategoryNameField);
            titleLayout.setAlignment(Pos.CENTER);
            titleLayout.setStyle("-fx-background-color: lightgrey;");

            // creates weightLayout
            Label weightLabel = new Label("Weight:");
            weightField = new TextField(String.valueOf( category.getWeight() ));
            weightField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
                        if (lostFocus) {
                            try {
                                category.setWeight(Double.valueOf(CategoryNameField.getText()));
                            }
                            catch (Exception e) {
                            }
                        }
                    });

            weightField.setStyle(   "-fx-text-box-border: transparent; " +
                                    "-fx-background-color: transparent;");
            weightLayout.getChildren().addAll(weightLabel, weightField);
            weightLayout.setAlignment(Pos.CENTER_LEFT);
            weightLayout.setPadding(new Insets(0,0,0,5));

            // creates gradeLayout
            Label gradeLabel = new Label("Grade:");
            this.gradeLabel = new Label(
                    doubleFormatter.format(
                            categoryCalculator.getCategoryGrade(category.getAssignments())));


            this.gradeLabel.setStyle(   "-fx-text-box-border: transparent; " +
                    "-fx-background-color: transparent;");
            gradeLayout.getChildren().addAll(gradeLabel, this.gradeLabel);
            gradeLayout.setAlignment(Pos.CENTER_LEFT);
            gradeLayout.setSpacing(2);
            gradeLayout.setPadding(new Insets(0,0,5,5));

            // configure headerLayout
            headerLayout.getChildren().addAll(titleLayout, weightLayout, gradeLayout);
            headerLayout.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            headerLayout.setPadding(new Insets(0,0,0,0));

            categoryTableLayout.setPadding(new Insets(20,0,20,0));
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
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
                } catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    refresh();
                }
            });

            /*
             * creates a grade Column
             */
            TableColumn<AssignmentInterface, Double> gradeColumn = new TableColumn<>("Grade");
            gradeColumn.setMinWidth(100);
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
                    gradeLabel.setText(doubleFormatter.format(updatedCategoryGrade));

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
            potentialGradeColumn.setMinWidth(100);
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
                    gradeLabel.setText(doubleFormatter.format(updatedCategoryGrade));

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
            setFixedCellSize(28);
            prefHeightProperty().bind(
                    fixedCellSizeProperty().multiply(Bindings.size(getItems()).add(1.01)));
            minHeightProperty().bind(prefHeightProperty());
            maxHeightProperty().bind(prefHeightProperty());

            /*
             * removes lines between rows
             */
            setStyle("-fx-table-cell-border-color: transparent; -fx-focus-color: transparent;");
        }


}
