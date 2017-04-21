package controllers;

import com.got.alert.Alerter;
import dto.Theatre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.TheatreService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TheatreController extends Controller implements Initializable {
    private TheatreService theatreService = container.make(TheatreService.class);

    @FXML
    private Label nameLabel;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField seatCapacity;

    @FXML
    private TextField showTimeOne;

    @FXML
    private TextField showTimeTwo;

    @FXML
    private TextField showTimeThree;

    @FXML
    private TextField showTimeFour;

    @FXML
    private TextField showTimeFive;

    @FXML
    private TextField ticketPrice;

    private List<TextField> showTimesField;

    @FXML
    void cancel(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void update(ActionEvent event) {
        Theatre theatre = container.make(Theatre.class);
        theatre.setAddress(address.getText());
        theatre.setName(name.getText());
        theatre.setSeatCapacity(seatCapacity.getText());
        theatre.setTicketPrice(ticketPrice.getText());
        List<String> strings = Arrays.asList(
                showTimeOne.getText(),
                showTimeTwo.getText(),
                showTimeThree.getText(),
                showTimeFour.getText(),
                showTimeFive.getText()
        );
        theatre.setShowTimes(strings.stream().map(String::trim).collect(Collectors.joining(",")));

        if (!theatreService.update(theatre)) {
            Alerter.showError("Theatre not updated! Try again.");

            return;
        }

        Alerter.showSuccess("Theatre updated successfully.");
        nameLabel.setText(theatre.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Theatre theatre = theatreService.getTheatre();
        nameLabel.setText(theatre.getName());
        name.setText(theatre.getName());
        address.setText(theatre.getAddress());
        seatCapacity.setText(theatre.getSeatCapacity());
        ticketPrice.setText(theatre.getTicketPrice());
        List<String> showTimes = theatre.getShowTimes();

        showTimesField = new ArrayList<TextField>(){
            {
                add(showTimeOne);
                add(showTimeTwo);
                add(showTimeThree);
                add(showTimeFour);
                add(showTimeFive);
            }
        };

        for(int i = 0; i<showTimes.size(); i++) {
            showTimesField.get(i).setText(showTimes.get(i));
        }
    }
}
