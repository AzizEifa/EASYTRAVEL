package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentPane;

    @FXML
    public void initialize() {
        showTrajets();
    }

    @FXML
    private void showTrajets() {
        loadView("trajet.fxml");
    }

    @FXML
    private void showLivraisons() {
        loadView("livraison.fxml");
    }
    @FXML
    private void showUsers(){loadView("users.fxml");}
    @FXML
    private void showFeedbacks(){loadView("feedback.fxml");}

    private void loadView(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
