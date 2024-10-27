package homework3.service;

import homework3.dto.*;
import homework3.entity.Habit;
import homework3.entity.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Интерфейс для управления привычками пользователя.
 * Предоставляет методы для создания, обновления, удаления привычек,
 * а также для получения статистики и отчетов о прогрессе.
 */
public interface HabitService {

    /**
     * Создает новую привычку для пользователя.
     *
     * @param createHabitDto Объект DTO, содержащий данные для создания привычки.
     * @return {@code true}, если привычка успешно создана, иначе {@code false}.
     */
    OperationResultDto createHabit(CreateHabitDto createHabitDto);

    /**
     * Обновляет существующую привычку пользователя.
     *
     * @param updateHabitDto Объект DTO, содержащий данные для обновления привычки.
     * @return {@code true}, если привычка успешно обновлена, иначе {@code false}.
     */
    OperationResultDto updateHabit(UpdateHabitDto updateHabitDto);

    /**
     * Удаляет привычку пользователя.
     *
     * @param deleteHabitDto Объект DTO, содержащий данные для удаления привычки.
     * @return {@code true}, если привычка успешно удалена, иначе {@code false}.
     */
    OperationResultDto deleteHabit(DeleteHabitDto deleteHabitDto);

    /**
     * Возвращает список всех привычек пользователя.
     *
     * @param req Пользователь, чьи привычки необходимо получить.
     * @return Список привычек пользователя.
     */
    OperationResultDto getAllHabits(HttpServletRequest req);

    /**
     * Возвращает список привычек пользователя, отфильтрованных по дате создания.
     *
     * @param getHabitsByCreationDateDto Объект DTO, содержащий данные для фильтрации по дате создания.
     * @return Список привычек, отфильтрованных по дате создания.
     */
    OperationResultDto getHabitsByCreationDate(GetHabitsByCreationDateDto getHabitsByCreationDateDto);

    /**
     * Возвращает список привычек пользователя, отфильтрованных по частоте выполнения.
     *
     * @param getHabitsByFrequencyDateDto Объект DTO, содержащий данные для фильтрации по частоте выполнения.
     * @return Список привычек, отфильтрованных по частоте выполнения.
     */
    OperationResultDto getHabitsByFrequency(GetHabitsByFrequencyDateDto getHabitsByFrequencyDateDto);

    /**
     * Отмечает привычку как выполненную.
     *
     * @param completionHabitDto Объект DTO, содержащий данные для отметки привычки как выполненной.
     * @return {@code true}, если привычка успешно отмечена как выполненная, иначе {@code false}.
     */
    OperationResultDto markHabitAsCompleted(CompletionHabitDto completionHabitDto);

    /**
     * Генерирует статистику по привычкам пользователя.
     *
     * @param countHabitCompletionsForPeriodDto Объект DTO, содержащий данные для генерации статистики.
     * @return Количество выполненных привычек.
     */
    OperationResultDto countHabitCompletionsForPeriod(CountHabitCompletionsForPeriodDto countHabitCompletionsForPeriodDto);

    /**
     * Возвращает текущую серию выполненных привычек.
     *
     * @param getCurrentStreakDto Объект DTO, содержащий данные для получения текущей серии.
     * @return Количество дней текущей серии выполненных привычек.
     */
    OperationResultDto getCurrentStreak(GetCurrentStreakDto getCurrentStreakDto);

    /**
     * Возвращает процент выполнения привычек пользователя.
     *
     * @param getCompletionPercentageDto Объект DTO, содержащий данные для получения процента выполнения.
     * @return Процент выполнения привычек.
     */
    OperationResultDto getCompletionPercentage(GetCompletionPercentageDto getCompletionPercentageDto);

    /**
     * Генерирует отчет о прогрессе пользователя.
     *
     * @param generateUserProgressReportDto Объект DTO, содержащий данные для генерации отчета.
     * @return Объект DTO, содержащий отчет о прогрессе пользователя.
     */
    UserProgressReportDto generateUserProgressReport(GenerateUserProgressReportDto generateUserProgressReportDto);

    Habit getHabitByName(User user, String name);
}