package homework3.servlet.user;

import homework3.dto.OperationResultDto;
import homework3.exception.NoPermissionsException;
import homework3.utils.AuthUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/user/getAll")
@Slf4j
public class GetAllUsersServlet extends BaseUserServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Проверка авторизации
            if (!AuthUtils.checkAuthentication(req, resp)) {
                return;
            }

            OperationResultDto result = userService.getAllUsers(req);
            sendResponse(resp, result, HttpServletResponse.SC_OK);
        } catch (NoPermissionsException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            log.warn("Internal server error: " + e.getMessage(), e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}