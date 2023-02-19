package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForFindAll;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoWithAvatar;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.DirectorRepository;
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

@Component
public class DirectorMapper {

    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private CountryService countryService;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmMapper filmMapper;

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
        model.setFilms(findFilmsByDirectorId(dto.getFilmsId()));

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

    private List<FilmModel> findFilmsByDirectorId(List<Long> listFilmId) {
        List<FilmModel> modelList = new ArrayList<>();

        for (Long id : listFilmId) {
            FilmModel model = findFilmByIdWhichWillReturnModel(id);
            modelList.add(model);
        }

        return modelList;
    }

    private FilmModel findFilmByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> filmModel = filmRepository.findById(id);

        return filmModel.orElseThrow(
                () -> new NotFoundModelException("the film with id = " + filmModel.get().getId() + " not found"));
    }

    private DirectorModel findDirectorByIdWhichWillReturnModel(Long id) {
        Optional<DirectorModel> directorModel = directorRepository.findById(id);

        return directorModel.orElseThrow(
                () -> new NotFoundModelException("the director with id = " + directorModel.get().getId() + " not found"));
    }
}
