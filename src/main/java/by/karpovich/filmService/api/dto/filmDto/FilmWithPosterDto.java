package by.karpovich.filmService.api.dto.filmDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmWithPosterDto {

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
