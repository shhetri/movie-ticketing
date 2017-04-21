package services;

import dto.Theatre;
import repositories.TheatreRepository;

public class TheatreService {
    private TheatreRepository theatreRepository;

    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    public Theatre getTheatre() {
        return theatreRepository.getTheatre();
    }

    public boolean update(Theatre theatre) {
        return theatreRepository.update(theatre);
    }
}
