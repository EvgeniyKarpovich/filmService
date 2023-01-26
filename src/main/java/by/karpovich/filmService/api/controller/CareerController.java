package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.CareerDto;
import by.karpovich.filmService.jpa.repository.CareerRepository;
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
    @Autowired
    private CareerRepository careerRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        CareerDto byId = careerService.findById(id);

        if (byId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> careersDto = careerService.findAll(page, size);

        if (careersDto != null) {
            return new ResponseEntity<>(careersDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CareerDto careerDto) {
        CareerDto save = careerService.save(careerDto);

        if (save == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the career was saved successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CareerDto careerDto,
                                    @PathVariable("id") Long id) {
        CareerDto update = careerService.update(id, careerDto);

        if (update == null) {
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
        List<CareerDto> careerByName = careerService.findByName(name);

        if (careerByName != null) {
            return new ResponseEntity<>(careerByName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
