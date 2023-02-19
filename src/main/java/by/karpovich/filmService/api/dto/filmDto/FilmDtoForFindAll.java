package by.karpovich.filmService.api.dto.filmDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmDtoForFindAll {

    private String name;

    private byte[] poster;

    private String year;

    private String country;

    private String genre;
}
