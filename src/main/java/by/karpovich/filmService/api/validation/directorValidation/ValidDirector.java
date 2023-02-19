package by.karpovich.filmService.api.validation.directorValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DirectorValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDirector {

    String message() default "Director with this id does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
