package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.actorDto.ActorDtoForFindAll;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoOut;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoWithAvatar;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import by.karpovich.filmService.service.CountryService;
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
    private CountryService countryService;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmMapper filmMapper;

    public ActorDtoOut mapActorOutDtoFromActorModel(ActorModel model) {
        if (model == null) {
            return null;
        }

        ActorDtoOut dto = new ActorDtoOut();

        dto.setName(model.getName());
        dto.setProfessions(model.getCareers());
        dto.setAvatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()));
        dto.setDateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()));
        dto.setPlaceOfBirth(model.getPlaceOfBirth().getName());
        dto.setHeight(model.getHeight());
        dto.setFilms(filmMapper.mapListFilmDtoForFindAllFromFilmModels(filmRepository.findByActorsId(model.getId())));

        return dto;
    }

    public ActorDtoForFindAll mapActorDtoForFindAllFromActorModel(ActorModel model) {
        if (model == null) {
            return null;
        }

        ActorDtoForFindAll dto = new ActorDtoForFindAll();

        dto.setName(model.getName());
        dto.setAvatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()));

        return dto;
    }

    public List<ActorDtoForFindAll> mapListDtoForFindAllFromListActors(List<ActorModel> models) {
        if (models == null) {
            return null;
        }

        List<ActorDtoForFindAll> actorsDto = new ArrayList<>();

        for (ActorModel model : models) {
            actorsDto.add(mapActorDtoForFindAllFromActorModel(model));
        }

        return actorsDto;
    }

    public ActorModel mapModelFromDto(ActorDtoForSaveUpdate dto, MultipartFile file) {
        if (dto == null) {
            return null;
        }

        ActorModel model = new ActorModel();

        String resulFileName = FileUploadDownloadUtil.generationFileName(file);
        FileUploadDownloadUtil.saveFile(resulFileName, file);

        model.setName(dto.getName());
        model.setCareers(dto.getCareers());
        model.setAvatar(resulFileName);
        model.setDateOfBirth(Utils.mapInstantFromString(dto.getDateOfBirth()));
        model.setPlaceOfBirth(countryService.findCountryByIdWhichWillReturnModel(dto.getPlaceOfBirth()));
        model.setHeight(dto.getHeight());
        model.setFilms(findFilmModelsByActorsId(dto.getFilmsId()));

        return model;
    }

    public ActorDtoWithAvatar mapDtoWithImageFromModel(ActorModel model) {
        if (model == null) {
            return null;
        }

        ActorDtoWithAvatar dto = new ActorDtoWithAvatar();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setCareers(model.getCareers());
        dto.setAvatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()));
        dto.setDateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()));
        dto.setPlaceOfBirth(model.getPlaceOfBirth().getId());
        dto.setHeight(model.getHeight());
        dto.setFilmsId(findFilmsIdFromActorModel(model));

        return dto;
    }

    private List<Long> findFilmsIdFromActorModel(ActorModel actorModel) {
        return actorModel.getFilms().stream()
                .map(FilmModel::getId)
                .collect(Collectors.toList());
    }

    private List<FilmModel> findFilmModelsByActorsId(List<Long> listFilmId) {
        List<FilmModel> modelList = new ArrayList<>();

        for (Long id : listFilmId) {
            FilmModel filmById = findFilmByIdWhichWillReturnModel(id);
            modelList.add(filmById);
        }

        return modelList;
    }

    private FilmModel findFilmByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> filmModel = filmRepository.findById(id);

        return filmModel.orElseThrow(
                () -> new NotFoundModelException("the film with id = " + filmModel.get().getId() + " not found"));
    }
}
