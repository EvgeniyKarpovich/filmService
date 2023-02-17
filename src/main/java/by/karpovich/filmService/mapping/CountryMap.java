package by.karpovich.filmService.mapping;

import by.karpovich.filmService.exception.NotFoundModelException;
import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.model.CountryModel;
import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CountryMap {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private DirectorMapper directorMapper;
    @Autowired
    private FilmMapper filmMapper;
    @Autowired
    private ActorMapper actorMapper;

    public CountryModel findCountryByIdWhichWillReturnModel(Long id) {
        Optional<CountryModel> model = countryRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("the country with ID = " + id + " was not found"));
    }

    public Long findCountriesIdFromFilmModel(FilmModel filmModel) {
        FilmModel model = filmMapper.findFilmByIdWhichWillReturnModel(filmModel.getId());

        return model.getCountry().getId();
    }

    public Long findCountryIdFromDirectorModel(DirectorModel model) {
        DirectorModel directorModel = directorMapper.findDirectorByIdWhichWillReturnModel(model.getId());

        return directorModel.getPlaceOfBirth().getId();
    }

    public Long findCountryIdFromActorModel(ActorModel model) {
        ActorModel actorModel = actorMapper.findActorByIdWhichWillReturnModel(model.getId());

        return actorModel.getPlaceOfBirth().getId();
    }

}
