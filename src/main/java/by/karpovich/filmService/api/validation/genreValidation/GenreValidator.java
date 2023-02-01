package by.karpovich.filmService.api.validation.genreValidation;

import by.karpovich.filmService.jpa.model.GenreModel;
import by.karpovich.filmService.jpa.repository.GenreRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class GenreValidator implements ConstraintValidator<ValidGenre, Long> {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<GenreModel> model = genreRepository.findById(id);
        return model.isPresent();
    }
}
