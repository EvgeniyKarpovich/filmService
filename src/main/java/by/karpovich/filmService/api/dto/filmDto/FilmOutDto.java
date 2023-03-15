package by.karpovich.filmService.api.dto.filmDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmOutDto {

    private String name;

    private byte[] poster;

    private String ratingIMDB;

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
