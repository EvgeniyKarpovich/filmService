package by.karpovich.filmService.api.dto.actorDto;

import by.karpovich.filmService.api.validation.countryValidation.ValidCountry;
import by.karpovich.filmService.api.validation.filmValidation.ValidFilm;
import by.karpovich.filmService.jpa.model.Career;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorDtoForSaveUpdate {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Brad Pitt")
    @NotBlank(message = "Enter name")
    private String name;

    private String avatar;

    @Schema(example = "1972-10-10")
    @NotBlank(message = "Enter date of birth")
    private String dateOfBirth;

    @Schema(example = "ACTOR")
    @NotEmpty(message = "Enter profession")
    private List<Career> careers;

    @Schema(example = "3")
    @ValidCountry
    private Long placeOfBirth;

    @Schema(example = "189")
    @NotNull(message = "Enter height")
    private Integer height;

    @Schema(example = "1, 3")
    @NotNull(message = "Enter film id")
    private List<@ValidFilm Long> filmsId = new ArrayList<>();
}
