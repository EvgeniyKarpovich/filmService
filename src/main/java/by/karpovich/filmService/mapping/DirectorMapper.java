package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForFindAll;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoWithAvatar;
import by.karpovich.filmService.jpa.model.DirectorModel;
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
public class DirectorMapper {

    private final CountryService countryService;
    private final FilmRepositoryForMapper filmRepositoryForMapper;
    private final FilmMapper filmMapper;

    public DirectorDtoForFindAll mapDirectorDtoForFindAllFromModel(DirectorModel model) {
        if (model == null) {
            return null;
        }

        return DirectorDtoForFindAll.builder()
                .name(model.getName())
                .avatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()))
                .build();
    }

    public List<DirectorDtoForFindAll> mapListDirectorDtoForFindAllFromListModels(List<DirectorModel> models) {
        if (models == null) {
            return null;
        }

        List<DirectorDtoForFindAll> directors = new ArrayList<>();

        for (DirectorModel model : models) {
            directors.add(mapDirectorDtoForFindAllFromModel(model));
        }
        return directors;
    }

    public DirectorModel mapModelFromDirectorDtoForSaveUpdate(DirectorDtoForSaveUpdate dto, MultipartFile file) {
        if (dto == null) {
            return null;
        }

        return DirectorModel.builder()
                .name(dto.getName())
                .careers(dto.getCareers())
                .avatar(FileUploadDownloadUtil.saveFile(file))
                .dateOfBirth(Utils.mapInstantFromString(dto.getDateOfBirth()))
                .country(countryService.findCountryByIdWhichWillReturnModel(dto.getPlaceOfBirth()))
                .build();
    }

    public DirectorDtoWithAvatar mapDtoWithImageFromModel(DirectorModel model) {
        if (model == null) {
            return null;
        }

        return DirectorDtoWithAvatar.builder()
                .name(model.getName())
                .careers(model.getCareers())
                .avatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()))
                .dateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()))
                .placeOfBirth(model.getCountry().getName())
                .films(filmMapper.mapListFilmDtoForFindAllFromFilmModels(filmRepositoryForMapper.findByDirectorsId(model.getId())))
                .build();
    }
}
