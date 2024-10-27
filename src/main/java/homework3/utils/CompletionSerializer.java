package homework3.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class CompletionSerializer {
    /**
     * Сериализует множество завершений в строку.
     *
     * @return строка, представляющая множество завершений.
     */
    public static String serializeCompletions(Set<LocalDate> completions) {
        return completions.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(","));
    }

    /**
     * Десериализует строку в множество завершений.
     *
     * @param completions строка, представляющая множество завершений.
     * @return множество завершений.
     */
    public static Set<LocalDate> deserializeCompletions(String completions) {
        if (completions == null || completions.isEmpty() || completions.equals("[]")) {
            return Collections.emptySet();
        }
        return Stream.of(completions.split(","))
                .map(LocalDate::parse)
                .collect(Collectors.toSet());
    }
}