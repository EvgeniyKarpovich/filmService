package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.criteriaDto.FilmDtoCriteria;
import by.karpovich.filmService.api.dto.filmDto.FilmDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.filmDto.FilmOutDto;
import by.karpovich.filmService.api.dto.filmDto.FilmWithPosterDto;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import by.karpovich.filmService.service.FilmService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final FilmRepository filmRepository;

    @GetMapping("/{id}")
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

    @GetMapping("/filmsByName/{name}")
    public Map<String, Object> findAllByName(@RequestParam("name") String name,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        return filmService.findFilmsByName(name, page, size);
    }

    @GetMapping("/filmsByGenreId/{genreId}")
    public Map<String, Object> findAllFilmsByGenreId(@RequestParam("genreId") Long genreId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllFilmsByGenreId(genreId, page, size);
    }

    @GetMapping("/filmsByActorId/{actorId}")
    public Map<String, Object> findAllFilmsByActorId(@RequestParam("actorId") Long actorId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllFilmsByActorId(actorId, page, size);
    }

    @GetMapping("/filmsByCountryId/{countryId}")
    public Map<String, Object> findByCountryId(@RequestParam("countryId") Long countryId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllFilmsByCountryId(countryId, page, size);
    }

    @GetMapping("/filmsByDirectorId/{directorId}")
    public Map<String, Object> findByDirectorId(@RequestParam("directorId") Long directorId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllFilmsByDirectorId(directorId, page, size);
    }

    @GetMapping("/byCriteria")
    public Map<String, Object> findByCriteria(FilmDtoCriteria dtoCriteria,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        return filmService.findAllByCriteria(dtoCriteria, page, size);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FilmWithPosterDto update(@Valid @RequestPart(value = "dto")
                                    @Parameter(schema = @Schema(type = "string", format = "binary")) FilmDtoForSaveUpdate dto,
                                    @PathVariable("id") Long id, @RequestPart MultipartFile file) {
        return filmService.update(dto, id, file);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        filmService.deleteById(id);
    }
}
