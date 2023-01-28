package by.karpovich.filmService.mapping;

import by.karpovich.filmService.api.dto.ActorDto;
import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.ActorRepository;
import by.karpovich.filmService.jpa.repository.CountryRepository;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import by.karpovich.filmService.service.CountryService;
import by.karpovich.filmService.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ActorMapper {

    @Autowired
    private Utils utils;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private FilmRepository filmRepository;

    public ActorDto mapDtoFromModel(ActorModel model) {
        if (model == null) {
            return null;
        }

        ActorDto dto = new ActorDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDateOfBirth(utils.mapStringFromInstant(model.getDateOfBirth()));
        dto.setPlaceOfBirth(findCountryId(model));
        dto.setHeight(model.getHeight());
        dto.setFilmsId(findFilmIdFromActorModel(model.getId()));

        return dto;
    }

    public ActorModel mapModelFromDto(ActorDto dto) {
        if (dto == null) {
            return null;
        }

        ActorModel model = new ActorModel();

        model.setName(dto.getName());
        model.setDateOfBirth(utils.mapInstantFromString(dto.getDateOfBirth()));
        model.setPlaceOfBirth(countryService.findByIdWhichWillReturnModel(dto.getPlaceOfBirth()));
        model.setHeight(dto.getHeight());
        model.setFilms(findListFilmsByActorId(dto.getFilmsId()));

        return model;
    }

    public List<ActorDto> mapListDtoFromListModel(List<ActorModel> modelList) {
        if (modelList == null) {
            return null;
        }

        List<ActorDto> actorDtoList = new ArrayList<>();

        for (ActorModel model : modelList) {
            actorDtoList.add(mapDtoFromModel(model));
        }

        return actorDtoList;
    }

    private List<FilmModel> findListFilmsByActorId(List<Long> listFilmId) {
        List<FilmModel> modelList = new ArrayList<>();

        for (Long id : listFilmId) {
            FilmModel model = findByIdWhichWillReturnModel(id);
            modelList.add(model);
        }

        return modelList;
    }

    private Long findCountryId(ActorModel model) {
        Optional<ActorModel> modelById = actorRepository.findById(model.getId());

        return modelById.get().getPlaceOfBirth().getId();
    }

    private List<Long> findFilmIdFromActorModel(Long id) {
        Optional<ActorModel> model = actorRepository.findById(id);

        List<FilmModel> films = model.get().getFilms();

        return films.stream()
                .map(FilmModel::getId)
                .collect(Collectors.toList());
    }

    public FilmModel findByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> model = filmRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("the film with ID = " + id + " was not found"));
    }
}
