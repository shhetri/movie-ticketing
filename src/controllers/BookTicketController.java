package controllers;

import com.got.alert.Alerter;
import com.got.database.DB;
import com.got.validator.Validator;
import dto.Booking;
import dto.Movie;
import dto.Theatre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class BookTicketController extends Controller implements Initializable {

    private List<Movie> movies;

    @FXML
    private ComboBox<String> movieNames;

    @FXML
    private ComboBox<String> movieDate;

    @FXML
    private ComboBox<String> movieShowTimes;

    @FXML
    private Label availableSeats;

    private Movie selectedMovie;
    private Theatre theatre;

    @FXML
    private TextField bookedSeats;

    @FXML
    private TextField customerContact;

    @FXML
    private TextField customerName;

    @FXML
    void bookTicket(ActionEvent event) {


        Validator validator = container.make(Validator.class);
        validator.addRules(movieNames, "required", "Movie must be selected");
        validator.addRules(movieDate, "required", "Date must be selected");
        validator.addRules(movieShowTimes, "required", "Show time must be selected");
        validator.addRules(bookedSeats, "required|number", "Number of seats is required|Number of seats must be numeric");
        validator.addRules(customerName, "required|alphanumeric", "Customer name is required|Customer name must be alphanumeric");
        validator.addRules(customerContact, "required|number", "Customer contact number is required|Customer contact number must be numeric");

        if(!validator.validate()) return;

        if(Integer.parseInt(bookedSeats.getText()) > Integer.parseInt(availableSeats.getText())) {
            Alerter.showError("Number of seats must not be greater than available seats!");
            return;
        }

        float totalPrice = Float.parseFloat(bookedSeats.getText()) * Float.parseFloat(theatre.getTicketPrice());

        Map<String, String> bookingInfo = new HashMap<String, String>(){
            {
                put("movie_id", selectedMovie.getId());
                put("date", movieDate.getSelectionModel().getSelectedItem());
                put("showtime", movieShowTimes.getSelectionModel().getSelectedItem());
                put("no_of_seats", bookedSeats.getText());
                put("customer_name", customerName.getText());
                put("customer_phone", customerContact.getText());
                put("total_price", totalPrice + "");
            }
        };

        int row = DB.table("bookings").values(bookingInfo).insert();
        if(row > 0) {
            Alerter.showSuccess("Ticket booked successfully with total amount of $" + totalPrice);
            clearFields();
        } else {
            Alerter.showError("Unable to book ticket!");
        }

    }

    private void clearFields() {
        bookedSeats.setText("");
        customerName.setText("");
        customerContact.setText("");
        updateDate();
    }

    @FXML
    void cancel(ActionEvent event) {

        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        movies = DB.table("movie").get(Movie.class);

        movies.forEach(movie -> movieNames.getItems().add(movie.getName()));

        movieNames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                selectedMovie = movies.get(movieNames.getSelectionModel().getSelectedIndex());
                updateDate();
            }
        });

    }

    private void updateDate() {
        List<String> dates = parseDates(selectedMovie.getFromDate(), selectedMovie.getToDate());

        movieDate.getItems().clear();

        dates.forEach(date -> movieDate.getItems().add(date));

        availableSeats.setText("0");

        movieDate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                updateShowTimes(movieDate.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void updateShowTimes(String selectedDate) {
        List<String> showTimes = selectedMovie.getShowTimesList();

        movieShowTimes.getItems().clear();

        showTimes.forEach(showTime -> movieShowTimes.getItems().add(showTime));

        availableSeats.setText("0");

        movieShowTimes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                updateAvailableSeats(selectedDate, movieShowTimes.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void updateAvailableSeats(String selectedDate, String selectedShowTime) {

        List<Booking> bookings = DB.table("bookings").where("movie_id", selectedMovie.getId()).andWhere("date",selectedDate).andWhere("showtime", selectedShowTime).get(Booking.class);

        int bookedSeats = 0;
        for(int i=0; i<bookings.size(); i++) {
            bookedSeats += Integer.parseInt(bookings.get(i).getNoOfSeats());
        }

        theatre = DB.table("theatre").getFirst(Theatre.class);
        int availableSeatsText = Integer.parseInt(theatre.getSeatCapacity()) - bookedSeats;

        availableSeats.setText(availableSeatsText+"");

    }

    private List<String> parseDates(String fromDate, String toDate) {
        List<String> dates = new ArrayList<>();
        LocalDate fromDateLocalDate = LocalDate.parse(fromDate);
        LocalDate toDateLocalDate = LocalDate.parse(toDate);

        while (fromDateLocalDate.isBefore(toDateLocalDate)) {
            dates.add(fromDateLocalDate.toString());
            fromDateLocalDate = fromDateLocalDate.plusDays(1);
        }

        return dates;
    }
}
