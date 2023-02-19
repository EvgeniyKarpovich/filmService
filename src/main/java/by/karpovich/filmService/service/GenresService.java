package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.genreDto.GenreDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.GenreRepository;
import by.karpovich.filmService.mapping.GenreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class GenresService {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GenreMapper genreMapper;

    public GenreDto findById(Long id) {
        Optional<GenreModel> model = genreRepository.findById(id);
        GenreModel genresModel = model.orElseThrow(
                () -> new NotFoundModelException(String.format("the genre with id = %s not found", model.get().getId())));

        log.info("method findById - the genre found with id = {} ", genresModel.getId());

        return genreMapper.mapDtoFromModel(genresModel);
    }

    public GenreDto save(GenreDto dto) {
        validateAlreadyExists(dto, null);

        GenreModel model = genreMapper.mapModelFromDto(dto);
        GenreModel savedModel = genreRepository.save(model);

        log.info("method save - the genre with name {} saved", savedModel.getName());

        return genreMapper.mapDtoFromModel(savedModel);
    }

    public List<GenreDto> findAll() {
        List<GenreModel> genres = genreRepository.findAll();

        log.info("method findAll - the genres found {} ", genres.size());

        return genreMapper.mapListDtoFromListModel(genres);
    }

    public GenreDto update(GenreDto dto, Long id) {
        validateAlreadyExists(dto, id);

        GenreModel model = genreMapper.mapModelFromDto(dto);
        model.setId(id);
        GenreModel save = genreRepository.save(model);

        log.info("method update - the genre with id = {} updated", save.getId());

        return genreMapper.mapDtoFromModel(save);
    }

    public void deleteById(Long id) {
        if (genreRepository.findById(id).isPresent()) {
            genreRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the genre with id = %s not found", id));
        }
        log.info("method deleteById - the genre with id = {} deleted", id);
    }

    private void validateAlreadyExists(GenreDto dto, Long id) {
        Optional<GenreModel> genreModel = genreRepository.findByName(dto.getName());

        if (genreModel.isPresent() && !genreModel.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the genre with name = %s already exist", genreModel.get().getName()));
        }
    }
}
