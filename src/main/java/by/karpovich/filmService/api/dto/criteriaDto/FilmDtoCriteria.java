package by.karpovich.filmService.api.dto.criteriaDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDtoCriteria {

    private String nameGenre;

    private Double rating;

    private String date;
}
