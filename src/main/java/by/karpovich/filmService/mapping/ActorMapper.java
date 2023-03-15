package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.actorDto.ActorDtoForFindAll;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoOut;
import by.karpovich.filmService.api.dto.actorDto.ActorDtoWithAvatar;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.repository.FilmRepositoryForMapper;
import by.karpovich.filmService.service.CountryService;
import by.karpovich.filmService.utils.FileUploadDownloadUtil;
import by.karpovich.filmService.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ActorMapper {

    private final CountryService countryService;
    private final FilmRepositoryForMapper filmRepositoryForMapper;
    private final FilmMapper filmMapper;

    public ActorDtoOut mapActorOutDtoFromActorModel(ActorModel model) {
        if (model == null) {
            return null;
        }

        return ActorDtoOut.builder()
                .name(model.getName())
                .professions(model.getCareers())
                .avatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()))
                .dateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()))
                .placeOfBirth(model.getCountry().getName())
                .height(model.getHeight())
                .build();
    }

    public ActorDtoForFindAll mapActorDtoForFindAllFromActorModel(ActorModel model) {
        if (model == null) {
            return null;
        }

        return ActorDtoForFindAll.builder()
                .name(model.getName())
                .avatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()))
                .build();
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

        return ActorModel.builder()
                .name(dto.getName())
                .avatar(FileUploadDownloadUtil.saveFile(file))
                .dateOfBirth(Utils.mapInstantFromString(dto.getDateOfBirth()))
                .country(countryService.findCountryByIdWhichWillReturnModel(dto.getPlaceOfBirth()))
                .careers(dto.getCareers())
                .height(dto.getHeight())
                .build();
    }

    public ActorDtoWithAvatar mapDtoWithImageFromModel(ActorModel model) {
        if (model == null) {
            return null;
        }

        return ActorDtoWithAvatar.builder()
                .name(model.getName())
                .careers(model.getCareers())
                .avatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()))
                .dateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()))
                .placeOfBirth(model.getCountry().getName())
                .height(model.getHeight())
                .films(filmMapper.mapListFilmDtoForFindAllFromFilmModels(filmRepositoryForMapper.findByActorsId(model.getId())))
                .build();
    }
}
