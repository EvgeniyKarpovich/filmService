package by.karpovich.filmService.api.dto;

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
public class GenreDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Drama")
    @NotBlank(message = "Enter name")
    private String name;

    @Schema(example = "1, 3")
    @NotNull(message = "Enter film id")
    private List<@ValidFilm Long> filmsId = new ArrayList<>();
}
