package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.DirectorDto;
import by.karpovich.filmService.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        DirectorDto dto = directorService.findById(id);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DirectorDto dto) {
        DirectorDto save = directorService.save(dto);

        if (save == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the director was saved successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> directorDto = directorService.findAll(page, size);

        if (directorDto != null) {
            return new ResponseEntity<>(directorDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody DirectorDto dto,
                                    @PathVariable("id") Long id) {
        DirectorDto update = directorService.update(dto, id);

        if (update == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the director was updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        directorService.deleteById(id);

        return new ResponseEntity<>("the director was deleted successfully", HttpStatus.OK);
    }
}
