package dto;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Movie {
    private String name;
    private String id;
    private String length;
    private String genre;
    private String showTimes;
    private String fromDate;
    private String toDate;

    public String getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(String showTimes) {
        this.showTimes = showTimes;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMovieRunningDate() {
        return fromDate + " to " + toDate;
    }

    public List<String> getShowTimesList() {
        List<String> showTimesList = Arrays.stream(showTimes.split(",")).map(String::trim).collect(Collectors.toList());
        return showTimesList;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", length='" + length + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
