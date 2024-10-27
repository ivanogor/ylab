package homework3.servlet.user;

import homework3.dto.OperationResultDto;
import homework3.dto.UserDto;
import homework3.exception.UserAlreadyExistException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends BaseUserServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDto userDto = objectMapper.readValue(req.getInputStream(), UserDto.class);
            OperationResultDto result = userService.register(userDto);
            if (result.isSuccess()) {
                HttpSession session = req.getSession();
                session.setAttribute("user", result.getData());
            }

            sendResponse(resp, result, HttpServletResponse.SC_CREATED);
        } catch (UserAlreadyExistException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (Exception e) {
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}