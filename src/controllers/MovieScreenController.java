package controllers;

import com.got.alert.Alerter;
import com.got.database.DB;
import dto.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MovieScreenController extends Controller implements Initializable {

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

    List<CheckBox> showTimesCheckBoxes;

    @FXML
    void addMovie(ActionEvent event) {
        Movie movie = container.make(Movie.class);
        movie.setName(titleField.getText());
        movie.setLength(lengthField.getText());
        movie.setGenre(genreField.getText());
        movie.setFromDate(fromDate.getValue().toString());
        movie.setToDate(toDate.getValue().toString());
        movie.setShowTimes(generateShowTimesString());

        Map<String, String> movieValues = new HashMap<String, String>(){
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

        if(row>0) {
            Alerter.showSuccess("Movie has been added successfully!");
        } else {
            Alerter.showError("Unable to add Movie!");
        }
    }

    private String generateShowTimesString() {
        String showTimes = "";
        if(!showTime1.getText().equals(""))
            showTimes += showTime1.getText() + ",";
        if (!showTime2.getText().equals(""))
            showTimes += showTime2.getText() + ",";
        if (!showTime3.getText().equals(""))
            showTimes += showTime3.getText() + ",";
        if (!showTime4.getText().equals(""))
            showTimes += showTime4.getText() + ",";
        if (!showTime5.getText().equals(""))
            showTimes += showTime5.getText() + ",";
        if(showTimes.contains(","))
            showTimes = showTimes.substring(0, showTimes.length()-1);

        return showTimes;
    }

    @FXML
    void deleteMovie(ActionEvent event) {

    }

    @FXML
    void updateMovie(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMovies();

        showTimesCheckBoxes = new ArrayList<CheckBox>(){
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
                    setMovie(rowData);
                }
            });
            return row;
        });

        tableView.prefWidthProperty().bind(moviesTableContainer.widthProperty());
        tableView.prefHeightProperty().bind(moviesTableContainer.heightProperty());
        moviesTableContainer.getChildren().add(tableView);
    }

    private void setMovie(Movie rowData) {
        clearFields();

        titleField.setText(rowData.getName());
        genreField.setText(rowData.getGenre());
        lengthField.setText(rowData.getLength());
        fromDate.setValue(LocalDate.parse(rowData.getFromDate()));
        toDate.setValue(LocalDate.parse(rowData.getToDate()));
        List<String> showTimes = rowData.getShowTimesList();

        for(int i=0; i<showTimes.size(); i++) {
            showTimesCheckBoxes.get(i).setVisible(true);
            showTimesCheckBoxes.get(i).setText(showTimes.get(i));
        }
    }

    private void clearFields() {
        titleField.setText("");
        genreField.setText("");
        lengthField.setText("");
        fromDate.setValue(null);
        toDate.setValue(null);

        showTimesCheckBoxes.forEach(box -> {box.setText(""); box.setVisible(false);});
    }

    public void loadMovies() {
        clearPanes();

        List<Movie> movies = DB.table("movie").get(Movie.class);

        updateMovies(movies);
    }
}
