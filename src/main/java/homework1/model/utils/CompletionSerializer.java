package homework1.model.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class CompletionSerializer {
    /**
     * Сериализует список завершений в строку.
     *
     * @return строка, представляющая список завершений.
     */
    public static String serializeCompletions(List<LocalDate> completions) {
        return completions.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(","));
    }

    /**
     * Десериализует строку в список завершений.
     *
     * @param completions строка, представляющая список завершений.
     * @return список завершений.
     */
    public static List<LocalDate> deserializeCompletions(String completions) {
        if (completions == null || completions.isEmpty() || completions.equals("[]")) {
            return Collections.emptyList();
        }
        return Stream.of(completions.split(","))
                .map(LocalDate::parse)
                .collect(Collectors.toList());
    }
}