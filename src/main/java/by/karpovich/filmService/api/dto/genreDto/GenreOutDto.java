package by.karpovich.filmService.api.dto.genreDto;

import by.karpovich.filmService.api.dto.filmDto.FilmDtoForFindAll;
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
public class GenreOutDto {

    private String name;

    private List<FilmDtoForFindAll> films = new ArrayList<>();
}
