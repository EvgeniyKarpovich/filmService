package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.genreDto.GenreDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.genreDto.GenreOutDto;
import by.karpovich.filmService.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenresService genresService;

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
    public void deleteById(@PathVariable("id") Long id) {
        genresService.deleteById(id);
    }
}
