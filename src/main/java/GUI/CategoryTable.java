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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;

import javax.xml.crypto.Data;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * node for CourseScene representing a category
 */
public class CategoryTable extends TableView<AssignmentInterface> {
        CategoryInterface category;
        CategoryCalculatorInterface categoryCalculator;
        NumberFormat doubleFormatter = new DecimalFormat("#0.00");
        ObservableList<AssignmentInterface> assignments = FXCollections.observableArrayList();
        VBox categoryTableLayout = new VBox();
        VBox headerLayout = new VBox();
        HBox titleLayout = new HBox();
        HBox weightLayout = new HBox();
        HBox gradeLayout = new HBox();
        TextField titleField;
        TextField weightField;
        Label gradeLabel;

        CategoryTable(CategoryInterface category,
                      CategoryCalculatorInterface categoryCalculator) {
            this.category = category;
            this.categoryCalculator = categoryCalculator;
            this.assignments.setAll(category.getAssignments());
            setItems(assignments);

            // creates titleLayout
            Label titleLabel = new Label("Category:");
            titleField = new TextField(category.getName());
            titleField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
                        if (lostFocus) {
                            category.setName(titleField.getText());
                        }
                    });

            titleField.setStyle(
                    "-fx-text-box-border: transparent; " +
                            "-fx-background-color: transparent;");
            titleLayout.getChildren().addAll(titleLabel, titleField);
            titleLayout.setAlignment(Pos.CENTER_LEFT);

            // creates weightLayout
            Label weightLabel = new Label("Weight:");
            weightField = new TextField(String.valueOf( category.getWeight() ));
            weightField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean lostFocus, Boolean gainFocus) -> {
                        if (lostFocus) {
                            try {
                                category.setWeight(Double.valueOf(titleField.getText()));
                            }
                            catch (Exception e) {
                            }
                        }
                    });


            weightField.setStyle(   "-fx-text-box-border: transparent; " +
                    "-fx-background-color: transparent;");
            weightLayout.getChildren().addAll(weightLabel, weightField);
            weightLayout.setAlignment(Pos.CENTER_LEFT);

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

            // configure headerLayout
            headerLayout.getChildren().addAll(titleLayout, weightLayout, gradeLayout);
            headerLayout.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            headerLayout.setPadding(new Insets(2,5,2,5));

            categoryTableLayout.setPadding(new Insets(20,0,20,0));
            categoryTableLayout.getChildren().add(headerLayout);
            categoryTableLayout.getChildren().add(this);

            buildTable();
        }

        private void buildTable() {
            setEditable(true);

            // creates a name column
            TableColumn<AssignmentInterface, String> nameColumn = new TableColumn<>("Assignment");
            nameColumn.setMinWidth(100);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // property must match object
            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            nameColumn.setOnEditCommit( event -> {
                // TODO Ask Model developer to throw exceptions with bad input
                try {
                    // this also updates the model
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
                } catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    // this updates table with data model if exception is caught
                    refresh();
                }
            });

            // creates a grade column
            TableColumn<AssignmentInterface, Double> gradeColumn = new TableColumn<>("Grade");
            gradeColumn.setMinWidth(100);
            gradeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            gradeColumn.setCellValueFactory(new PropertyValueFactory<>("currentGrade"));
            gradeColumn.setOnEditCommit( event -> {
                try {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).
                            setCurrentGrade(event.getNewValue());

                    // update category table grade
                    String updatedCategoryGrade =
                            String.valueOf(categoryCalculator.getCategoryGrade(category.getAssignments()));

                    gradeLabel.setText(updatedCategoryGrade);

                } catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    // this updates table with data model if exception is caught
                    refresh();
                }
            });


            // creates a potential grade column
            TableColumn<AssignmentInterface, Double> potentialGradeColumn = new TableColumn<>("Potential Grade");
            potentialGradeColumn.setMinWidth(100);
            potentialGradeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            potentialGradeColumn.setCellValueFactory(new PropertyValueFactory<>("potentialGrade"));
            potentialGradeColumn.setOnEditCommit( event -> {
                try {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).
                            setPotentialGrade(event.getNewValue());
                } catch(Exception e) {
                    // TODO catch specific exception related to bad input
                    // this updates table with data model if exception is caught
                    refresh();
                }
            });

            // delete assignment with delete key feature
            setOnKeyPressed(keyEvent -> {
                AssignmentInterface selectedAssignment = getSelectionModel().getSelectedItem();
                if (selectedAssignment !=  null) {
                    if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                        // adds removed assignment to stack for undo feature
                        // must also store category
                        // deletedAssignments.push(selectedAssignment);

                        // remove assignment from category (model)
                        category.removeAssignment(selectedAssignment.getName());

                        // delete from table
                        getItems().remove(selectedAssignment);

                    }
                }
            });

            getColumns().addAll(nameColumn, gradeColumn, potentialGradeColumn);
            setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            // removes blank rows
            setFixedCellSize(28);
            prefHeightProperty().bind(
                    fixedCellSizeProperty().multiply(Bindings.size(getItems()).add(1.01)));
            minHeightProperty().bind(prefHeightProperty());
            maxHeightProperty().bind(prefHeightProperty());

            // removes lines between rows
            setStyle("-fx-table-cell-border-color: transparent; -fx-focus-color: transparent;");
        }


}
