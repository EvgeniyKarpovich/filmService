package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.FilmDto;
import by.karpovich.filmService.api.dto.FilmWithPosterDto;
import by.karpovich.filmService.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) throws IOException {
        FilmWithPosterDto dto = filmService.findById(id);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestPart FilmDto dto, @RequestPart("file") MultipartFile file) throws IOException {
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody FilmDto dto,
                                    @PathVariable("id") Long id, MultipartFile file) {
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
