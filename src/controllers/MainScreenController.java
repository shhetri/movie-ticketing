package controllers;

import com.got.window.Window;
import com.got.window.WindowViewResolver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainScreenController extends Controller {
    @FXML
    private Button btnRegMovie;
    @FXML
    private Button btnTheatre;
    @FXML
    private Button btnReport;
    @FXML
    private Button btnLogout;

    {
        WindowViewResolver.getInstance().setRootView("/views");
    }

    @FXML
    void Logout(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void showMovie(ActionEvent event) {


    }

    @FXML
    void showReport(ActionEvent event) {


    }

    @FXML
    void showTheatre(ActionEvent event) {
        Window.WindowBuilder.initialize()
                .withView("TheatreScreen")
                .withTitle("Theatre Information")
                .build()
                .open();
    }
}
