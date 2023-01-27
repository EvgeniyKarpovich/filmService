package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.ActorDto;
import by.karpovich.filmService.exception.DuplicateException;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.repository.ActorRepository;
import by.karpovich.filmService.mapping.ActorMapper;
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
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private ActorMapper actorMapper;

    public ActorDto findById(Long id) {
        Optional<ActorModel> optionalFilmModel = actorRepository.findById(id);
        ActorModel actorModel = optionalFilmModel.orElseThrow(
                () -> new NotFoundModelException(String.format("the actor with id = %s was not found", id)));

        log.info("method findById - the actor was founded with id = {} ", actorModel.getId());

        return actorMapper.mapDtoFromModel(actorModel);
    }

    public ActorDto save(ActorDto dto) {
        validateAlreadyExists(dto, null);

        ActorModel model = actorMapper.mapModelFromDto(dto);
        ActorModel save = actorRepository.save(model);

        log.info("method save - the actor with name '{}' was saved", dto.getName());

        return actorMapper.mapDtoFromModel(save);
    }

    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<ActorModel> actorModelPage = actorRepository.findAll(pageable);
        List<ActorModel> content = actorModelPage.getContent();

        List<ActorDto> actorDtoList = actorMapper.mapListDtoFromListModel(content);

        Map<String, Object> response = new HashMap<>();

        response.put("tutorials", actorDtoList);
        response.put("currentPage", actorModelPage.getNumber());
        response.put("totalItems", actorModelPage.getTotalElements());
        response.put("totalPages", actorModelPage.getTotalPages());

        return response;
    }

    public ActorDto update(ActorDto dto, Long id) {
        validateAlreadyExists(dto, id);

        ActorModel model = actorMapper.mapModelFromDto(dto);
        model.setId(id);
        ActorModel save = actorRepository.save(model);

        log.info("method update - the actor {} was updated", dto.getName());

        return actorMapper.mapDtoFromModel(save);
    }

    public void deleteById(Long id) {
        if (actorRepository.findById(id).isPresent()) {
            actorRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the actor with id = %s was not found", id));
        }
        log.info("method deleteById - the actor with id = {} deleted", id);
    }

    private void validateAlreadyExists(ActorDto dto, Long id) {
        Optional<ActorModel> actorModel = actorRepository.findByName(dto.getName());
        if (actorModel.isPresent() && !actorModel.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the actor with id = %s already exist", id));
        }
    }

    public ActorModel findByIdWhichWillReturnModel(Long id) {
        Optional<ActorModel> actorModel = actorRepository.findById(id);

        return actorModel.orElseThrow(
                () -> new NotFoundModelException("the actor with ID = " + id + " was not found"));
    }
}
