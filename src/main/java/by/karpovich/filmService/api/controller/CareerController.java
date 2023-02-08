package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.careerDto.CareerDto;
import by.karpovich.filmService.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careers")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        CareerDto dto = careerService.findById(id);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> listDto = careerService.findAll(page, size);

        if (listDto != null) {
            return new ResponseEntity<>(listDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CareerDto dto) {
        CareerDto savedDto = careerService.save(dto);

        if (savedDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the career was saved successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CareerDto dto,
                                    @PathVariable("id") Long id) {
        CareerDto updatedDto = careerService.update(id, dto);

        if (updatedDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the career was updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        careerService.deleteById(id);

        return new ResponseEntity<>("the career was deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        List<CareerDto> listCarersByName = careerService.findByName(name);

        if (listCarersByName != null) {
            return new ResponseEntity<>(listCarersByName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
