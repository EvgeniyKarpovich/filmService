package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.CountryDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.CountryModel;
import by.karpovich.filmService.jpa.repository.CountryRepository;
import by.karpovich.filmService.mapping.CountryMapper;
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
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryMapper countryMapper;

    public CountryDto save(CountryDto dto) {
        validateAlreadyExists(dto, null);

        CountryModel entity = countryMapper.mapModelFromDto(dto);
        CountryModel savedCountry = countryRepository.save(entity);

        log.info("method save - the country with name '{}' was saved", dto.getName());

        return countryMapper.mapDtoFromModel(savedCountry);
    }

    public CountryDto findById(Long id) {
        Optional<CountryModel> model = countryRepository.findById(id);
        CountryModel country = model.orElseThrow(
                () -> new NotFoundModelException(String.format("the country with id = %s was not found", id)));

        log.info("method findById - the country was founded with id = {} ", country.getId());

        return countryMapper.mapDtoFromModel(country);
    }

    public Map<String, Object> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<CountryModel> countryModelPage = countryRepository.findAll(pageable);
        List<CountryModel> content = countryModelPage.getContent();

        List<CountryDto> countryDtoList = countryMapper.mapListDtoFromListModel(content);

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", countryDtoList);
        response.put("currentPage", countryModelPage.getNumber());
        response.put("totalItems", countryModelPage.getTotalElements());
        response.put("totalPages", countryModelPage.getTotalPages());

        return response;
    }

    public CountryDto update(Long id, CountryDto dto) {
        validateAlreadyExists(dto, id);

        CountryModel country = countryMapper.mapModelFromDto(dto);
        country.setId(id);
        CountryModel updated = countryRepository.save(country);

        log.info("method update - the country {} was updated", dto.getName());

        return countryMapper.mapDtoFromModel(updated);
    }

    public void deleteById(Long id) {
        if (countryRepository.findById(id).isPresent()) {
            countryRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the country with id = %s was not found", id));
        }
        log.info("method deleteById - the country with id = {} deleted", id);
    }

    private void validateAlreadyExists(CountryDto dto, Long id) {
        Optional<CountryModel> model = countryRepository.findByName(dto.getName());
        if (model.isPresent() && !model.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the country with id = %s already exist", id));
        }
    }

    public List<CountryDto> findByName(String name) {
        List<CountryModel> countryModels = countryRepository.findAll();
        List<CountryModel> countriesFilterByName = findByName(countryModels, name);

        return countryMapper.mapListDtoFromListModel(countriesFilterByName);
    }

    public CountryModel findByIdWhichWillReturnModel(Long id) {
        Optional<CountryModel> model = countryRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("the country with ID = " + id + " was not found"));
    }

    private List<CountryModel> findByName(List<CountryModel> models, String name) {
        return models.stream()
                .filter(model -> model.getName().matches("(?i).*" + name + ".*"))
                .collect(Collectors.toList());
    }
}
