package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.filmDto.FilmDtoForFindAll;
import by.karpovich.filmService.api.dto.filmDto.FilmDtoForSaveUpdate;
import by.karpovich.filmService.api.dto.filmDto.FilmOutDto;
import by.karpovich.filmService.api.dto.filmDto.FilmWithPosterDto;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.ActorRepository;
import by.karpovich.filmService.jpa.repository.DirectorRepository;
import by.karpovich.filmService.jpa.repository.GenreRepository;
import by.karpovich.filmService.service.CountryService;
import by.karpovich.filmService.utils.FileUploadDownloadUtil;
import by.karpovich.filmService.utils.IMDB;
import by.karpovich.filmService.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilmMapper {

    private final CountryService countryService;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;

    public FilmModel mapModelFromDto(FilmDtoForSaveUpdate dto, MultipartFile file) {
        if (dto == null) {
            return null;
        }

        return FilmModel.builder()
                .name(dto.getName())
                .nameFilmFromImdb(dto.getNameFilmFromImdb())
                .poster(FileUploadDownloadUtil.saveFile(file))
                .ratingIMDB(dto.getRatingIMDB())
                .tagline(dto.getTagline())
                .releaseDate(Utils.mapInstantFromString(dto.getReleaseDate()))
                .country(countryService.findCountryByIdWhichWillReturnModel(dto.getCountryId()))
                .directors(findDirectorModelsByDirectorId(dto.getDirectorsId()))
                .genres(findGenreModelsByGenreId(dto.getGenresId()))
                .ageLimit(dto.getAgeLimit())
                .durationInMinutes(dto.getDurationInMinutes())
                .actors(findActorModelsByActorId(dto.getActorsId()))
                .description(dto.getDescription())
                .build();
    }

    public FilmOutDto mapFilmOutDtoFromFilmModel(FilmModel model) {
        if (model == null) {
            return null;
        }

        return FilmOutDto.builder()
                .name(model.getName())
                .poster(FileUploadDownloadUtil.getImageAsResponseEntity(model.getPoster()))
                .ratingIMDB(IMDB.getRatingIMDB(model.getNameFilmFromImdb()))
                .tagline(model.getTagline())
                .releaseDate(Utils.mapStringFromInstant(model.getReleaseDate()))
                .countryName(model.getCountry().getName())
                .directorsName(findDirectorsNameFromFilmModel(model))
                .genresName(findGenresNameFromFilmModel(model))
                .ageLimit(model.getAgeLimit())
                .durationInMinutes(model.getDurationInMinutes())
                .actorsName(findActorsNameFromFilmModel(model))
                .description(model.getDescription())
                .build();
    }

    public FilmDtoForFindAll mapFilmDtoForFindAllFromFilmModel(FilmModel model) {
        if (model == null) {
            return null;
        }

        return FilmDtoForFindAll.builder()
                .name(model.getName())
                .poster(FileUploadDownloadUtil.getImageAsResponseEntity(model.getPoster()))
                .year(Utils.mapStringFromInstant(model.getReleaseDate()))
                .country(model.getCountry().getName())
                .genre(findFirstGenreNameFromFilmModel(model))
                .build();
    }

    public List<DirectorModel> findDirectorModelsByDirectorId(List<Long> listDirectorsId) {
        if (listDirectorsId == null) {
            return null;
        }

        return listDirectorsId.stream()
                .map(this::findDirectorByIdWhichWillReturnModel)
                .toList();
    }

    public List<FilmDtoForFindAll> mapListFilmDtoForFindAllFromFilmModels(List<FilmModel> films) {
        if (films == null) {
            return null;
        }

        return films.stream()
                .map(this::mapFilmDtoForFindAllFromFilmModel)
                .toList();
    }

    public FilmWithPosterDto mapDtoWithImageFromModel(FilmModel model) {
        if (model == null) {
            return null;
        }

        return FilmWithPosterDto.builder()
                .name(model.getName())
                .poster(FileUploadDownloadUtil.getImageAsResponseEntity(model.getPoster()))
                .ratingIMDB(model.getRatingIMDB())
                .tagline(model.getTagline())
                .releaseDate(Utils.mapStringFromInstant(model.getReleaseDate()))
                .countryId(model.getCountry().getId())
                .directorsId(findDirectorsIdFromFilmModel(model))
                .genresId(findGenresIdFromFilmModel(model.getGenres()))
                .ageLimit(model.getAgeLimit())
                .durationInMinutes(model.getDurationInMinutes())
                .actorsId(findActorsIdFromFilmModel(model))
                .description(model.getDescription())
                .build();
    }

    private List<Long> findActorsIdFromFilmModel(FilmModel filmModel) {
        return filmModel.getActors().stream()
                .map(ActorModel::getId)
                .collect(Collectors.toList());
    }

    private List<Long> findDirectorsIdFromFilmModel(FilmModel filmModel) {
        return filmModel.getDirectors().stream()
                .map(DirectorModel::getId)
                .collect(Collectors.toList());
    }

    private List<Long> findGenresIdFromFilmModel(List<GenreModel> genres) {
        return genres.stream()
                .map(GenreModel::getId)
                .collect(Collectors.toList());
    }

    private String findFirstGenreNameFromFilmModel(FilmModel model) {
        return model.getGenres().stream()
                .map(GenreModel::getName)
                .findFirst()
                .get();
    }

    private List<String> findGenresNameFromFilmModel(FilmModel model) {
        return model.getGenres().stream()
                .map(GenreModel::getName)
                .collect(Collectors.toList());
    }

    private List<String> findDirectorsNameFromFilmModel(FilmModel model) {
        return model.getDirectors().stream()
                .map(DirectorModel::getName)
                .collect(Collectors.toList());
    }

    private List<String> findActorsNameFromFilmModel(FilmModel model) {
        return model.getActors().stream()
                .map(ActorModel::getName)
                .collect(Collectors.toList());
    }

    private List<ActorModel> findActorModelsByActorId(List<Long> listActorsId) {
        List<ActorModel> actorModels = new ArrayList<>();

        for (Long id : listActorsId) {
            ActorModel model = findActorByIdWhichWillReturnModel(id);
            actorModels.add(model);
        }

        return actorModels;
    }

    private List<GenreModel> findGenreModelsByGenreId(List<Long> listGenresId) {
        List<GenreModel> genreModels = new ArrayList<>();

        for (Long id : listGenresId) {
            GenreModel model = findGenreByIdWhichWillReturnModel(id);
            genreModels.add(model);
        }

        return genreModels;
    }

    private GenreModel findGenreByIdWhichWillReturnModel(Long id) {
        Optional<GenreModel> genreModel = genreRepository.findById(id);

        return genreModel.orElseThrow(
                () -> new NotFoundModelException("the genre with id = " + id + " not found"));
    }

    private DirectorModel findDirectorByIdWhichWillReturnModel(Long id) {
        Optional<DirectorModel> directorModel = directorRepository.findById(id);

        return directorModel.orElseThrow(
                () -> new NotFoundModelException("the director with id = " + id + " not found"));
    }

    private ActorModel findActorByIdWhichWillReturnModel(Long id) {
        Optional<ActorModel> model = actorRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("the actor with id = " + id + " not found"));
    }
}
