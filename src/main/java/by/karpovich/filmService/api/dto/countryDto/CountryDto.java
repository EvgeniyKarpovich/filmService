package by.karpovich.filmService.api.dto.countryDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Norway")
    @NotBlank(message = "Enter name")
    private String name;
}
