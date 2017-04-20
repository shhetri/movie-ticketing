package repositories;

import com.got.database.DB;
import dto.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRepository {
    public List<Movie> getAll() {
        return DB.table("movie").get(Movie.class);
    }

    public void save(Movie movie){
        Map<String, String> pair = new HashMap<String, String >(){
            {
                put("name", movie.getName());
                put("genre", movie.getGenre());
                put("length", movie.getLength());
            }
        };
        DB.table("movie").values(pair).insert();
    }
}
