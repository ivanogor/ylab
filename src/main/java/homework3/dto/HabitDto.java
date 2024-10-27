package homework3.dto;

import homework3.entity.Habit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HabitDto {
    @NotBlank(message = "Description cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Frequency cannot be null")
    private Habit.Frequency frequency;

    public @NotBlank(message = "Description cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Description cannot be blank") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Description cannot be blank") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description cannot be blank") String description) {
        this.description = description;
    }

    public @NotNull(message = "Frequency cannot be null") Habit.Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(@NotNull(message = "Frequency cannot be null") Habit.Frequency frequency) {
        this.frequency = frequency;
    }
}
