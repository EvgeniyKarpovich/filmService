package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.genreDto.GenreDto;
import by.karpovich.filmService.service.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenresService genresService;

    @GetMapping("/{id}")
    public GenreDto findById(@PathVariable("id") Long id) {
        return genresService.findById(id);
    }

    @PostMapping
    public GenreDto save(@RequestBody GenreDto dto) {
        return genresService.save(dto);
    }

    @GetMapping
    public Map<String, Object> findAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        return genresService.findAll(page, size);
    }

    @PutMapping("/{id}")
    public GenreDto update(@RequestBody GenreDto dto,
                           @PathVariable("id") Long id) {
        return genresService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        genresService.deleteById(id);
    }
}
