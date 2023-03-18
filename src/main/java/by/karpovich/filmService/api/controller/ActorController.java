package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.actorDto.ActorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoOut;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoWithAvatar;
import by.karpovich.filmService.service.ActorService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    private static final String FIND_BY_ID = "/{id}";
    private static final String UPDATE_BY_ID = "/{id}";
    private static final String DELETE_BY_ID = "/{id}";
    private static final String FIND_BY_NAME = "/name/{name}";

    @GetMapping(FIND_BY_ID)
    public ActorDtoOut findById(@PathVariable("id") Long id) {
        return actorService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActorDtoWithAvatar save(@Valid @RequestPart(value = "dto")
                                   @Parameter(schema = @Schema(type = "string", format = "binary")) ActorDtoForSaveUpdate dto,
                                   @RequestPart("file") MultipartFile file) {
        return actorService.save(dto, file);
    }

    @GetMapping
    public Map<String, Object> findAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        return actorService.findAll(page, size);
    }

    @GetMapping(FIND_BY_NAME)
    public Map<String, Object> findAllByName(@RequestParam("name") String name,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        return actorService.findActorsByName(name, page, size);
    }

    @PutMapping(value = UPDATE_BY_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActorDtoWithAvatar update(@Valid @RequestPart(value = "dto")
                                     @Parameter(schema = @Schema(type = "string", format = "binary")) ActorDtoForSaveUpdate dto,
                                     @PathVariable("id") Long id, @RequestPart MultipartFile file) {
        return actorService.update(dto, id, file);
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        actorService.deleteById(id);

        return new ResponseEntity<>("Film successfully deleted", HttpStatus.OK);
    }
}
