package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.actorDto.ActorDto;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoWithAvatar;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.ActorRepository;
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
    private ActorRepository actorRepository;
    @Autowired
    private CountryService countryService;
    @Autowired
    private FilmRepository filmRepository;

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
        dto.setPlaceOfBirth(findCountryIdFromActorModel(model));
        dto.setHeight(model.getHeight());
        dto.setFilmsId(findFilmsIdFromActorModel(model.getId()));

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
        model.setPlaceOfBirth(countryService.findCountryByIdWhichWillReturnModel(dto.getPlaceOfBirth()));
        model.setHeight(dto.getHeight());
        model.setFilms(findFilmModelsByActorsId(dto.getFilmsId()));

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
        dto.setPlaceOfBirth(findCountryIdFromActorModel(model));
        dto.setHeight(model.getHeight());
        dto.setFilmsId(findFilmsIdFromActorModel(model.getId()));

        return dto;
    }

    public List<Long> findFilmsIdFromActorModel(Long id) {
        ActorModel model = findActorByIdWhichWillReturnModel(id);

        List<FilmModel> films = model.getFilms();

        return films.stream()
                .map(FilmModel::getId)
                .collect(Collectors.toList());
    }

    public List<FilmModel> findFilmModelsByActorsId(List<Long> listFilmId) {
        List<FilmModel> modelList = new ArrayList<>();

        for (Long id : listFilmId) {
            FilmModel filmById = findFilmByIdWhichWillReturnModel(id);
            modelList.add(filmById);
        }

        return modelList;
    }

    public Long findCountryIdFromActorModel(ActorModel model) {
        ActorModel actorModel = findActorByIdWhichWillReturnModel(model.getId());

        return actorModel.getPlaceOfBirth().getId();
    }

    public FilmModel findFilmByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> optionalCountry = filmRepository.findById(id);

        return optionalCountry.orElseThrow(
                () -> new NotFoundModelException("the film with ID = " + id + " not found"));
    }

    public ActorModel findActorByIdWhichWillReturnModel(Long id) {
        Optional<ActorModel> model = actorRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("the actor with ID = " + id + " not found"));
    }
}
