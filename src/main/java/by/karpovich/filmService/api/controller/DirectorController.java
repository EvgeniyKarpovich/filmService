package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.directorDto.DirectorDto;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoWithAvatar;
import by.karpovich.filmService.service.DirectorService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping("/{id}")
    public DirectorDtoWithAvatar findById(@PathVariable("id") Long id) {
        return directorService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DirectorDtoWithAvatar save(@Valid @RequestPart(value = "dto")
                                      @Parameter(schema = @Schema(type = "string", format = "binary")) DirectorDto dto,
                                      @RequestPart("file") MultipartFile file) {
        return directorService.save(dto, file);
    }

    @GetMapping
    public Map<String, Object> findAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        return directorService.findAll(page, size);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DirectorDtoWithAvatar update(@Valid @RequestPart(value = "dto")
                                        @Parameter(schema = @Schema(type = "string", format = "binary")) DirectorDto dto,
                                        @PathVariable("id") Long id, @RequestPart MultipartFile file) {
        return directorService.update(dto, id, file);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        directorService.deleteById(id);
    }
}
