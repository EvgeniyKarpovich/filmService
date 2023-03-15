package by.karpovich.filmService.api.dto.actorDto;

import by.karpovich.filmService.api.dto.filmDto.FilmDtoForFindAll;
import by.karpovich.filmService.jpa.model.Career;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActorDtoOut {

    private String name;

    private List<Career> professions;

    private byte[] avatar;

    private String dateOfBirth;

    private String placeOfBirth;

    private Integer height;

    private List<FilmDtoForFindAll> films = new ArrayList<>();
}
