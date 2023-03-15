package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.genreDto.GenreDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.genreDto.GenreOutDto;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.FilmRepositoryForMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    private final FilmMapper filmMapper;
    private final FilmRepositoryForMapper filmRepositoryForMapper;

    public GenreOutDto mapDtoFromModel(GenreModel model) {
        if (model == null) {
            return null;
        }

        return GenreOutDto.builder()
                .name(model.getName())
                .films(filmMapper.mapListFilmDtoForFindAllFromFilmModels(filmRepositoryForMapper.findByGenresId(model.getId())))
                .build();
    }

    public GenreModel mapModelFromDto(GenreDtoForSaveUpdate dto) {
        if (dto == null) {
            return null;
        }

        return GenreModel.builder()
                .name(dto.getName())
                .build();
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
