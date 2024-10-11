package homework1.command;

import homework1.service.HabitService;
import homework1.utils.UserHolder;

public class GetAllHabitsCommand implements Command {
    private final HabitService habitService;
    private final UserHolder userHolder;

    public GetAllHabitsCommand(HabitService habitService, UserHolder userHolder) {
        this.habitService = habitService;
        this.userHolder = userHolder;
    }

    @Override
    public void execute() {
        System.out.println("All habits:");
        habitService.getAllHabits(userHolder.getUser()).forEach(System.out::println);
    }
}