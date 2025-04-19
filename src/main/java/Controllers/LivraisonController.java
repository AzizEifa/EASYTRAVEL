package Controllers;

import Entities.Livraison;
import Services.LivraisonService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class LivraisonController {

    @FXML
    private TextField departField;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField prixField;
    @FXML
    private DatePicker dateField;

    @FXML
    private TableView<Livraison> livraisonTable;
    @FXML
    private TableColumn<Livraison, String> departCol;
    @FXML
    private TableColumn<Livraison, String> destinationCol;
    @FXML
    private TableColumn<Livraison, String> dateCol;  // Change this to String column
    @FXML
    private TableColumn<Livraison, Double> prixCol;

    private final LivraisonService livraisonService = new LivraisonService();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadLivraisons();
        setupTableSelectionListener();
    }

    private void setupTableColumns() {
        departCol.setCellValueFactory(new PropertyValueFactory<>("depart"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));

        // For the date column, we will convert the Timestamp to String
        dateCol.setCellValueFactory(cellData -> {
            Timestamp timestamp = cellData.getValue().getDateLivraison();
            return new SimpleStringProperty(timestamp != null ? timestamp.toString() : "");
        });

        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
    }

    private void loadLivraisons() {
        try {
            List<Livraison> livraisons = livraisonService.getAllLivraisons();
            livraisonTable.setItems(FXCollections.observableArrayList(livraisons));
        } catch (Exception e) {
            showAlert("Error loading livraisons: " + e.getMessage());
        }
    }

    private void setupTableSelectionListener() {
        livraisonTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        fillFormWithSelectedLivraison(newSelection);
                    }
                });
    }

    private void fillFormWithSelectedLivraison(Livraison livraison) {
        departField.setText(livraison.getDepart());
        destinationField.setText(livraison.getDestination());
        prixField.setText(String.valueOf(livraison.getPrix()));
        dateField.setValue(livraison.getDateLivraison().toLocalDateTime().toLocalDate());
    }

    @FXML
    private void handleAdd() {
        try {
            Livraison livraison = createLivraisonFromForm();
            livraisonService.insertLivraison(livraison);
            clearForm();
            loadLivraisons();
        } catch (Exception e) {
            showAlert("Error adding livraison: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Livraison selected = livraisonTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a livraison to update");
            return;
        }

        try {
            Livraison updatedLivraison = createLivraisonFromForm();
            updatedLivraison.setId(selected.getId());
            livraisonService.updateLivraison(updatedLivraison);
            clearForm();
            loadLivraisons();
        } catch (Exception e) {
            showAlert("Error updating livraison: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Livraison selected = livraisonTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a livraison to delete");
            return;
        }

        try {
            livraisonService.deleteLivraison(selected.getId());
            clearForm();
            loadLivraisons();
        } catch (Exception e) {
            showAlert("Error deleting livraison: " + e.getMessage());
        }
    }

    private Livraison createLivraisonFromForm() {
        return new Livraison(
                departField.getText(),
                destinationField.getText(),
                Timestamp.valueOf(LocalDateTime.of(dateField.getValue(), LocalDateTime.now().toLocalTime())),
                Double.parseDouble(prixField.getText())
        );
    }

    private void clearForm() {
        departField.clear();
        destinationField.clear();
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
