package homework3.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import homework3.dto.UpdateHabitDto;
import homework3.dto.UpdateUserDto;

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {

    @Override
    public void initialize(AtLeastOneNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        if (dto instanceof UpdateHabitDto) {
            UpdateHabitDto habitDto = (UpdateHabitDto) dto;
            return isNotEmpty(habitDto.getNewHabitName()) ||
                    isNotEmpty(habitDto.getNewHabitDescription()) ||
                    habitDto.getNewFrequency() != null;
        } else if (dto instanceof UpdateUserDto userDto) {
            return isNotEmpty(userDto.getNewName()) ||
                    isNotEmpty(userDto.getNewEmail()) ||
                    isNotEmpty(userDto.getNewPassword());
        }

        return false;
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}