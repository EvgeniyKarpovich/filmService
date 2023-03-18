package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.countryDto.CountryDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.CountryModel;
import by.karpovich.filmService.jpa.repository.CountryRepository;
import by.karpovich.filmService.mapping.CountryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Transactional
    public CountryDto save(CountryDto dto) {
        validateAlreadyExists(dto, null);

        CountryModel entity = countryMapper.mapModelFromDto(dto);
        CountryModel savedCountry = countryRepository.save(entity);

        log.info("method save - the country with name {} saved", savedCountry.getName());
        return countryMapper.mapDtoFromModel(savedCountry);
    }

    public CountryDto findById(Long id) {
        Optional<CountryModel> model = countryRepository.findById(id);
        CountryModel country = model.orElseThrow(
                () -> new NotFoundModelException(String.format("the country with id = %s not found", id)));

        log.info("method findById - the country found with id = {} ", country.getId());
        return countryMapper.mapDtoFromModel(country);
    }

    public List<CountryDto> findAll() {
        List<CountryModel> countries = countryRepository.findAll();

        log.info("method findAll - the number of countries found  = {} ", countries.size());
        return countryMapper.mapListDtoFromListModel(countries);
    }

    @Transactional
    public CountryDto update(Long id, CountryDto dto) {
        validateAlreadyExists(dto, id);

        CountryModel country = countryMapper.mapModelFromDto(dto);
        country.setId(id);
        CountryModel updated = countryRepository.save(country);

        log.info("method update - the country {} updated", updated.getName());
        return countryMapper.mapDtoFromModel(updated);
    }

    @Transactional
    public void deleteById(Long id) {
        if (countryRepository.findById(id).isPresent()) {
            countryRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the country with id = %s not found", id));
        }
        log.info("method deleteById - the country with id = {} deleted", id);
    }

    private void validateAlreadyExists(CountryDto dto, Long id) {
        Optional<CountryModel> model = countryRepository.findByName(dto.getName());
        if (model.isPresent() && !model.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the country with name = %s already exist", model.get().getName()));
        }
    }

    public List<CountryDto> findByName(String name) {
        List<CountryModel> countryModels = countryRepository.findAll();
        List<CountryModel> countriesFilterByName = findByNameCountry(countryModels, name);

        log.info("method findByName - the number of countries found = {} ", countriesFilterByName.size());
        return countryMapper.mapListDtoFromListModel(countriesFilterByName);
    }

    private List<CountryModel> findByNameCountry(List<CountryModel> models, String name) {
        return models.stream()
                .filter(model -> model.getName().matches("(?i).*" + name + ".*"))
                .collect(Collectors.toList());
    }

    public CountryModel findCountryByIdWhichWillReturnModel(Long id) {
        Optional<CountryModel> model = countryRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("the country with ID = " + id + " not found"));
    }
}
