package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.actorDto.ActorDto;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoWithAvatar;
import by.karpovich.filmService.service.ActorService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        ActorDtoWithAvatar dto = actorService.findById(id);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@Valid @RequestPart(value = "dto")
                                  @Parameter(schema = @Schema(type = "string", format = "binary")) ActorDto dto,
                                  @RequestPart("file") MultipartFile file) {
        ActorDtoWithAvatar savedDto = actorService.save(dto, file);

        if (savedDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the actor was saved successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> listDto = actorService.findAll(page, size);

        if (listDto != null) {
            return new ResponseEntity<>(listDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/actorsByName/{name}")
    public ResponseEntity<?> findAllByName(@RequestParam("name") String name,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> actorsByName = actorService.findActorsByName(name, page, size);

        if (actorsByName != null) {
            return new ResponseEntity<>(actorsByName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@Valid @RequestPart(value = "dto")
                                    @Parameter(schema = @Schema(type = "string", format = "binary")) ActorDto dto,
                                    @PathVariable("id") Long id, @RequestPart MultipartFile file) {
        ActorDtoWithAvatar updatedDto = actorService.update(dto, id, file);

        if (updatedDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("the actor was updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        actorService.deleteById(id);

        return new ResponseEntity<>("the actor was deleted successfully", HttpStatus.OK);
    }

}
