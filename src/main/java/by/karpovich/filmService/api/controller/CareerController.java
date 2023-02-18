package by.karpovich.filmService.api.controller;

import by.karpovich.filmService.api.dto.careerDto.CareerDto;
import by.karpovich.filmService.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/careers")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping("/{id}")
    public CareerDto findById(@PathVariable("id") Long id) {
        return careerService.findById(id);
    }

    @GetMapping
    public Map<String, Object> findAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return careerService.findAll(page, size);
    }

    @PostMapping
    public CareerDto save(@RequestBody CareerDto dto) {
        return careerService.save(dto);
    }

    @PutMapping("/{id}")
    public CareerDto update(@RequestBody CareerDto dto,
                            @PathVariable("id") Long id) {
        return careerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        careerService.deleteById(id);
    }

    @GetMapping("/name/{name}")
    public List<CareerDto> findByName(@PathVariable("name") String name) {
        return careerService.findByName(name);
    }
}
