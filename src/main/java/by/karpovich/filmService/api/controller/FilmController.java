package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.criteriaDto.FilmDtoCriteria;
import by.karpovich.filmService.api.dto.filmDto.FilmDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.filmDto.FilmOutDto;
import by.karpovich.filmService.api.dto.filmDto.FilmWithPosterDto;
import by.karpovich.filmService.service.FilmService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    private static final String FIND_BY_ID = "/{id}";
    private static final String UPDATE_BY_ID = "/{id}";
    private static final String DELETE_BY_ID = "/{id}";
    private static final String FIND_BY_GENRE_ID = "/genre/{genreId}";
    private static final String FIND_BY_ACTOR_ID = "/actor/{actorId}";
    private static final String FIND_BY_COUNTRY_ID = "/country/{countryId}";
    private static final String FIND_BY_DIRECTOR_ID = "/director/{directorId}";
    private static final String FIND_BY_CRITERIA = "/criteria";
    private static final String FIND_BY_NAME = "/name/{name}";

    @GetMapping(FIND_BY_ID)
    public FilmOutDto findById(@PathVariable("id") Long id) {
        return filmService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FilmWithPosterDto save(@Valid @RequestPart(value = "dto")
                                  @Parameter(schema = @Schema(type = "string", format = "binary")) FilmDtoForSaveUpdate dto,
                                  @RequestPart("file") MultipartFile file) {
        return filmService.save(dto, file);
    }

    @GetMapping
    public Map<String, Object> findAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        return filmService.findAll(page, size);
    }

//    @GetMapping("/date")
//    public List<FilmModel> findByReleaseDate() {
//        return filmRepository.findAllByOrderByReleaseDateDesc();
//    }

    @GetMapping(FIND_BY_NAME)
    public Map<String, Object> findAllByName(@RequestParam("name") String name,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        return filmService.findFilmsByName(name, page, size);
    }

    @GetMapping(FIND_BY_GENRE_ID)
    public Map<String, Object> findAllFilmsByGenreId(@PathVariable("genreId") Long genreId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllFilmsByGenreId(genreId, page, size);
    }

    @GetMapping(FIND_BY_ACTOR_ID)
    public Map<String, Object> findAllFilmsByActorId(@PathVariable("actorId") Long actorId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllFilmsByActorId(actorId, page, size);
    }

    @GetMapping(FIND_BY_COUNTRY_ID)
    public Map<String, Object> findByCountryId(@PathVariable("countryId") Long countryId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllFilmsByCountryId(countryId, page, size);
    }

    @GetMapping(FIND_BY_DIRECTOR_ID)
    public Map<String, Object> findByDirectorId(@PathVariable("directorId") Long directorId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllFilmsByDirectorId(directorId, page, size);
    }

    @GetMapping(FIND_BY_CRITERIA)
    public Map<String, Object> findByCriteria(@RequestBody FilmDtoCriteria dtoCriteria,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllByCriteria(dtoCriteria, page, size);
    }

    @PutMapping(value = UPDATE_BY_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FilmWithPosterDto update(@Valid @RequestPart(value = "dto")
                                    @Parameter(schema = @Schema(type = "string", format = "binary")) FilmDtoForSaveUpdate dto,
                                    @PathVariable("id") Long id, @RequestPart MultipartFile file) {
        return filmService.update(dto, id, file);
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        filmService.deleteById(id);

        return new ResponseEntity<>("Film successfully deleted", HttpStatus.OK);
    }
}
