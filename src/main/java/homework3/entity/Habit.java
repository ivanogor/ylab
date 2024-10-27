package homework3.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий привычку пользователя.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class Habit {
    /**
     * Уникальный идентификатор привычки.
     */
    private Long id;

    /**
     * Название привычки.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    /**
     * Описание привычки.
     */

    @NotBlank(message = "Description cannot be blank")
    private String description;

    /**
     * Частота выполнения привычки.
     */
    @NotNull(message = "Frequency cannot be null")
    private Frequency frequency;

    /**
     * Дата создания привычки.
     */
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    /**
     * Список завершений привычки.
     */
    @Builder.Default
    private Set<LocalDate> completions = new HashSet<>();

    /**
     * Флаг, указывающий, была ли привычка выполнена. Будет использоваться для отправки уведомлений.
     */
    @Builder.Default
    private boolean isCompleted = false;

    /**
     * Перечисление, представляющее частоту выполнения привычки.
     */
    public enum Frequency {
        /**
         * Ежедневная частота выполнения привычки.
         */
        DAILY,

        /**
         * Еженедельная частота выполнения привычки.
         */
        WEEKLY
    }

    public @NotBlank(message = "Name cannot be blank") @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Description cannot be blank") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description cannot be blank") String description) {
        this.description = description;
    }

    public @NotNull(message = "Frequency cannot be null") Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(@NotNull(message = "Frequency cannot be null") Frequency frequency) {
        this.frequency = frequency;
    }
}