package by.karpovich.filmService.api.dto.directorDto;

import by.karpovich.filmService.api.dto.filmDto.FilmDtoForFindAll;
import by.karpovich.filmService.jpa.model.Career;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDtoWithAvatar {

    private String name;

    private byte[] avatar;

    private List<Career> careers;

    private String dateOfBirth;

    private String placeOfBirth;

    private List<FilmDtoForFindAll> films = new ArrayList<>();
}
