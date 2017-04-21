package controllers;

import com.got.alert.Alerter;
import com.got.validator.ValidationType;
import com.got.validator.Validator;
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

    @FXML
    void cancel(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void update(ActionEvent event) {
        Validator validator = container.make(Validator.class);
        validator.setValidationType(ValidationType.POPUP);
        validator.addRules(name, "required|alphanumeric", "Name is required|Name must be alpha numeric");
        validator.addRules(name, "required", "Address is required");
        validator.addRules(seatCapacity, "required|number", "Seat Capacity is required|Seat capacity must be a number");
        validator.addRules(ticketPrice, "required|number", "Ticket Price is required|Ticket Price must be a number");
        validator.addRules(showTimeOne, "required|time", "Show Time is required|Show Time must be a valid time in HH:MM format");
        validator.addRules(showTimeTwo, "required|time", "Show Time is required|Show Time must be a valid time in HH:MM format");
        validator.addRules(showTimeThree, "required|time", "Show Time is required|Show Time must be a valid time in HH:MM format");
        validator.addRules(showTimeFour, "required|time", "Show Time is required|Show Time must be a valid time in HH:MM format");
        validator.addRules(showTimeFive, "required|time", "Show Time is required|Show Time must be a valid time in HH:MM format");

        if (!validator.validate()) {
            return;
        }

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
        showTimeOne.setText(showTimes.get(0));
        showTimeTwo.setText(showTimes.get(1));
        showTimeThree.setText(showTimes.get(2));
        showTimeFour.setText(showTimes.get(3));
        showTimeFive.setText(showTimes.get(4));
    }
}
