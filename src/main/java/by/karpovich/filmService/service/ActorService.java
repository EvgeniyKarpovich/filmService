package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.actorDto.ActorDtoForFindAll;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoOut;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoWithAvatar;
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
import org.springframework.web.multipart.MultipartFile;

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

    public ActorDtoOut findById(Long id) {
        Optional<ActorModel> model = actorRepository.findById(id);

        ActorModel actorModel = model.orElseThrow(
                () -> new NotFoundModelException(String.format("the actor with id = %s not found", model.get().getId())));

        log.info("method findById - the actor found with id = {} ", actorModel.getId());

        return actorMapper.mapActorOutDtoFromActorModel(actorModel);
    }

    public ActorDtoWithAvatar save(ActorDtoForSaveUpdate dto, MultipartFile file) {
        validateAlreadyExists(dto, null);

        ActorModel model = actorMapper.mapModelFromDto(dto, file);
        ActorModel save = actorRepository.save(model);

        log.info("method save - the actor with name = {} saved", save.getName());

        return actorMapper.mapDtoWithImageFromModel(save);
    }

    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<ActorModel> actorModelPage = actorRepository.findAll(pageable);
        List<ActorModel> content = actorModelPage.getContent();

        List<ActorDtoForFindAll> actorDtoList = actorMapper.mapListDtoForFindAllFromListActors(content);

        Map<String, Object> response = new HashMap<>();

        response.put("Actors", actorDtoList);
        response.put("currentPage", actorModelPage.getNumber());
        response.put("totalItems", actorModelPage.getTotalElements());
        response.put("totalPages", actorModelPage.getTotalPages());

        log.info("method findAll - the number of actors = {}", actorDtoList.size());

        return response;
    }

    public ActorDtoWithAvatar update(ActorDtoForSaveUpdate dto, Long id, MultipartFile file) {
        validateAlreadyExists(dto, id);

        ActorModel model = actorMapper.mapModelFromDto(dto, file);
        model.setId(id);
        ActorModel save = actorRepository.save(model);

        log.info("method update - the actor with id = {} updated", save.getId());

        return actorMapper.mapDtoWithImageFromModel(save);
    }

    public void deleteById(Long id) {
        if (actorRepository.findById(id).isPresent()) {
            actorRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the actor with id = %s not found", id));
        }
        log.info("method deleteById - the actor with id = {} deleted", id);
    }

    //find films by name contain name ignore case
    public Map<String, Object> findActorsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<ActorModel> actorModelPage = actorRepository.findByNameContainingIgnoreCase(name, pageable);
        List<ActorModel> content = actorModelPage.getContent();

        List<ActorDtoForFindAll> actorDtoWithAvatars = actorMapper.mapListDtoForFindAllFromListActors(content);

        Map<String, Object> response = new HashMap<>();
        response.put("Actors", actorDtoWithAvatars);
        response.put("currentPage", actorModelPage.getNumber());
        response.put("totalItems", actorModelPage.getTotalElements());
        response.put("totalPages", actorModelPage.getTotalPages());

        log.info("method findActorsByName  - the number of actors = {}", actorDtoWithAvatars.size());

        return response;
    }

    private void validateAlreadyExists(ActorDtoForSaveUpdate dto, Long id) {
        Optional<ActorModel> model = actorRepository.findByName(dto.getName());

        if (model.isPresent() && !model.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the actor with name = %s already exist", model.get().getName()));
        }
    }
}
