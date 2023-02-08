package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.countryDto.CountryDto;
import by.karpovich.filmService.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        CountryDto dto = countryService.findById(id);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> listDto = countryService.findAll(page, size);

        if (listDto != null) {
            return new ResponseEntity<>(listDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CountryDto dto) {
        CountryDto savedDto = countryService.save(dto);

        if (savedDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the country was saved successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CountryDto dto,
                                    @PathVariable("id") Long id) {
        CountryDto updatedDto = countryService.update(id, dto);

        if (updatedDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the country was updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        countryService.deleteById(id);

        return new ResponseEntity<>("the country was deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        List<CountryDto> listCountryByName = countryService.findByName(name);

        if (listCountryByName != null) {
            return new ResponseEntity<>(listCountryByName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
