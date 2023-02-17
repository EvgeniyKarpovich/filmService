package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.genreDto.GenreDto;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GenreMapper {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private FilmMapper filmMapper;

    public GenreDto mapDtoFromModel(GenreModel model) {
        if (model == null) {
            return null;
        }

        GenreDto dto = new GenreDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setFilmsId(filmMapper.findFilmIdFromGenreModel(model.getId()));

        return dto;
    }

    public GenreModel mapModelFromDto(GenreDto dto) {
        if (dto == null) {
            return null;
        }

        GenreModel model = new GenreModel();

        model.setName(dto.getName());
        model.setFilms(filmMapper.findFilmsByGenreId(dto.getFilmsId()));

        return model;
    }

    public List<GenreDto> mapListDtoFromListModel(List<GenreModel> modelList) {
        if (modelList == null) {
            return null;
        }

        List<GenreDto> genreDtoList = new ArrayList<>();
        for (GenreModel genreModel : modelList) {
            genreDtoList.add(mapDtoFromModel(genreModel));
        }

        return genreDtoList;
    }

    public List<GenreModel> findGenreModelsByGenreId(List<Long> listGenresId) {
        List<GenreModel> genreModels = new ArrayList<>();

        for (Long id : listGenresId) {
            GenreModel model = findGenreByIdWhichWillReturnModel(id);
            genreModels.add(model);
        }

        return genreModels;
    }

    public List<Long> findGenresIdFromFilmModel(Long id) {
        FilmModel model = filmMapper.findFilmByIdWhichWillReturnModel(id);

        List<GenreModel> genres = model.getGenres();

        return genres.stream()
                .map(GenreModel::getId)
                .collect(Collectors.toList());
    }

    public GenreModel findGenreByIdWhichWillReturnModel(Long id) {
        Optional<GenreModel> genreModel = genreRepository.findById(id);

        return genreModel.orElseThrow(
                () -> new NotFoundModelException("the genre with ID = " + id + " was not found"));
    }
}
