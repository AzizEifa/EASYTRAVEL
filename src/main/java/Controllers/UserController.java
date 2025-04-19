package Controllers;

import Entities.User;
import Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserController {
    // UI Components
    @FXML private TextField tfId;
    @FXML private TextField tfAge;
    @FXML private TextField tfNom;
    @FXML private TextField tfPrenom;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfAddress;
    @FXML private ComboBox<String> cbRole;
    @FXML private PasswordField pfPassword;
    @FXML private ComboBox<String> cbGender;

    @FXML private TableView<User> tableView;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, Integer> colAge;
    @FXML private TableColumn<User, String> colNom;
    @FXML private TableColumn<User, String> colPrenom;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colPhone;
    @FXML private TableColumn<User, String> colAddress;
    @FXML private TableColumn<User, String> colRole;
    @FXML private TableColumn<User, String> colGender;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        try {

            cbRole.getItems().addAll("Admin", "Client", "Livreur");
            cbGender.getItems().addAll("Male", "Female");

            setupTableColumns();
            loadTableData();
            setupTableSelection();
        } catch (Exception e) {
            showErrorAlert("Initialization Error", "Failed to initialize controller: " + e.getMessage());
        }
    }

    private void setupTableColumns() {
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        } catch (Exception e) {
            showErrorAlert("Table Setup Error", "Failed to setup table columns: " + e.getMessage());
        }
    }

    private void loadTableData() {
        try {
            tableView.getItems().setAll(userService.recuperer());
        } catch (Exception e) {
            showErrorAlert("Data Loading Error", "Failed to load user data: " + e.getMessage());
        }
    }

    private void setupTableSelection() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal != null) {
                    fillFormWithSelectedItem(newVal);
                }
            } catch (Exception e) {
                showErrorAlert("Selection Error", "Failed to handle selection: " + e.getMessage());
            }
        });
    }

    private void fillFormWithSelectedItem(User user) {
        try {
            tfId.setText(String.valueOf(user.getId()));
            tfAge.setText(String.valueOf(user.getAge()));
            tfNom.setText(user.getNom());
            tfPrenom.setText(user.getPrenom());
            tfEmail.setText(user.getEmail());
            tfPhone.setText(user.getPhoneNumber());
            tfAddress.setText(user.getAddress());
            cbRole.setValue(user.getRole());
            pfPassword.setText(user.getPassword());
            cbGender.setValue(user.getGender());
        } catch (Exception e) {
            showErrorAlert("Form Fill Error", "Failed to fill form: " + e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        try {
            if (!validateForm()) return;

            User user = createUserFromForm();
            userService.ajouter(user);
            showSuccessAlert("User Added", "User added successfully!");
            clearForm();
            loadTableData();
        } catch (NumberFormatException e) {
            showErrorAlert("Input Error", "Please enter valid numeric values for age");
        } catch (IllegalArgumentException e) {
            showErrorAlert("Input Error", e.getMessage());
        } catch (Exception e) {
            showErrorAlert("Add Error", "Failed to add user: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            User selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showWarningAlert("No Selection", "Please select a user to update");
                return;
            }

            if (!validateForm()) return;

            User user = createUserFromForm();
            user.setId(selected.getId());
            userService.modifier(user);
            showSuccessAlert("User Updated", "User updated successfully!");
            clearForm();
            loadTableData();
        } catch (Exception e) {
            showErrorAlert("Update Error", "Failed to update user: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        try {
            User selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showWarningAlert("No Selection", "Please select a user to delete");
                return;
            }

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Delete User");
            confirmation.setContentText("Are you sure you want to delete this user?");

            if (confirmation.showAndWait().get() == ButtonType.OK) {
                userService.supprimer(selected);
                showSuccessAlert("User Deleted", "User deleted successfully!");
                clearForm();
                loadTableData();
            }
        } catch (Exception e) {
            showErrorAlert("Delete Error", "Failed to delete user: " + e.getMessage());
        }
    }

    private User createUserFromForm() throws IllegalArgumentException {
        // Validate required fields
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() ||
                tfEmail.getText().isEmpty() || cbRole.getValue() == null) {
            throw new IllegalArgumentException("Please fill all required fields");
        }

        return new User(
                Integer.parseInt(tfAge.getText()),
                tfNom.getText(),
                tfPrenom.getText(),
                tfEmail.getText(),
                tfPhone.getText(),
                tfAddress.getText(),
                cbRole.getValue(),
                pfPassword.getText(),
                cbGender.getValue()
        );
    }

    private boolean validateForm() {
        try {

            Integer.parseInt(tfAge.getText());


            if (tfNom.getText().isEmpty()) {
                showWarningAlert("Validation Error", "Last name is required");
                return false;
            }
            if (tfPrenom.getText().isEmpty()) {
                showWarningAlert("Validation Error", "First name is required");
                return false;
            }
            if (tfEmail.getText().isEmpty() || !tfEmail.getText().contains("@")) {
                showWarningAlert("Validation Error", "Valid email is required");
                return false;
            }
            if (cbRole.getValue() == null) {
                showWarningAlert("Validation Error", "Role is required");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showWarningAlert("Validation Error", "Age must be a valid number");
            return false;
        }
    }

    private void clearForm() {
        try {
            tfId.clear();
            tfAge.clear();
            tfNom.clear();
            tfPrenom.clear();
            tfEmail.clear();
            tfPhone.clear();
            tfAddress.clear();
            cbRole.getSelectionModel().clearSelection();
            pfPassword.clear();
            cbGender.getSelectionModel().clearSelection();
        } catch (Exception e) {
            showErrorAlert("Clear Form Error", "Failed to clear form: " + e.getMessage());
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarningAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}