package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.criteriaDto.FilmDtoCriteria;
import by.karpovich.filmService.api.dto.filmDto.FilmDto;
import by.karpovich.filmService.api.dto.filmDto.FilmWithPosterDto;
import by.karpovich.filmService.service.FilmService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        FilmWithPosterDto dto = filmService.findById(id);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@Valid @RequestPart(value = "dto")
                                  @Parameter(schema = @Schema(type = "string", format = "binary")) FilmDto dto,
                                  @RequestPart("file") MultipartFile file) {
        FilmWithPosterDto savedDto = filmService.save(dto, file);

        if (savedDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the film was saved successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> listFilmDto = filmService.findAll(page, size);

        if (listFilmDto != null) {
            return new ResponseEntity<>(listFilmDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filmsByName/{name}")
    public ResponseEntity<?> findAll(@RequestParam("name") String name,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> filmsByName = filmService.findFilmsByName(name, page, size);

        if (filmsByName != null) {
            return new ResponseEntity<>(filmsByName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filmsByGenreId/{genreId}")
    public ResponseEntity<?> findAllFilmsByGenreId(@RequestParam("genreId") Long genreId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> listFilmDto = filmService.findAllFilmsByGenreId(genreId, page, size);

        if (listFilmDto != null) {
            return new ResponseEntity<>(listFilmDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filmsByActorId/{actorId}")
    public ResponseEntity<?> findAllFilmsByActorId(@RequestParam("actorId") Long actorId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> listFilmDto = filmService.findAllFilmsByActorId(actorId, page, size);

        if (listFilmDto != null) {
            return new ResponseEntity<>(listFilmDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filmsByCountryId/{countryId}")
    public ResponseEntity<?> findByCountryId(@RequestParam("countryId") Long countryId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> listFilmDto = filmService.findAllFilmsByCountryId(countryId, page, size);

        if (listFilmDto != null) {
            return new ResponseEntity<>(listFilmDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filmsByDirectorId/{directorId}")
    public ResponseEntity<?> findByDirectorId(@RequestParam("directorId") Long directorId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> listFilmDto = filmService.findAllFilmsByDirectorId(directorId, page, size);

        if (listFilmDto != null) {
            return new ResponseEntity<>(listFilmDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byCriteria")
    public ResponseEntity<?> findByCriteria(FilmDtoCriteria dtoCriteria,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> listFilmDto = filmService.findAllByCriteria(dtoCriteria, page, size);

        if (listFilmDto != null) {
            return new ResponseEntity<>(listFilmDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@Valid @RequestPart(value = "dto")
                                    @Parameter(schema = @Schema(type = "string", format = "binary")) FilmDto dto,
                                    @PathVariable("id") Long id, @RequestPart MultipartFile file) {
        FilmWithPosterDto updatedDto = filmService.update(dto, id, file);

        if (updatedDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the film was updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        filmService.deleteById(id);

        return new ResponseEntity<>("the film was deleted successfully", HttpStatus.OK);
    }
}
