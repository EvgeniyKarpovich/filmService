package by.karpovich.filmService.api.dto.filmDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmOutDto {

    private String name;

    private byte[] poster;

    private Double ratingIMDB;

    private String tagline;

    private String releaseDate;

    private String countryName;

    private List<String> directorsName;

    private List<String> genresName;

    private String ageLimit;

    private int durationInMinutes;

    private List<String> actorsName;

    private String description;
}
