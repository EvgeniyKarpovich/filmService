package by.karpovich.filmService.api.validation.genreValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GenreValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGenre {

    String message() default "Genre with this id does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
