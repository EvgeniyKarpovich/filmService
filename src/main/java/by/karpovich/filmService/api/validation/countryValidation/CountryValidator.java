package by.karpovich.filmService.api.validation.countryValidation;

import by.karpovich.filmService.jpa.model.CountryModel;
import by.karpovich.filmService.jpa.repository.CountryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CountryValidator implements ConstraintValidator<ValidCountry, Long> {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<CountryModel> model = countryRepository.findById(id);
        return model.isPresent();
    }
}
