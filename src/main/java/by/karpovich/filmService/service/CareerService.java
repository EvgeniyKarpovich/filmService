package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.CareerDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.CareerModel;
import by.karpovich.filmService.jpa.repository.CareerRepository;
import by.karpovich.filmService.mapping.CareerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private CareerMapper careerMapper;

    public CareerDto save(CareerDto CareerDto) {
        validateAlreadyExists(CareerDto, null);
        CareerModel entity = careerMapper.mapModelFromDto(CareerDto);
        CareerModel savedCareer = careerRepository.save(entity);
        log.info("method save - the career with name '{}' was saved", CareerDto.getName());
        return careerMapper.mapDtoFromModel(savedCareer);
    }

    public CareerDto findById(Long id) {
        Optional<CareerModel> optionalCountry = careerRepository.findById(id);
        CareerModel careerModel = optionalCountry.orElseThrow(
                () -> new NotFoundModelException(String.format("the career with id = %s was not found", id)));
        log.info("method findById - the career was founded with id = {} ", careerModel.getId());
        return careerMapper.mapDtoFromModel(careerModel);
    }

    public Map<String, Object> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<CareerModel> CareerModelPage = careerRepository.findAll(pageable);
        List<CareerModel> content = CareerModelPage.getContent();

        List<CareerDto> CareerDtoList = careerMapper.mapListDtoFromListModel(content);

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", CareerDtoList);
        response.put("currentPage", CareerModelPage.getNumber());
        response.put("totalItems", CareerModelPage.getTotalElements());
        response.put("totalPages", CareerModelPage.getTotalPages());

        return response;
    }

    public CareerDto update(Long id, CareerDto CareerDto) {
        validateAlreadyExists(CareerDto, id);
        CareerModel career = careerMapper.mapModelFromDto(CareerDto);
        career.setId(id);
        CareerModel updated = careerRepository.save(career);
        log.info("method update - the career {} was updated", CareerDto.getName());
        return careerMapper.mapDtoFromModel(updated);
    }

    public void deleteById(Long id) {
        if (careerRepository.findById(id).isPresent()) {
            careerRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the career with id = %s was not found", id));
        }
        log.info("method deleteById - career with id = {} deleted", id);
    }

    private void validateAlreadyExists(CareerDto CareerDto, Long id) {
        Optional<CareerModel> career = careerRepository.findByName(CareerDto.getName());
        if (career.isPresent() && !career.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the career with id = %s already exist", id));
        }
    }

    public List<CareerDto> findByName(String name) {

        List<CareerModel> careerModels = careerRepository.findAll();
        List<CareerModel> careerFilterByName = findByName(careerModels, name);

        return careerMapper.mapListDtoFromListModel(careerFilterByName);
    }

    private List<CareerModel> findByName(List<CareerModel> models, String name) {
        return models.stream()
                .filter(model -> model.getName().matches("(?i).*" + name + ".*"))
                .collect(Collectors.toList());
    }

}
