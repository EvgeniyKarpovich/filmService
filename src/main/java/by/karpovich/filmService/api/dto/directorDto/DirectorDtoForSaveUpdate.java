package by.karpovich.filmService.api.dto.directorDto;

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
public class DirectorDtoForSaveUpdate {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Guy Ritchie")
    @NotBlank(message = "Enter name")
    private String name;

    private String avatar;

    @Schema(example = "DIRECTOR")
    @NotEmpty(message = "Enter profession")
    private List<Career> careers;

    @Schema(example = "1965-10-10")
    @NotBlank(message = "Enter date of birth")
    private String dateOfBirth;

    @Schema(example = "3")
    @ValidCountry
    @NotNull(message = "Enter country id")
    private Long placeOfBirth;
}
