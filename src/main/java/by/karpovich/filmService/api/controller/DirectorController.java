package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoWithAvatar;
import by.karpovich.filmService.service.DirectorService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    private static final String FIND_BY_ID = "/{id}";
    private static final String UPDATE_BY_ID = "/{id}";
    private static final String DELETE_BY_ID = "/{id}";

    @GetMapping(FIND_BY_ID)
    public DirectorDtoWithAvatar findById(@PathVariable("id") Long id) {
        return directorService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DirectorDtoWithAvatar save(@Valid @RequestPart(value = "dto")
                                      @Parameter(schema = @Schema(type = "string", format = "binary")) DirectorDtoForSaveUpdate dto,
                                      @RequestPart("file") MultipartFile file) {
        return directorService.save(dto, file);
    }

    @GetMapping
    public Map<String, Object> findAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        return directorService.findAll(page, size);
    }

    @PutMapping(value = UPDATE_BY_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DirectorDtoWithAvatar update(@Valid @RequestPart(value = "dto")
                                        @Parameter(schema = @Schema(type = "string", format = "binary")) DirectorDtoForSaveUpdate dto,
                                        @PathVariable("id") Long id, @RequestPart MultipartFile file) {
        return directorService.update(dto, id, file);
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        directorService.deleteById(id);

        return new ResponseEntity<>("Director successfully deleted", HttpStatus.OK);
    }
}
