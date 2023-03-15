package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.genreDto.GenreDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.genreDto.GenreOutDto;
import by.karpovich.filmService.service.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenresService genresService;

    @GetMapping("/{id}")
    public GenreOutDto findById(@PathVariable("id") Long id) {
        return genresService.findById(id);
    }

    @PostMapping
    public GenreOutDto save(@RequestBody GenreDtoForSaveUpdate dto) {
        return genresService.save(dto);
    }

    @GetMapping
    public List<GenreOutDto> findAll() {
        return genresService.findAll();
    }

    @PutMapping("/{id}")
    public GenreOutDto update(@RequestBody GenreDtoForSaveUpdate dto,
                              @PathVariable("id") Long id) {
        return genresService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        genresService.deleteById(id);

        return new ResponseEntity<>("Genre successfully deleted", HttpStatus.OK);
    }
}
