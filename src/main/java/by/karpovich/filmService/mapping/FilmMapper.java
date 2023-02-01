package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.FilmDto;
import by.karpovich.filmService.api.dto.FilmWithPosterDto;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.CountryRepository;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import by.karpovich.filmService.service.ActorService;
import by.karpovich.filmService.service.CountryService;
import by.karpovich.filmService.service.DirectorService;
import by.karpovich.filmService.service.GenresService;
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
    private CountryRepository countryRepository;
    @Autowired
    private CountryService countryService;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private DirectorService directorService;
    @Autowired
    private GenresService genresService;
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
        filmDto.setCountryId(findCountryId(model));
        filmDto.setDirectorsId(findDirectorsIdFromFilmModel(model.getId()));
        filmDto.setGenresId(findGenresIdFromFilmModel(model.getId()));
        filmDto.setAgeLimit(model.getAgeLimit());
        filmDto.setDurationInMinutes(model.getDurationInMinutes());
        filmDto.setActorsId(findActorsIdFromModel(model.getId()));
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
        model.setCountry(countryService.findByIdWhichWillReturnModel(dto.getCountryId()));
        model.setDirectors(findListDirectorByDirectorId(dto.getDirectorsId()));
        model.setGenres(findListGenreByGenreId(dto.getGenresId()));
        model.setAgeLimit(dto.getAgeLimit());
        model.setDurationInMinutes(dto.getDurationInMinutes());
        model.setActors(findListActorByActorId(dto.getActorsId()));
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
        FilmWithPosterDto dto = new FilmWithPosterDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setPoster(FileUploadDownloadUtil.getImageAsResponseEntity(model.getPoster()));
        dto.setRatingIMDB(model.getRatingIMDB());
        dto.setTagline(model.getTagline());
        dto.setReleaseDate(Utils.mapStringFromInstant(model.getReleaseDate()));
        dto.setCountryId(findCountryId(model));
        dto.setDirectorsId(findDirectorsIdFromFilmModel(model.getId()));
        dto.setGenresId(findGenresIdFromFilmModel(model.getId()));
        dto.setAgeLimit(model.getAgeLimit());
        dto.setDurationInMinutes(model.getDurationInMinutes());
        dto.setActorsId(findActorsIdFromModel(model.getId()));
        dto.setDescription(model.getDescription());

        return dto;
    }

    private List<Long> findDirectorsIdFromFilmModel(Long id) {
        Optional<FilmModel> model = filmRepository.findById(id);

        List<DirectorModel> directors = model.get().getDirectors();

        return directors.stream()
                .map(DirectorModel::getId)
                .collect(Collectors.toList());
    }

    private List<Long> findGenresIdFromFilmModel(Long id) {
        Optional<FilmModel> byId = filmRepository.findById(id);

        List<GenreModel> genres = byId.get().getGenres();

        return genres.stream()
                .map(GenreModel::getId)
                .collect(Collectors.toList());
    }

    private List<Long> findActorsIdFromModel(Long id) {
        Optional<FilmModel> model = filmRepository.findById(id);

        List<ActorModel> actors = model.get().getActors();

        return actors.stream()
                .map(ActorModel::getId)
                .collect(Collectors.toList());
    }

    private Long findCountryId(FilmModel filmModel) {
        Optional<FilmModel> model = filmRepository.findById(filmModel.getId());

        return model.get().getCountry().getId();
    }

    private List<DirectorModel> findListDirectorByDirectorId(List<Long> listDirectorsId) {
        List<DirectorModel> directorModels = new ArrayList<>();

        for (Long id : listDirectorsId) {
            DirectorModel directorModel = directorService.findByIdWhichWillReturnModel(id);
            directorModels.add(directorModel);
        }

        return directorModels;
    }

    private List<GenreModel> findListGenreByGenreId(List<Long> listGenresId) {
        List<GenreModel> genreModels = new ArrayList<>();

        for (Long id : listGenresId) {
            GenreModel model = genresService.findByIdWhichWillReturnModel(id);
            genreModels.add(model);
        }

        return genreModels;
    }

    private List<ActorModel> findListActorByActorId(List<Long> listActorsId) {
        List<ActorModel> actorModels = new ArrayList<>();

        for (Long id : listActorsId) {
            ActorModel model = actorService.findByIdWhichWillReturnModel(id);
            actorModels.add(model);
        }

        return actorModels;
    }

}
