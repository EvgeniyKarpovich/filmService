package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.directorDto.DirectorDto;
import by.karpovich.filmService.api.dto.directorDto.DirectorDtoWithAvatar;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.DirectorRepository;
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
public class DirectorMapper {

    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private FilmMapper filmMapper;
    @Autowired
    private CountryMap countryMapper;

    public DirectorDto mapDtoFromModel(DirectorModel model, MultipartFile file) {
        if (model == null) {
            return null;
        }

        String resulFileName = FileUploadDownloadUtil.generationFileName(file);
        FileUploadDownloadUtil.saveFile(resulFileName, file);

        DirectorDto dto = new DirectorDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setAvatar(resulFileName);
        dto.setDateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()));
        dto.setPlaceOfBirth(countryMapper.findCountryIdFromDirectorModel(model));
        dto.setFilmsId(filmMapper.findFilmsIdFromDirectorModel(model.getId()));

        return dto;
    }

    public DirectorModel mapModelFromDto(DirectorDto dto, MultipartFile file) {
        if (dto == null) {
            return null;
        }

        DirectorModel model = new DirectorModel();

        String resulFileName = FileUploadDownloadUtil.generationFileName(file);
        FileUploadDownloadUtil.saveFile(resulFileName, file);

        model.setName(dto.getName());
        model.setAvatar(resulFileName);
        model.setDateOfBirth(Utils.mapInstantFromString(dto.getDateOfBirth()));
        model.setPlaceOfBirth(countryMapper.findCountryByIdWhichWillReturnModel(dto.getPlaceOfBirth()));
        model.setFilms(filmMapper.findFilmsByDirectorId(dto.getFilmsId()));

        return model;
    }

    public List<DirectorDtoWithAvatar> mapListDtoWithAvatarFromListModel(List<DirectorModel> modelList) {
        if (modelList == null) {
            return null;
        }

        List<DirectorDtoWithAvatar> directorDtoList = new ArrayList<>();

        for (DirectorModel directorModel : modelList) {
            directorDtoList.add(mapDtoWithImageFromModel(directorModel));
        }

        return directorDtoList;
    }

    public DirectorDtoWithAvatar mapDtoWithImageFromModel(DirectorModel model) {
        if (model == null) {
            return null;
        }

        DirectorDtoWithAvatar dto = new DirectorDtoWithAvatar();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setAvatar(FileUploadDownloadUtil.getImageAsResponseEntity(model.getAvatar()));
        dto.setDateOfBirth(Utils.mapStringFromInstant(model.getDateOfBirth()));
        dto.setPlaceOfBirth(countryMapper.findCountryIdFromDirectorModel(model));
        dto.setFilmsId(filmMapper.findFilmsIdFromDirectorModel(model.getId()));

        return dto;
    }

    public List<Long> findDirectorsIdFromFilmModel(Long id) {
        FilmModel model = filmMapper.findFilmByIdWhichWillReturnModel(id);

        List<DirectorModel> directors = model.getDirectors();

        return directors.stream()
                .map(DirectorModel::getId)
                .collect(Collectors.toList());
    }

    public List<DirectorModel> findDirectorModelsByDirectorId(List<Long> listDirectorsId) {
        List<DirectorModel> directorModels = new ArrayList<>();

        for (Long id : listDirectorsId) {
            DirectorModel directorModel = findDirectorByIdWhichWillReturnModel(id);
            directorModels.add(directorModel);
        }

        return directorModels;
    }

    public DirectorModel findDirectorByIdWhichWillReturnModel(Long id) {
        Optional<DirectorModel> directorModel = directorRepository.findById(id);

        return directorModel.orElseThrow(
                () -> new NotFoundModelException("the country with ID = " + id + " was not found"));
    }
}
