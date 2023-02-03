package by.karpovich.filmService.api.validation.filmValidation;

import by.karpovich.filmService.jpa.model.FilmModel;
import by.karpovich.filmService.jpa.repository.FilmRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class FilmValidator implements ConstraintValidator<ValidFilm, Long> {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<FilmModel> model = filmRepository.findById(id);
        return model.isPresent();
    }
}
