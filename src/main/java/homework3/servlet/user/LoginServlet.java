package homework3.servlet.user;

import homework3.dto.LoginDto;
import homework3.dto.OperationResultDto;
import homework3.exception.UserNotFoundException;
import homework3.exception.WrongPasswordException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends BaseUserServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LoginDto loginDto = objectMapper.readValue(req.getInputStream(), LoginDto.class);
            OperationResultDto result = userService.login(loginDto);

            // Если аутентификация успешна, сохраняем пользователя в сессии
            if (result.isSuccess()) {
                HttpSession session = req.getSession();
                session.setAttribute("user", result.getData());
            }

            sendResponse(resp, result, HttpServletResponse.SC_OK);
        } catch (UserNotFoundException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (WrongPasswordException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}