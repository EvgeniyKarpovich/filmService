package by.karpovich.filmService.api.dto;

import by.karpovich.filmService.api.validation.countryValidation.ValidCountry;
import by.karpovich.filmService.api.validation.filmValidation.ValidFilm;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class ActorDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Brad Pitt")
    @NotBlank(message = "Enter name")
    private String name;

    @Schema(example = "1972-10-10")
    @NotBlank(message = "Enter date of birth")
    private String dateOfBirth;

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
