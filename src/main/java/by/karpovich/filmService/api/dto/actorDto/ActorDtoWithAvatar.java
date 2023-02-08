package by.karpovich.filmService.api.dto.actorDto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ActorDtoWithAvatar {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;

    private byte[] avatar;

    private String dateOfBirth;

    private Long placeOfBirth;

    private Integer height;

    private List<Long> filmsId = new ArrayList<>();
}
