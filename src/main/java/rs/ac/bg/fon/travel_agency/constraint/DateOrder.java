package rs.ac.bg.fon.travel_agency.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import rs.ac.bg.fon.travel_agency.constraint.validator.DateOrderValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateOrderValidator.class)
public @interface DateOrder {

    String message() default "Invalid date order";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dateFromField();

    String dateToField();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        DateOrder[] value();
    }
}
