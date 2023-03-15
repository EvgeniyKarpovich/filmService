package by.karpovich.filmService.api.dto.filmDto;

import by.karpovich.filmService.api.validation.actorValidation.ValidActor;
import by.karpovich.filmService.api.validation.countryValidation.ValidCountry;
import by.karpovich.filmService.api.validation.directorValidation.ValidDirector;
import by.karpovich.filmService.api.validation.genreValidation.ValidGenre;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDtoForSaveUpdate {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Poker Face")
    @NotBlank(message = "Enter name")
    private String name;

    @Schema(example = "tt1276104")
    @NotBlank(message = "Enter nameFilmFromImdb")
    private String nameFilmFromImdb;

    private String poster;

    @Schema(example = "8.2")
    @NotBlank(message = "Enter rating")
    private String ratingIMDB;

    @Schema(example = "Never give up")
    @NotBlank(message = "Enter tagline")
    private String tagline;

    @Schema(example = "2009-10-10")
    @NotBlank(message = "Enter release date")
    private String releaseDate;

    @Schema(example = "3")
    @ValidCountry
    @NotNull(message = "Enter country id")
    private Long countryId;

    @Schema(example = "2")
    @NotNull(message = "Enter director id")
    private List<@ValidDirector Long> directorsId;

    @Schema(example = "3, 2")
    @NotNull(message = "Enter genre id")
    private List<@ValidGenre Long> genresId;

    @Schema(example = "16+")
    @NotBlank(message = "Enter age limit")
    private String ageLimit;

    @Schema(example = "145")
    @NotNull(message = "Enter duration")
    private int durationInMinutes;

    @Schema(example = "1, 3")
    @NotNull(message = "Enter actor id")
    private List<@ValidActor Long> actorsId;

    @Schema(example = "Daring, reserved and unlucky Charlie Cale")
    @NotBlank(message = "Enter description")
    private String description;
}
