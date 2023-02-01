package by.karpovich.filmService.api.dto;

import by.karpovich.filmService.api.validation.actorValidation.ValidActor;
import by.karpovich.filmService.api.validation.countryValidation.ValidCountry;
import by.karpovich.filmService.api.validation.directorValidation.ValidDirector;
import by.karpovich.filmService.api.validation.genreValidation.ValidGenre;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "id", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "name", example = "Big father")
    @NotBlank(message = "Enter name")
    private String name;

    @ApiModelProperty(value = "poster", example = "Brad pit.jpg")
    private String poster;

    @ApiModelProperty(value = "Rating", example = "8.9")
    @NotNull(message = "Enter rating")
    private Double ratingIMDB;

    @ApiModelProperty(value = "Tagline", example = "Never give up")
    @NotBlank(message = "Enter tagline")
    private String tagline;

    @ApiModelProperty(value = "Release date", example = "2018-01-10")
    @NotBlank(message = "Enter release date")
    private String releaseDate;

    @ApiModelProperty(value = "Country id", example = "1")
    @ValidCountry
    private Long countryId;

    @ApiModelProperty(value = "Director id", example = "2")
    private List<@ValidDirector Long> directorsId;

    @ApiModelProperty(value = "Genre id", example = "3")
    private List<@ValidGenre Long> genresId;

    @ApiModelProperty(value = "Age limit", example = "16+")
    @NotBlank(message = "Enter age limit")
    private String ageLimit;

    @ApiModelProperty(value = "Duration", example = "145")
    @NotNull(message = "Enter duration")
    private int durationInMinutes;

    @ApiModelProperty(value = "Actor id", example = "3")
    private List<@ValidActor Long> actorsId;

    @ApiModelProperty(value = "Description", example = "The best movie")
    @NotBlank(message = "Enter description")
    private String description;
}
