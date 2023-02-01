package by.karpovich.filmService.api.validation.directorValidation;

import by.karpovich.filmService.jpa.model.DirectorModel;
import by.karpovich.filmService.jpa.repository.DirectorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DirectorValidator implements ConstraintValidator<ValidDirector, Long> {

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<DirectorModel> model = directorRepository.findById(id);
        return model.isPresent();
    }
}
