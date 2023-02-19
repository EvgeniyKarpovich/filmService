package by.karpovich.filmService.api.validation.actorValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ActorValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidActor {

    String message() default "Actor with this id does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
