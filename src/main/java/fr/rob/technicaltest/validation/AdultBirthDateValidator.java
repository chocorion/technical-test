package fr.rob.technicaltest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AdultBirthDateValidator implements ConstraintValidator<AdultBirthDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return birthDate != null && birthDate.isBefore(LocalDate.now().minusYears(18));
    }
}
