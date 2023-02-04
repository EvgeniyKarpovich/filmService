package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.GenreDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.GenreRepository;
import by.karpovich.filmService.mapping.GenreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                () -> new NotFoundModelException(String.format("the genre with id = %s was not found", id)));

        log.info("method findById - the genre was founded with id = {} ", genresModel.getId());

        return genreMapper.mapDtoFromModel(genresModel);
    }

    public GenreDto save(GenreDto dto) {
        validateAlreadyExists(dto, null);

        GenreModel model = genreMapper.mapModelFromDto(dto);
        GenreModel savedModel = genreRepository.save(model);

        log.info("method save - the genre with name '{}' was saved", dto.getName());

        return genreMapper.mapDtoFromModel(savedModel);
    }

    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<GenreModel> genreModelPage = genreRepository.findAll(pageable);
        List<GenreModel> content = genreModelPage.getContent();

        List<GenreDto> genreDtoList = genreMapper.mapListDtoFromListModel(content);

        Map<String, Object> response = new HashMap<>();

        response.put("Genres", genreDtoList);
        response.put("currentPage", genreModelPage.getNumber());
        response.put("totalItems", genreModelPage.getTotalElements());
        response.put("totalPages", genreModelPage.getTotalPages());

        return response;
    }

    public GenreDto update(GenreDto dto, Long id) {
        validateAlreadyExists(dto, id);

        GenreModel model = genreMapper.mapModelFromDto(dto);
        model.setId(id);
        GenreModel save = genreRepository.save(model);

        log.info("method update - the genre {} was updated", dto.getName());

        return genreMapper.mapDtoFromModel(save);
    }

    public void deleteById(Long id) {
        if (genreRepository.findById(id).isPresent()) {
            genreRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the genre with id = %s was not found", id));
        }
        log.info("method deleteById - the genre with id = {} deleted", id);
    }

    private void validateAlreadyExists(GenreDto dto, Long id) {
        Optional<GenreModel> genreModel = genreRepository.findByName(dto.getName());

        if (genreModel.isPresent() && !genreModel.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the genre with id = %s already exist", id));
        }
    }

    public GenreModel findByIdWhichWillReturnModel(Long id) {
        Optional<GenreModel> genreModel = genreRepository.findById(id);

        return genreModel.orElseThrow(
                () -> new NotFoundModelException("the genre with ID = " + id + " was not found"));
    }
}
