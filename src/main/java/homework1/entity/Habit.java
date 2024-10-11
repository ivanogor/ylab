package homework1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс, представляющий привычку пользователя.
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class Habit {
    /**
     * Название привычки.
     */
    private String name;

    /**
     * Описание привычки.
     */
    private String description;

    /**
     * Частота выполнения привычки.
     */
    private Frequency frequency;

    /**
     * Дата создания привычки.
     */
    private LocalDate creationDate;

    /**
     * Список завершений привычки.
     */
    private List<LocalDate> completions;

    /**
     * Флаг, указывающий, была ли привычка выполнена. Будет использоваться для отправки уведомлений.
     */
    private boolean isCompleted;

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
}