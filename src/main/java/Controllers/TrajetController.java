package Controllers;

import Entities.Trajet;
import Services.TrajetService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.beans.property.*;
import java.time.LocalDateTime;
import java.util.List;

public class TrajetController {
    @FXML private TextField departField;
    @FXML private TextField destinationField;
    @FXML private TextField distanceField;
    @FXML private TextField prixField;
    @FXML private DatePicker dateField;

    @FXML private TableView<Trajet> trajetTable;
    @FXML private TableColumn<Trajet, String> departCol;
    @FXML private TableColumn<Trajet, String> destinationCol;
    @FXML private TableColumn<Trajet, Double> distanceCol;
    @FXML private TableColumn<Trajet, Double> prixCol;
    @FXML private TableColumn<Trajet, String> dateCol;

    private final TrajetService trajetService = new TrajetService();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadTrajets();
        setupTableSelectionListener();
    }

    private void setupTableColumns() {
        departCol.setCellValueFactory(new PropertyValueFactory<>("depart"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        distanceCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateCol.setCellValueFactory(cellData -> {
            LocalDateTime date = cellData.getValue().getDateDepart();
            return new SimpleStringProperty(date != null ? date.toString() : "");
        });
    }

    private void loadTrajets() {
        try {
            List<Trajet> trajets = trajetService.getAll();
            trajetTable.setItems(FXCollections.observableArrayList(trajets));
        } catch (Exception e) {
            showAlert("Error loading trajets: " + e.getMessage());
        }
    }

    private void setupTableSelectionListener() {
        trajetTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        fillFormWithSelectedTrajet(newSelection);
                    }
                });
    }

    private void fillFormWithSelectedTrajet(Trajet trajet) {
        departField.setText(trajet.getDepart());
        destinationField.setText(trajet.getDestination());
        distanceField.setText(String.valueOf(trajet.getDistance()));
        prixField.setText(String.valueOf(trajet.getPrix()));
        dateField.setValue(trajet.getDateDepart() != null ?
                trajet.getDateDepart().toLocalDate() : null);
    }

    @FXML
    private void handleAdd() {
        try {
            Trajet trajet = createTrajetFromForm();
            trajetService.add(trajet);
            clearForm();
            loadTrajets();
        } catch (Exception e) {
            showAlert("Error adding trajet: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Trajet selected = trajetTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a trajet to update");
            return;
        }

        try {
            Trajet updatedTrajet = createTrajetFromForm();
            updatedTrajet.setId(selected.getId());
            trajetService.update(updatedTrajet);
            clearForm();
            loadTrajets();
        } catch (Exception e) {
            showAlert("Error updating trajet: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Trajet selected = trajetTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a trajet to delete");
            return;
        }

        try {
            trajetService.delete(selected.getId());
            clearForm();
            loadTrajets();
        } catch (Exception e) {
            showAlert("Error deleting trajet: " + e.getMessage());
        }
    }

    private Trajet createTrajetFromForm() {
        return new Trajet(
                departField.getText(),
                destinationField.getText(),
                Double.parseDouble(distanceField.getText()),
                dateField.getValue() != null ?
                        dateField.getValue().atStartOfDay() :
                        LocalDateTime.now(),
                Double.parseDouble(prixField.getText())
        );
    }

    private void clearForm() {
        departField.clear();
        destinationField.clear();
        distanceField.clear();
        prixField.clear();
        dateField.setValue(null);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}