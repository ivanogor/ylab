package homework1.dto;

import homework1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) для отметки привычки как выполненной.
 */
@Data
@AllArgsConstructor
@Builder
public class CompletionHabitDto {

    /**
     * Пользователь, чья привычка отмечается как выполненная.
     */
    private User user;

    /**
     * Имя привычки, которую нужно отметить как выполненную.
     */
    private String habitName;

    /**
     * Дата выполнения привычки.
     */
    private LocalDate date;
}