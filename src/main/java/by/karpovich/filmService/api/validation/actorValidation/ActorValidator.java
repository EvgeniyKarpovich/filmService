package by.karpovich.filmService.api.validation.actorValidation;

import by.karpovich.filmService.jpa.model.ActorModel;
import by.karpovich.filmService.jpa.repository.ActorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ActorValidator implements ConstraintValidator<ValidActor, Long> {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        Optional<ActorModel> model = actorRepository.findById(id);
        return model.isPresent();
    }
}
