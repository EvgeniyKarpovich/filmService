package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.criteriaDto.FilmDtoCriteria;
import by.karpovich.filmService.api.dto.filmDto.FilmDtoForFindAll;
import by.karpovich.filmService.api.dto.filmDto.FilmDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.filmDto.FilmOutDto;
import by.karpovich.filmService.api.dto.filmDto.FilmWithPosterDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import by.karpovich.filmService.jpa.specification.FilmSpecificationUtils;
import by.karpovich.filmService.mapping.FilmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    public FilmOutDto findById(Long id) {
        Optional<FilmModel> model = filmRepository.findById(id);

        FilmModel filmModel = model.orElseThrow(
                () -> new NotFoundModelException(String.format("the film with id = %s not found", id)));

        log.info("method findById - the film found with id = {} ", filmModel.getId());

        return filmMapper.mapFilmOutDtoFromFilmModel(filmModel);
    }

    @Transactional
    public FilmWithPosterDto save(FilmDtoForSaveUpdate dto, MultipartFile file) {
        validateAlreadyExists(dto, null);

        FilmModel filmModel = filmMapper.mapModelFromDto(dto, file);
        FilmModel save = filmRepository.save(filmModel);

        log.info("method save - the film with name {} saved", save.getName());

        return filmMapper.mapDtoWithImageFromModel(save);
    }

    @Transactional
    public FilmWithPosterDto update(FilmDtoForSaveUpdate dto, Long id, MultipartFile file) {
        validateAlreadyExists(dto, id);

        FilmModel filmModel = filmMapper.mapModelFromDto(dto, file);
        filmModel.setId(id);
        FilmModel updatedModel = filmRepository.save(filmModel);

        log.info("method update - the film with id =  {} updated", updatedModel.getId());

        return filmMapper.mapDtoWithImageFromModel(updatedModel);
    }

    @Transactional
    public void deleteById(Long id) {
        if (filmRepository.findById(id).isPresent()) {
            filmRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the film with id = %s  not found", id));
        }
        log.info("method deleteById - the film with id = {} deleted", id);
    }

    //find all sort by rating
    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingIMDB").descending());
        Page<FilmModel> filmModelPageModelPage = filmRepository.findAll(pageable);
        List<FilmModel> content = filmModelPageModelPage.getContent();

        List<FilmDtoForFindAll> filmDtoList = filmMapper.mapListFilmDtoForFindAllFromFilmModels(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Films", filmDtoList);
        response.put("currentPage", filmModelPageModelPage.getNumber());
        response.put("totalItems", filmModelPageModelPage.getTotalElements());
        response.put("totalPages", filmModelPageModelPage.getTotalPages());

        log.info("method findAll - the number of films = {}", filmDtoList.size());

        return response;
    }

    //find by criteria sort by rating
    public Map<String, Object> findAllByCriteria(FilmDtoCriteria filmSearchCriteriaDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingIMDB").descending());
        Page<FilmModel> filmModelPageModelPage = filmRepository.findAll(FilmSpecificationUtils.createFromCriteria(filmSearchCriteriaDto), pageable);
        List<FilmModel> content = filmModelPageModelPage.getContent();

        List<FilmDtoForFindAll> filmDtoList = filmMapper.mapListFilmDtoForFindAllFromFilmModels(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Films", filmDtoList);
        response.put("currentPage", filmModelPageModelPage.getNumber());
        response.put("totalItems", filmModelPageModelPage.getTotalElements());
        response.put("totalPages", filmModelPageModelPage.getTotalPages());

        log.info("method findAllByCriteria  - the number of films  = {}", filmDtoList.size());

        return response;
    }

    public Map<String, Object> findAllFilmsByGenreId(Long genreId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingIMDB").descending());
        Page<FilmModel> filmModelPageModelPage = filmRepository.findByGenresId(genreId, pageable);
        List<FilmModel> content = filmModelPageModelPage.getContent();

        List<FilmDtoForFindAll> filmDtoList = filmMapper.mapListFilmDtoForFindAllFromFilmModels(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Films", filmDtoList);
        response.put("currentPage", filmModelPageModelPage.getNumber());
        response.put("totalItems", filmModelPageModelPage.getTotalElements());
        response.put("totalPages", filmModelPageModelPage.getTotalPages());

        log.info("method findAllFilmsByGenreId  - the number of films by genre = {}", filmDtoList.size());

        return response;
    }

    public Map<String, Object> findAllFilmsByActorId(Long genreId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingIMDB").descending());
        Page<FilmModel> filmModelPageModelPage = filmRepository.findByActorsId(genreId, pageable);
        List<FilmModel> content = filmModelPageModelPage.getContent();

        List<FilmDtoForFindAll> filmDtoList = filmMapper.mapListFilmDtoForFindAllFromFilmModels(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Films", filmDtoList);
        response.put("currentPage", filmModelPageModelPage.getNumber());
        response.put("totalItems", filmModelPageModelPage.getTotalElements());
        response.put("totalPages", filmModelPageModelPage.getTotalPages());

        log.info("method findAllFilmsByActorId  - the number of films by actor = {}", filmDtoList.size());

        return response;
    }

    public Map<String, Object> findAllFilmsByCountryId(Long countryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingIMDB").descending());
        Page<FilmModel> filmModelPageModelPage = filmRepository.findByCountryId(countryId, pageable);
        List<FilmModel> content = filmModelPageModelPage.getContent();

        List<FilmDtoForFindAll> filmDtoList = filmMapper.mapListFilmDtoForFindAllFromFilmModels(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Films", filmDtoList);
        response.put("currentPage", filmModelPageModelPage.getNumber());
        response.put("totalItems", filmModelPageModelPage.getTotalElements());
        response.put("totalPages", filmModelPageModelPage.getTotalPages());

        log.info("method findByCountryId  - the number of films by country = {}", filmDtoList.size());

        return response;
    }

    public Map<String, Object> findAllFilmsByDirectorId(Long directorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingIMDB").descending());
        Page<FilmModel> filmModelPageModelPage = filmRepository.findByDirectorsId(directorId, pageable);
        List<FilmModel> content = filmModelPageModelPage.getContent();

        List<FilmDtoForFindAll> filmDtoList = filmMapper.mapListFilmDtoForFindAllFromFilmModels(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Films", filmDtoList);
        response.put("currentPage", filmModelPageModelPage.getNumber());
        response.put("totalItems", filmModelPageModelPage.getTotalElements());
        response.put("totalPages", filmModelPageModelPage.getTotalPages());

        log.info("method findAllFilmsByDirectorId  - the number of films by director = {}", filmDtoList.size());

        return response;
    }

    //find films by name contain name ignore case
    public Map<String, Object> findFilmsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ratingIMDB").descending());
        Page<FilmModel> filmModelPageModelPage = filmRepository.findByNameContainingIgnoreCase(name, pageable);
        List<FilmModel> content = filmModelPageModelPage.getContent();

        List<FilmDtoForFindAll> filmDtoList = filmMapper.mapListFilmDtoForFindAllFromFilmModels(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Films", filmDtoList);
        response.put("currentPage", filmModelPageModelPage.getNumber());
        response.put("totalItems", filmModelPageModelPage.getTotalElements());
        response.put("totalPages", filmModelPageModelPage.getTotalPages());

        log.info("method findFilmsByName  - the number of films = {}", filmDtoList.size());

        return response;
    }

    private void validateAlreadyExists(FilmDtoForSaveUpdate dto, Long id) {
        Optional<FilmModel> filmModel = filmRepository.findByNameAndDescription(dto.getName(), dto.getDescription());
        if (filmModel.isPresent() && !filmModel.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the film with id = %s already exist", id));
        }
    }
}
