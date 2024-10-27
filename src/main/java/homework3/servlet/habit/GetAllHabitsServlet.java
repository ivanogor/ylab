package homework3.servlet.habit;

import homework3.dto.OperationResultDto;
import homework3.utils.AuthUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/habit/getAll")
@Slf4j
public class GetAllHabitsServlet extends BaseHabitServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Проверка авторизации
            if (!AuthUtils.checkAuthentication(req, resp)) {
                return;
            }

            OperationResultDto result = habitService.getAllHabits(req);
            sendResponse(resp, result, HttpServletResponse.SC_OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}