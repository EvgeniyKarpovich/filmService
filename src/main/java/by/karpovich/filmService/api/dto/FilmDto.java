package by.karpovich.filmService.api.dto;

import by.karpovich.filmService.api.validation.actorValidation.ValidActor;
import by.karpovich.filmService.api.validation.countryValidation.ValidCountry;
import by.karpovich.filmService.api.validation.directorValidation.ValidDirector;
import by.karpovich.filmService.api.validation.genreValidation.ValidGenre;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Enter name")
    private String name;

    private String poster;

    @NotNull(message = "Enter rating")
    private Double ratingIMDB;

    @NotBlank(message = "Enter tagline")
    private String tagline;

    @NotBlank(message = "Enter release date")
    private String releaseDate;

    @ValidCountry
    private Long countryId;

    @NotNull(message = "Enter director id")
    private List<@ValidDirector Long> directorsId;

    @NotNull(message = "Enter genre id")
    private List<@ValidGenre Long> genresId;

    @NotBlank(message = "Enter age limit")
    private String ageLimit;

    @NotNull(message = "Enter duration")
    private int durationInMinutes;

    @NotNull(message = "Enter actor id")
    private List<@ValidActor Long> actorsId;

    @NotBlank(message = "Enter description")
    private String description;
}
