package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.filmDto.FilmDto;
import by.karpovich.filmService.api.dto.filmDto.FilmWithPosterDto;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.ActorRepository;
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
public class FilmMapper {

    @Autowired
    private CountryService countryService;
    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private DirectorMapper directorMapper;
    @Autowired
    private ActorRepository actorRepository;

    public FilmDto mapDtoFromModel(FilmModel model, MultipartFile file) {
        if (model == null) {
            return null;
        }

        FilmDto filmDto = new FilmDto();

        String resulFileName = FileUploadDownloadUtil.generationFileName(file);
        FileUploadDownloadUtil.saveFile(resulFileName, file);

        filmDto.setId(model.getId());
        filmDto.setName(model.getName());
        filmDto.setPoster(resulFileName);
        filmDto.setRatingIMDB(model.getRatingIMDB());
        filmDto.setTagline(model.getTagline());
        filmDto.setReleaseDate(Utils.mapStringFromInstant(model.getReleaseDate()));
        filmDto.setCountryId(model.getCountry().getId());
        filmDto.setDirectorsId(findDirectorsIdFromFilmModel(model));
        filmDto.setGenresId(findGenresIdFromFilmModel(model.getGenres()));
        filmDto.setAgeLimit(model.getAgeLimit());
        filmDto.setDurationInMinutes(model.getDurationInMinutes());
        filmDto.setActorsId(findActorsIdFromFilmModel(model));
        filmDto.setDescription(model.getDescription());

        return filmDto;
    }

    public FilmModel mapModelFromDto(FilmDto dto, MultipartFile file) {
        if (dto == null) {
            return null;
        }

        FilmModel model = new FilmModel();

        String resulFileName = FileUploadDownloadUtil.generationFileName(file);
        FileUploadDownloadUtil.saveFile(resulFileName, file);

        model.setName(dto.getName());
        model.setPoster(resulFileName);
        model.setRatingIMDB(dto.getRatingIMDB());
        model.setTagline(dto.getTagline());
        model.setReleaseDate(Utils.mapInstantFromString(dto.getReleaseDate()));
        model.setCountry(countryService.findCountryByIdWhichWillReturnModel(dto.getCountryId()));
        model.setDirectors(directorMapper.findDirectorModelsByDirectorId(dto.getDirectorsId()));
        model.setGenres(genreMapper.findGenreModelsByGenreId(dto.getGenresId()));
        model.setAgeLimit(dto.getAgeLimit());
        model.setDurationInMinutes(dto.getDurationInMinutes());
        model.setActors(findActorModelsByActorId(dto.getActorsId()));
        model.setDescription(dto.getDescription());

        return model;
    }

    public List<FilmWithPosterDto> mapListDtoWithImageFromListModel(List<FilmModel> modelList) {
        if (modelList == null) {
            return null;
        }

        List<FilmWithPosterDto> listDto = new ArrayList<>();

        for (FilmModel model : modelList) {
            listDto.add(mapDtoWithImageFromModel(model));
        }

        return listDto;
    }

    public FilmWithPosterDto mapDtoWithImageFromModel(FilmModel model) {
        if (model == null) {
            return null;
        }

        FilmWithPosterDto dto = new FilmWithPosterDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setPoster(FileUploadDownloadUtil.getImageAsResponseEntity(model.getPoster()));
        dto.setRatingIMDB(model.getRatingIMDB());
        dto.setTagline(model.getTagline());
        dto.setReleaseDate(Utils.mapStringFromInstant(model.getReleaseDate()));
        dto.setCountryId(model.getCountry().getId());
        dto.setDirectorsId(findDirectorsIdFromFilmModel(model));
        dto.setGenresId(findGenresIdFromFilmModel(model.getGenres()));
        dto.setAgeLimit(model.getAgeLimit());
        dto.setDurationInMinutes(model.getDurationInMinutes());
        dto.setActorsId(findActorsIdFromFilmModel(model));
        dto.setDescription(model.getDescription());

        return dto;
    }

    public List<Long> findActorsIdFromFilmModel(FilmModel filmModel) {
        return filmModel.getActors().stream()
                .map(ActorModel::getId)
                .collect(Collectors.toList());
    }

    public List<Long> findDirectorsIdFromFilmModel(FilmModel filmModel) {
        return filmModel.getDirectors().stream()
                .map(DirectorModel::getId)
                .collect(Collectors.toList());
    }

    List<Long> findGenresIdFromFilmModel(List<GenreModel> genres) {
        return genres.stream()
                .map(GenreModel::getId)
                .collect(Collectors.toList());
    }

    public List<ActorModel> findActorModelsByActorId(List<Long> listActorsId) {
        List<ActorModel> actorModels = new ArrayList<>();

        for (Long id : listActorsId) {
            ActorModel model = findActorByIdWhichWillReturnModel(id);
            actorModels.add(model);
        }

        return actorModels;
    }

    public ActorModel findActorByIdWhichWillReturnModel(Long id) {
        Optional<ActorModel> model = actorRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("the actor with ID = " + id + " not found"));
    }
}
