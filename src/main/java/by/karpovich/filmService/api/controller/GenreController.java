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

    private static final String FIND_BY_ID = "/{id}";
    private static final String UPDATE_BY_ID = "/{id}";
    private static final String DELETE_BY_ID = "/{id}";

    @GetMapping(FIND_BY_ID)
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

    @PutMapping(UPDATE_BY_ID)
    public GenreOutDto update(@RequestBody GenreDtoForSaveUpdate dto,
                              @PathVariable("id") Long id) {
        return genresService.update(dto, id);
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        genresService.deleteById(id);

        return new ResponseEntity<>("Genre successfully deleted", HttpStatus.OK);
    }
}
