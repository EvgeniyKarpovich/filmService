package by.karpovich.filmService.service;

import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForFindAll;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoWithAvatar;
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
import org.springframework.web.multipart.MultipartFile;

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

    public DirectorDtoWithAvatar findById(Long id) {
        Optional<DirectorModel> directorModel = directorRepository.findById(id);
        DirectorModel model = directorModel.orElseThrow(
                () -> new NotFoundModelException(String.format("the director with id = %s not found", directorModel.get().getId())));

        log.info("method findById - the director found with id = {} ", model.getId());

        return directorMapper.mapDtoWithImageFromModel(model);
    }

    public DirectorDtoWithAvatar save(DirectorDtoForSaveUpdate dto, MultipartFile file) {
        validateAlreadyExists(dto, null);

        DirectorModel model = directorMapper.mapModelFromDirectorDtoForSaveUpdate(dto, file);
        DirectorModel save = directorRepository.save(model);

        log.info("method save - the director with name {} saved", save.getName());

        return directorMapper.mapDtoWithImageFromModel(save);
    }

    public Map<String, Object> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<DirectorModel> directorModelPage = directorRepository.findAll(pageable);
        List<DirectorModel> content = directorModelPage.getContent();

        List<DirectorDtoForFindAll> directorDtoWithAvatars = directorMapper.mapListDirectorDtoForFindAllFromListModels(content);

        Map<String, Object> response = new HashMap<>();

        response.put("Directors", directorDtoWithAvatars);
        response.put("currentPage", directorModelPage.getNumber());
        response.put("totalItems", directorModelPage.getTotalElements());
        response.put("totalPages", directorModelPage.getTotalPages());

        log.info("method save - the number of directors  found {} ", directorDtoWithAvatars.size());

        return response;
    }

    public DirectorDtoWithAvatar update(DirectorDtoForSaveUpdate dto, Long id, MultipartFile file) {
        validateAlreadyExists(dto, id);

        DirectorModel model = directorMapper.mapModelFromDirectorDtoForSaveUpdate(dto, file);
        model.setId(id);
        DirectorModel update = directorRepository.save(model);

        log.info("method update - the director with id = {} updated", update.getId());

        return directorMapper.mapDtoWithImageFromModel(update);
    }

    public void deleteById(Long id) {
        if (directorRepository.findById(id).isPresent()) {
            directorRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format(" the director with id = %s  not found", id));
        }
        log.info("method deleteById - the director with id = {} deleted", id);
    }

    private void validateAlreadyExists(DirectorDtoForSaveUpdate dto, Long id) {
        Optional<DirectorModel> directorModel = directorRepository.findByName(dto.getName());

        if (directorModel.isPresent() && !directorModel.get().getId().equals(id)) {
            throw new DuplicateException(String.format("the director with name = %s already exist", directorModel.get().getName()));
        }
    }
}
