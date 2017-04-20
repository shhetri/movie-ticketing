package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainScreenController extends Controller {

    @FXML
    private Button btnRegMovie;

    @FXML
    private Button btnTheatre;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnLogout;

    @FXML
    void Logout(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

    }

    @FXML
    void showMovie(ActionEvent event) {


    }

    @FXML
    void showReport(ActionEvent event) {


    }

    @FXML
    void showTheatre(ActionEvent event) {


    }
}
