package Controllers;

import Entities.Feedback;
import Services.FeedbackService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class FeedbackController {

    @FXML
    private TextField userIdField;
    @FXML
    private TextField subjectField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Spinner<Integer> ratingSpinner;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Feedback> feedbackTable;
    @FXML
    private TableColumn<Feedback, Integer> idCol;
    @FXML
    private TableColumn<Feedback, Integer> userIdCol;
    @FXML
    private TableColumn<Feedback, String> subjectCol;
    @FXML
    private TableColumn<Feedback, String> descriptionCol;
    @FXML
    private TableColumn<Feedback, Integer> ratingCol;
    @FXML
    private TableColumn<Feedback, String> dateCol;

    private final FeedbackService feedbackService = new FeedbackService();

    @FXML
    public void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3);
        ratingSpinner.setValueFactory(valueFactory);

        setupTableColumns();
        loadFeedbacks();
        setupTableSelectionListener();
    }

    private void setupTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        dateCol.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getDate();
            return new SimpleStringProperty(date != null ? date.toString() : "");
        });
    }

    private void loadFeedbacks() {
        try {
            List<Feedback> feedbacks = feedbackService.recuperer();
            feedbackTable.setItems(FXCollections.observableArrayList(feedbacks));
        } catch (Exception e) {
            showAlert("Error loading feedbacks: " + e.getMessage());
        }
    }

    private void setupTableSelectionListener() {
        feedbackTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        fillFormWithSelectedFeedback(newSelection);
                    }
                });
    }

    private void fillFormWithSelectedFeedback(Feedback feedback) {
        userIdField.setText(String.valueOf(feedback.getUserId()));
        subjectField.setText(feedback.getSubject());
        descriptionField.setText(feedback.getDescription());
        ratingSpinner.getValueFactory().setValue(feedback.getRating());
        datePicker.setValue(feedback.getDate());
    }

    @FXML
    private void handleAdd() {
        try {
            Feedback feedback = createFeedbackFromForm();
            feedbackService.ajouter(feedback);
            clearForm();
            loadFeedbacks();
        } catch (Exception e) {
            showAlert("Error adding feedback: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Feedback selected = feedbackTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a feedback to update");
            return;
        }

        try {
            Feedback updatedFeedback = createFeedbackFromForm();
            updatedFeedback.setId(selected.getId());
            feedbackService.modifier(updatedFeedback);
            clearForm();
            loadFeedbacks();
        } catch (Exception e) {
            showAlert("Error updating feedback: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Feedback selected = feedbackTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a feedback to delete");
            return;
        }

        try {
            feedbackService.supprimer(selected);
            clearForm();
            loadFeedbacks();
        } catch (Exception e) {
            showAlert("Error deleting feedback: " + e.getMessage());
        }
    }

    private Feedback createFeedbackFromForm() {
        return new Feedback(
                Integer.parseInt(userIdField.getText()),
                subjectField.getText(),
                descriptionField.getText(),
                ratingSpinner.getValue(),
                datePicker.getValue()
        );
    }

    private void clearForm() {
        userIdField.clear();
        subjectField.clear();
        descriptionField.clear();
        ratingSpinner.getValueFactory().setValue(3);
        datePicker.setValue(null);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}