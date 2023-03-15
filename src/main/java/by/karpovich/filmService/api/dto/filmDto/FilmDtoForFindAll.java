package by.karpovich.filmService.api.dto.filmDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDtoForFindAll {

    private String name;

    private byte[] poster;

    private String year;

    private String country;

    private String genre;
}
