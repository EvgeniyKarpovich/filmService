package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.filmDto.FilmDto;
import by.karpovich.filmService.api.dto.filmDto.FilmWithPosterDto;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import by.karpovich.filmService.service.ActorService;
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
    private FilmRepository filmRepository;
    @Autowired
    private ActorMapper actorMapper;
    @Autowired
    private CountryService countryService;
    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private DirectorMapper directorMapper;
    @Autowired
    private ActorService actorService;

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
        filmDto.setCountryId(findCountriesIdFromFilmModel(model));
        filmDto.setDirectorsId(findDirectorsIdFromFilmModel(model.getId()));
        filmDto.setGenresId(genreMapper.findGenresIdFromFilmModel(model.getId()));
        filmDto.setAgeLimit(model.getAgeLimit());
        filmDto.setDurationInMinutes(model.getDurationInMinutes());
        filmDto.setActorsId(findActorsIdFromFilmModel(model.getId()));
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
        dto.setCountryId(findCountriesIdFromFilmModel(model));
        dto.setDirectorsId(findDirectorsIdFromFilmModel(model.getId()));
        dto.setGenresId(genreMapper.findGenresIdFromFilmModel(model.getId()));
        dto.setAgeLimit(model.getAgeLimit());
        dto.setDurationInMinutes(model.getDurationInMinutes());
        dto.setActorsId(findActorsIdFromFilmModel(model.getId()));
        dto.setDescription(model.getDescription());

        return dto;
    }

    public List<ActorModel> findActorModelsByActorId(List<Long> listActorsId) {
        List<ActorModel> actorModels = new ArrayList<>();

        for (Long id : listActorsId) {
            ActorModel model = actorMapper.findActorByIdWhichWillReturnModel(id);
            actorModels.add(model);
        }

        return actorModels;
    }

    public List<Long> findActorsIdFromFilmModel(Long id) {
        FilmModel model = findFilmByIdWhichWillReturnModel(id);

        List<ActorModel> actors = model.getActors();

        return actors.stream()
                .map(ActorModel::getId)
                .collect(Collectors.toList());
    }

    public List<Long> findDirectorsIdFromFilmModel(Long id) {
        FilmModel model = findFilmByIdWhichWillReturnModel(id);

        List<DirectorModel> directors = model.getDirectors();

        return directors.stream()
                .map(DirectorModel::getId)
                .collect(Collectors.toList());
    }

    public Long findCountriesIdFromFilmModel(FilmModel filmModel) {
        FilmModel model = findFilmByIdWhichWillReturnModel(filmModel.getId());

        return model.getCountry().getId();
    }

    public FilmModel findFilmByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> optionalCountry = filmRepository.findById(id);

        return optionalCountry.orElseThrow(
                () -> new NotFoundModelException("the film with ID = " + id + " not found"));
    }
}
