package repositories;

import com.got.database.DB;
import dto.Theatre;

import java.util.HashMap;
import java.util.Map;

public class TheatreRepository {
    public Theatre getTheatre() {
        return DB.table("theatre").getFirst(Theatre.class);
    }

    public boolean update(Theatre theatre) {
        Map<String, String> updatedValues = new HashMap<>();
        updatedValues.put("name", theatre.getName());
        updatedValues.put("address", theatre.getAddress());
        updatedValues.put("seat_capacity", theatre.getSeatCapacity());
        updatedValues.put("ticket_price", theatre.getTicketPrice());
        updatedValues.put("show_times", theatre.getShowTimesInString());
        int result = DB.table("theatre")
                .set(updatedValues)
                .update();

        return result > 0;
    }
}
