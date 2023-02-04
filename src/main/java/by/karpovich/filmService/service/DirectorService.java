package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.DirectorDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.repository.DirectorRepository;
import by.karpovich.filmService.mapping.DirectorMapper;
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

@Service
@Transactional
@Slf4j
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private DirectorMapper directorMapper;

    public DirectorDto findById(Long id) {
        Optional<DirectorModel> directorModel = directorRepository.findById(id);
        DirectorModel model = directorModel.orElseThrow(
                () -> new NotFoundModelException(String.format("the director with id = %s was not found", id)));

        log.info("method findById - the director was founded with id = {} ", model.getId());

        return directorMapper.mapDtoFromModel(model);
    }

    public DirectorDto save(DirectorDto dto) {
        validateAlreadyExists(dto, null);

        DirectorModel model = directorMapper.mapModelFromDto(dto);
        DirectorModel save = directorRepository.save(model);

        log.info("method save - the director with name '{}' was saved", dto.getName());

        return directorMapper.mapDtoFromModel(save);
    }

    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<DirectorModel> directorModelPage = directorRepository.findAll(pageable);
        List<DirectorModel> content = directorModelPage.getContent();

        List<DirectorDto> actorDtoList = directorMapper.mapListDtoFromListModel(content);

        Map<String, Object> response = new HashMap<>();

        response.put("Directors", actorDtoList);
        response.put("currentPage", directorModelPage.getNumber());
        response.put("totalItems", directorModelPage.getTotalElements());
        response.put("totalPages", directorModelPage.getTotalPages());

        return response;
    }

    public DirectorDto update(DirectorDto dto, Long id) {
        validateAlreadyExists(dto, id);

        DirectorModel model = directorMapper.mapModelFromDto(dto);
        model.setId(id);
        DirectorModel save = directorRepository.save(model);

        log.info("method update - the director {} was updated", dto.getName());

        return directorMapper.mapDtoFromModel(save);
    }

    public void deleteById(Long id) {
        if (directorRepository.findById(id).isPresent()) {
            directorRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the director with id = %s was not found", id));
        }
        log.info("method deleteById - the director with id = {} deleted", id);
    }

    private void validateAlreadyExists(DirectorDto dto, Long id) {
        Optional<DirectorModel> directorModel = directorRepository.findByName(dto.getName());

        if (directorModel.isPresent() && !directorModel.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the director with id = %s already exist", id));
        }
    }

    public DirectorModel findByIdWhichWillReturnModel(Long id) {
        Optional<DirectorModel> directorModel = directorRepository.findById(id);

        return directorModel.orElseThrow(
                () -> new NotFoundModelException("the country with ID = " + id + " was not found"));
    }
}
