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
public class FilmWithPosterDto {

    private Long id;

    private String name;

    private byte[] poster;

    private String ratingIMDB;

    private String tagline;

    private String releaseDate;

    private Long countryId;

    private List<Long> directorsId;

    private List<Long> genresId;

    private String ageLimit;

    private int durationInMinutes;

    private List<Long> actorsId;

    private String description;
}
