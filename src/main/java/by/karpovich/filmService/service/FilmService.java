package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.FilmDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import by.karpovich.filmService.mapping.FilmMapper;
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
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmMapper filmMapper;

    public FilmDto findById(Long id) {
        Optional<FilmModel> model = filmRepository.findById(id);

        FilmModel filmModel = model.orElseThrow(
                () -> new NotFoundModelException(String.format("the film with id = %s was not found", id)));
        log.info("method findById - the film was founded with id = {} ", filmModel.getId());

        return filmMapper.mapDtoFromModel(filmModel);
    }

    public FilmDto save(FilmDto filmDto) {
        validateAlreadyExists(filmDto, null);

        FilmModel filmModel = filmMapper.mapModelFromDto(filmDto);
        FilmModel save = filmRepository.save(filmModel);

        log.info("method save - the film with name '{}' was saved", filmDto.getName());

        return filmMapper.mapDtoFromModel(save);
    }

    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<FilmModel> filmModelPageModelPage = filmRepository.findAll(pageable);
        List<FilmModel> content = filmModelPageModelPage.getContent();

        List<FilmDto> filmDtoList = filmMapper.mapListDtoFromListModel(content);

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", filmDtoList);
        response.put("currentPage", filmModelPageModelPage.getNumber());
        response.put("totalItems", filmModelPageModelPage.getTotalElements());
        response.put("totalPages", filmModelPageModelPage.getTotalPages());

        return response;
    }

    public FilmDto update(FilmDto dto, Long id) {
        validateAlreadyExists(dto, id);

        FilmModel filmModel = filmMapper.mapModelFromDto(dto);
        filmModel.setId(id);
        FilmModel save = filmRepository.save(filmModel);

        log.info("method update - the film {} was updated", dto.getName());

        return filmMapper.mapDtoFromModel(save);
    }

    public void deleteById(Long id) {
        if (filmRepository.findById(id).isPresent()) {
            filmRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the film with id = %s was not found", id));
        }
        log.info("method deleteById - the film with id = {} deleted", id);
    }

    private void validateAlreadyExists(FilmDto filmDto, Long id) {
        Optional<FilmModel> filmModel = filmRepository.findByNameAndDescription(filmDto.getName(), filmDto.getDescription());
        if (filmModel.isPresent() && !filmModel.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the film with id = %s already exist", id));
        }
    }

    public FilmModel findByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> optionalCountry = filmRepository.findById(id);

        return optionalCountry.orElseThrow(
                () -> new NotFoundModelException("the film with ID = " + id + " was not found"));
    }
}
