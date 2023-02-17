package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.actorDto.ActorDto;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoWithAvatar;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.ActorRepository;
import by.karpovich.filmService.utils.FileUploadDownloadUtil;
import by.karpovich.filmService.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ActorMapper {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private FilmMapper filmMapper;
    @Autowired
    private CountryMap countryMapper;

    public ActorDto mapDtoFromModel(ActorModel model, MultipartFile file) {
        if (model == null) {
            return null;
        }

        String resulFileName = FileUploadDownloadUtil.generationFileName(file);
        FileUploadDownloadUtil.saveFile(resulFileName, file);

        ActorDto dto = new ActorDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setAvatar(resulFileName);
        dto.setDateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()));
        dto.setPlaceOfBirth(countryMapper.findCountryIdFromActorModel(model));
        dto.setHeight(model.getHeight());
        dto.setFilmsId(filmMapper.findFilmsIdFromActorModel(model.getId()));

        return dto;
    }

    public ActorModel mapModelFromDto(ActorDto dto, MultipartFile file) {
        if (dto == null) {
            return null;
        }

        ActorModel model = new ActorModel();

        String resulFileName = FileUploadDownloadUtil.generationFileName(file);
        FileUploadDownloadUtil.saveFile(resulFileName, file);

        model.setName(dto.getName());
        model.setAvatar(resulFileName);
        model.setDateOfBirth(Utils.mapInstantFromString(dto.getDateOfBirth()));
        model.setPlaceOfBirth(countryMapper.findCountryByIdWhichWillReturnModel(dto.getPlaceOfBirth()));
        model.setHeight(dto.getHeight());
        model.setFilms(filmMapper.findFilmModelsByActorsId(dto.getFilmsId()));

        return model;
    }

    public List<ActorDtoWithAvatar> mapListDtoWithAvatarFromListModel(List<ActorModel> modelList) {
        if (modelList == null) {
            return null;
        }

        List<ActorDtoWithAvatar> actorDtoList = new ArrayList<>();

        for (ActorModel model : modelList) {
            actorDtoList.add(mapDtoWithImageFromModel(model));
        }

        return actorDtoList;
    }

    public ActorDtoWithAvatar mapDtoWithImageFromModel(ActorModel model) {
        if (model == null) {
            return null;
        }

        ActorDtoWithAvatar dto = new ActorDtoWithAvatar();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setAvatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()));
        dto.setDateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()));
        dto.setPlaceOfBirth(countryMapper.findCountryIdFromActorModel(model));
        dto.setHeight(model.getHeight());
        dto.setFilmsId(filmMapper.findFilmsIdFromActorModel(model.getId()));

        return dto;
    }

    public List<ActorModel> findActorModelsByActorId(List<Long> listActorsId) {
        List<ActorModel> actorModels = new ArrayList<>();

        for (Long id : listActorsId) {
            ActorModel model = findActorByIdWhichWillReturnModel(id);
            actorModels.add(model);
        }

        return actorModels;
    }

    public List<Long> findActorsIdFromFilmModel(Long id) {
        FilmModel model = filmMapper.findFilmByIdWhichWillReturnModel(id);

        List<ActorModel> actors = model.getActors();

        return actors.stream()
                .map(ActorModel::getId)
                .collect(Collectors.toList());
    }

    public ActorModel findActorByIdWhichWillReturnModel(Long id) {
        Optional<ActorModel> model = actorRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("the actor with ID = " + id + " was not found"));
    }
}
