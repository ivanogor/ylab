package homework1.entity;

import homework1.dto.GenerateHabitStatisticsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс, представляющий привычку пользователя.
 */
@Data
@AllArgsConstructor
@Builder
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
    private List<HabitCompletion> completions;

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

    @Data
    @AllArgsConstructor
    @Builder
    public static class HabitCompletion {

        /**
         * Дата выполнения привычки.
         */
        private LocalDate completionDate;

        /**
         * Флаг, указывающий, была ли привычка выполнена.
         */
        private boolean isCompleted;
    }
}