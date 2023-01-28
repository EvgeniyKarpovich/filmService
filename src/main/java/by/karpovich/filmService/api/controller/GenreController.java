package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.GenreDto;
import by.karpovich.filmService.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenresService genresService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        GenreDto dto = genresService.findById(id);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody GenreDto dto) {
        GenreDto save = genresService.save(dto);

        if (save == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the genre was saved successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> genreDto = genresService.findAll(page, size);

        if (genreDto != null) {
            return new ResponseEntity<>(genreDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody GenreDto dto,
                                    @PathVariable("id") Long id) {
        GenreDto update = genresService.update(dto, id);

        if (update == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the genre was updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        genresService.deleteById(id);

        return new ResponseEntity<>("the genre was deleted successfully", HttpStatus.OK);
    }

}
