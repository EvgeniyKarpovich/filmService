package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.filmDto.FilmDto;
import by.karpovich.filmService.api.dto.filmDto.FilmWithPosterDto;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.FilmRepository;
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
    private CountryMap countryMapper;
    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private DirectorMapper directorMapper;

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
        filmDto.setCountryId(countryMapper.findCountriesIdFromFilmModel(model));
        filmDto.setDirectorsId(directorMapper.findDirectorsIdFromFilmModel(model.getId()));
        filmDto.setGenresId(genreMapper.findGenresIdFromFilmModel(model.getId()));
        filmDto.setAgeLimit(model.getAgeLimit());
        filmDto.setDurationInMinutes(model.getDurationInMinutes());
        filmDto.setActorsId(actorMapper.findActorsIdFromFilmModel(model.getId()));
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
        model.setCountry(countryMapper.findCountryByIdWhichWillReturnModel(dto.getCountryId()));
        model.setDirectors(directorMapper.findDirectorModelsByDirectorId(dto.getDirectorsId()));
        model.setGenres(genreMapper.findGenreModelsByGenreId(dto.getGenresId()));
        model.setAgeLimit(dto.getAgeLimit());
        model.setDurationInMinutes(dto.getDurationInMinutes());
        model.setActors(actorMapper.findActorModelsByActorId(dto.getActorsId()));
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
        dto.setCountryId(countryMapper.findCountriesIdFromFilmModel(model));
        dto.setDirectorsId(directorMapper.findDirectorsIdFromFilmModel(model.getId()));
        dto.setGenresId(genreMapper.findGenresIdFromFilmModel(model.getId()));
        dto.setAgeLimit(model.getAgeLimit());
        dto.setDurationInMinutes(model.getDurationInMinutes());
        dto.setActorsId(actorMapper.findActorsIdFromFilmModel(model.getId()));
        dto.setDescription(model.getDescription());

        return dto;
    }

    public List<FilmModel> findFilmsByGenreId(List<Long> listFilmId) {
        List<FilmModel> filmModelList = new ArrayList<>();

        for (Long id : listFilmId) {
            FilmModel model = findFilmByIdWhichWillReturnModel(id);
            filmModelList.add(model);
        }

        return filmModelList;
    }

    public List<Long> findFilmIdFromGenreModel(Long id) {
        GenreModel genreModel = genreMapper.findGenreByIdWhichWillReturnModel(id);

        List<FilmModel> films = genreModel.getFilms();

        return films.stream()
                .map(FilmModel::getId)
                .collect(Collectors.toList());
    }

    public List<Long> findFilmsIdFromActorModel(Long id) {
        ActorModel model = actorMapper.findActorByIdWhichWillReturnModel(id);

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

    public List<FilmModel> findFilmsByDirectorId(List<Long> listFilmId) {
        List<FilmModel> modelList = new ArrayList<>();

        for (Long id : listFilmId) {
            FilmModel model = findFilmByIdWhichWillReturnModel(id);
            modelList.add(model);
        }

        return modelList;
    }

    public List<Long> findFilmsIdFromDirectorModel(Long id) {
        DirectorModel model = directorMapper.findDirectorByIdWhichWillReturnModel(id);

        List<FilmModel> listFilm = model.getFilms();

        return listFilm.stream()
                .map(FilmModel::getId)
                .collect(Collectors.toList());
    }

    public FilmModel findFilmByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> optionalCountry = filmRepository.findById(id);

        return optionalCountry.orElseThrow(
                () -> new NotFoundModelException("the film with ID = " + id + " was not found"));
    }
}
