package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.genreDto.GenreDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.genreDto.GenreOutDto;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    private final FilmMapper filmMapper;
    private final FilmRepository filmRepository;

    public GenreOutDto mapDtoFromModel(GenreModel model) {
        if (model == null) {
            return null;
        }

        GenreOutDto dto = new GenreOutDto();

        dto.setName(model.getName());
        dto.setFilms(filmMapper.mapListFilmDtoForFindAllFromFilmModels(filmRepository.findByGenresId(model.getId())));

        return dto;
    }

    public GenreModel mapModelFromDto(GenreDtoForSaveUpdate dto) {
        if (dto == null) {
            return null;
        }

        GenreModel model = new GenreModel();

        model.setName(dto.getName());

        return model;
    }

    public List<GenreOutDto> mapListDtoFromListModel(List<GenreModel> modelList) {
        if (modelList == null) {
            return null;
        }

        List<GenreOutDto> genreDtoList = new ArrayList<>();
        for (GenreModel genreModel : modelList) {
            genreDtoList.add(mapDtoFromModel(genreModel));
        }

        return genreDtoList;
    }
}
