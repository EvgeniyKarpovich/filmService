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
        for (ActorModel actorModel : modelList) {
            actorDtoList.add(mapDtoFromModel(actorModel));
        }

        return actorDtoList;
    }

    private List<FilmModel> findListFilmsByActorId(List<Long> listFilmId) {
        List<FilmModel> filmModelList = new ArrayList<>();

        for (Long id : listFilmId) {
            FilmModel model = findByIdWhichWillReturnModel(id);
            filmModelList.add(model);
        }

        return filmModelList;
    }

    private Long findCountryId(ActorModel actorModel) {
        Optional<ActorModel> model = actorRepository.findById(actorModel.getId());

        return model.get().getPlaceOfBirth().getId();
    }

    private List<Long> findFilmIdFromActorModel(Long id) {
        Optional<ActorModel> byId = actorRepository.findById(id);
        List<FilmModel> films = byId.get().getFilms();
        List<Long> collect = films.stream()
                .map(FilmModel::getId)
                .collect(Collectors.toList());

        return collect;
    }

    public FilmModel findByIdWhichWillReturnModel(Long id) {
        Optional<FilmModel> optionalCountry = filmRepository.findById(id);

        return optionalCountry.orElseThrow(
                () -> new NotFoundModelException("the film with ID = " + id + " was not found"));
    }
}
