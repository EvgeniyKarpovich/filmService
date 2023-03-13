package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForFindAll;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoWithAvatar;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.repository.FilmRepository;
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
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    public DirectorDtoForFindAll mapDirectorDtoForFindAllFromModel(DirectorModel model) {
        if (model == null) {
            return null;
        }

        DirectorDtoForFindAll dto = new DirectorDtoForFindAll();

        dto.setName(model.getName());
        dto.setAvatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()));

        return dto;
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

        DirectorModel model = new DirectorModel();

        String resulFileName = FileUploadDownloadUtil.generationFileName(file);
        FileUploadDownloadUtil.saveFile(resulFileName, file);

        model.setName(dto.getName());
        model.setCareers(dto.getCareers());
        model.setAvatar(resulFileName);
        model.setDateOfBirth(Utils.mapInstantFromString(dto.getDateOfBirth()));
        model.setPlaceOfBirth(countryService.findCountryByIdWhichWillReturnModel(dto.getPlaceOfBirth()));

        return model;
    }

    public DirectorDtoWithAvatar mapDtoWithImageFromModel(DirectorModel model) {
        if (model == null) {
            return null;
        }

        DirectorDtoWithAvatar dto = new DirectorDtoWithAvatar();

        dto.setName(model.getName());
        dto.setCareers(model.getCareers());
        dto.setAvatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()));
        dto.setDateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()));
        dto.setPlaceOfBirth(model.getPlaceOfBirth().getName());
        dto.setFilms(filmMapper.mapListFilmDtoForFindAllFromFilmModels(filmRepository.findByDirectorsId(model.getId())));

        return dto;
    }
}
