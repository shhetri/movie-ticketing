package controllers;

import com.got.alert.Alerter;
import com.got.database.DB;
import com.got.validator.Validator;
import dto.Movie;
import dto.Theatre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import services.MovieService;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MovieScreenController extends Controller implements Initializable {

    List<CheckBox> showTimesCheckBoxes;
    @FXML
    private CheckBox showTime1;
    @FXML
    private CheckBox showTime2;
    @FXML
    private CheckBox showTime3;
    @FXML
    private CheckBox showTime4;
    @FXML
    private CheckBox showTime5;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TextField titleField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField lengthField;
    @FXML
    private AnchorPane moviesTableContainer;
    private Movie selectedMovie = container.make(Movie.class);

    @FXML
    void addMovie(ActionEvent event) {

        Validator validator = container.make(Validator.class);
        validator.addRules(titleField, "required|alphanumeric", "Title is required|Title must be alapha numeric");
        validator.addRules(lengthField, "required|time", "Length is required|Length must be in HH:MM format");
        validator.addRules(genreField, "required|alphanumeric", "Genre is required|Genre must be alapha numeric");
        validator.addRules(fromDate, "required|date", "From is required|From field must be a valid date");
        validator.addRules(toDate, "required|date", "To is required|To field must be a valid date");

        if (!validator.validate()) return;
        if (!(showTime1.isSelected() || showTime2.isSelected() || showTime3.isSelected() || showTime4.isSelected() || showTime5.isSelected())) {
            Alerter.showError("At least one show time must be selected.");
            return;
        }
        Movie movie = container.make(Movie.class);
        movie.setName(titleField.getText());
        movie.setLength(lengthField.getText());
        movie.setGenre(genreField.getText());
        movie.setFromDate(fromDate.getValue().toString());
        movie.setToDate(toDate.getValue().toString());
        movie.setShowTimes(generateShowTimesString());

        Map<String, String> movieValues = new HashMap<String, String>() {
            {
                put("name", movie.getName());
                put("length", movie.getLength());
                put("genre", movie.getGenre());
                put("from_date", movie.getFromDate());
                put("to_date", movie.getToDate());
                put("show_times", movie.getShowTimes());
            }
        };
        int row = DB.table("movie").values(movieValues).insert();

        if (row > 0) {
            Alerter.showSuccess("Movie has been added successfully!");
            loadMovies();
            clearFields();
        } else {
            Alerter.showError("Unable to add Movie!");
        }
    }

    private String generateShowTimesString() {
        String showTimes = "";
        if (showTime1.isSelected())
            showTimes += showTime1.getText() + ",";
        if (showTime2.isSelected())
            showTimes += showTime2.getText() + ",";
        if (showTime3.isSelected())
            showTimes += showTime3.getText() + ",";
        if (showTime4.isSelected())
            showTimes += showTime4.getText() + ",";
        if (showTime5.isSelected())
            showTimes += showTime5.getText() + ",";
        if (showTimes.contains(","))
            showTimes = showTimes.substring(0, showTimes.length() - 1);

        return showTimes;
    }

    @FXML
    void deleteMovie(ActionEvent event) {

        int row = DB.table("movie").where("id", selectedMovie.getId()).delete();

        if (row > 0) {
            Alerter.showSuccess("Movie has been deleted successfully!");
            loadMovies();
            clearFields();
        } else {
            Alerter.showError("Unable to delete Movie!");
        }

    }

    @FXML
    void updateMovie(ActionEvent event) {

        Validator validator = container.make(Validator.class);
        validator.addRules(titleField, "required|alphanumeric", "Title is required|Title must be alaphanumeric");
        validator.addRules(lengthField, "required|time", "Length is required|Length must be in HH:MM format");
        validator.addRules(genreField, "required|alphanumeric", "Genre is required|Genre must be alaphanumeric");
        validator.addRules(fromDate, "required|date", "From is required|From field must be a valid date");
        validator.addRules(toDate, "required|date", "To is required|To field must be a valid date");

        if (!validator.validate()) return;
        if (!(showTime1.isSelected() || showTime2.isSelected() || showTime3.isSelected() || showTime4.isSelected() || showTime5.isSelected())) {
            Alerter.showError("At least one show time must be selected.");
            return;
        }
        Movie movie = container.make(Movie.class);
        movie.setName(titleField.getText());
        movie.setLength(lengthField.getText());
        movie.setGenre(genreField.getText());
        movie.setFromDate(fromDate.getValue().toString());
        movie.setToDate(toDate.getValue().toString());
        movie.setShowTimes(generateShowTimesString());

        Map<String, String> movieValues = new HashMap<String, String>() {
            {
                put("name", movie.getName());
                put("length", movie.getLength());
                put("genre", movie.getGenre());
                put("from_date", movie.getFromDate());
                put("to_date", movie.getToDate());
                put("show_times", movie.getShowTimes());
            }
        };
        int row = DB.table("movie").set(movieValues).where("id", selectedMovie.getId()).update();

        if (row > 0) {
            Alerter.showSuccess("Movie has been updated successfully!");
            loadMovies();
            clearFields();
        } else {
            Alerter.showError("Unable to update Movie!");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMovies();

        showTimesCheckBoxes = new ArrayList<CheckBox>() {
            {
                add(showTime1);
                add(showTime2);
                add(showTime3);
                add(showTime4);
                add(showTime5);
            }
        };

        updateCheckBoxes();
    }

    private void updateCheckBoxes() {
        Theatre theatre = DB.table("theatre").getFirst(Theatre.class);
        List<String> showTimes = theatre.getShowTimes();

        showTimesCheckBoxes.forEach(box -> box.setVisible(false));

        for (int i = 0; i < showTimes.size(); i++) {
            showTimesCheckBoxes.get(i).setVisible(true);
            showTimesCheckBoxes.get(i).setText(showTimes.get(i));
        }
    }

    private void clearPanes() {
        moviesTableContainer.getChildren().clear();
    }

    public void updateMovies(List<Movie> movies) {
        TableView tableView = new TableView();

        TableColumn t1 = new TableColumn<Movie, String>("Name");
        TableColumn t2 = new TableColumn<Movie, String>("Genre");
        TableColumn t3 = new TableColumn<Movie, String>("Length");
        TableColumn t4 = new TableColumn<Movie, String>("Show Times");
        TableColumn t5 = new TableColumn<Movie, String>("Date");

        t1.setCellValueFactory(new PropertyValueFactory<>("name"));
        t2.setCellValueFactory(new PropertyValueFactory<>("genre"));
        t3.setCellValueFactory(new PropertyValueFactory<>("length"));
        t4.setCellValueFactory(new PropertyValueFactory<>("showTimes"));
        t5.setCellValueFactory(new PropertyValueFactory<>("movieRunningDate"));

        tableView.getColumns().addAll(t1, t2, t3, t4, t5);
        tableView.getItems().setAll(movies);

        tableView.setRowFactory(tv -> {
            TableRow<Movie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Movie rowData = row.getItem();
                    selectedMovie = rowData;
                    setMovie();
                }
            });
            return row;
        });

        tableView.prefWidthProperty().bind(moviesTableContainer.widthProperty());
        tableView.prefHeightProperty().bind(moviesTableContainer.heightProperty());
        moviesTableContainer.getChildren().add(tableView);
    }

    private void setMovie() {
        clearFields();

        titleField.setText(selectedMovie.getName());
        genreField.setText(selectedMovie.getGenre());
        lengthField.setText(selectedMovie.getLength());
        fromDate.setValue(LocalDate.parse(selectedMovie.getFromDate()));
        toDate.setValue(LocalDate.parse(selectedMovie.getToDate()));
        List<String> showTimes = selectedMovie.getShowTimesList();

        for (int i = 0; i < showTimes.size(); i++) {
            for (int j = 0; j < showTimesCheckBoxes.size(); j++) {
                if (showTimes.get(i).equals(showTimesCheckBoxes.get(j).getText())) {
                    showTimesCheckBoxes.get(j).setSelected(true);
                }
            }
        }
    }

    private void clearFields() {
        titleField.setText("");
        genreField.setText("");
        lengthField.setText("");
        fromDate.setValue(null);
        toDate.setValue(null);

        showTimesCheckBoxes.forEach(box -> box.setSelected(false));
    }

    public void loadMovies() {
        clearPanes();

        MovieService movieService = container.make(MovieService.class);
        List<Movie> movies = movieService.getAll();

        updateMovies(movies);
    }
}
