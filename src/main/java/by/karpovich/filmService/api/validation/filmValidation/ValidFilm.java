package by.karpovich.filmService.api.validation.filmValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FilmValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFilm {

    String message() default "Film with this ID does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
