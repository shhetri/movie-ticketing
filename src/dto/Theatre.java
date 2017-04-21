package dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Theatre {
    private String id;
    private String name;
    private String address;
    private String seatCapacity;
    private String showTimes;
    private String ticketPrice;

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(String seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public List<String> getShowTimes() {
        return Arrays.stream(showTimes.split(",")).map(String::trim).collect(Collectors.toList());
    }

    public void setShowTimes(String showTimes) {
        this.showTimes = showTimes;
    }

    public String getShowTimesInString() {
        return showTimes;
    }
}
