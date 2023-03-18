package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.countryDto.CountryDto;
import by.karpovich.filmService.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    private static final String FIND_BY_ID = "/{id}";
    private static final String UPDATE_BY_ID = "/{id}";
    private static final String DELETE_BY_ID = "/{id}";
    private static final String FIND_BY_NAME = "/name/{name}";

    @GetMapping(FIND_BY_ID)
    public CountryDto findById(@PathVariable("id") Long id) {
        return countryService.findById(id);
    }

    @GetMapping
    public List<CountryDto> findAll() {
        return countryService.findAll();
    }

    @PostMapping
    public CountryDto save(@RequestBody CountryDto dto) {
        return countryService.save(dto);
    }

    @PutMapping(UPDATE_BY_ID)
    public CountryDto update(@RequestBody CountryDto dto,
                             @PathVariable("id") Long id) {
        return countryService.update(id, dto);
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        countryService.deleteById(id);

        return new ResponseEntity<>("Country successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public List<CountryDto> findByName(@PathVariable("name") String name) {
        return countryService.findByName(name);
    }
}
