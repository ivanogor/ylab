package homework3.servlet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework3.dto.OperationResultDto;
import homework3.repository.UserRepository;
import homework3.repository.impl.UserRepositoryImpl;
import homework3.service.UserService;
import homework3.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseUserServlet extends HttpServlet {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final UserService userService;

    public BaseUserServlet() {
        UserRepository userRepository = new UserRepositoryImpl();
        this.userService = new UserServiceImpl(userRepository);
    }

    protected void sendResponse(HttpServletResponse resp, OperationResultDto result, int status) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(status);
        resp.getWriter().write(objectMapper.writeValueAsString(result));
    }

    protected void sendErrorResponse(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(status);
        resp.getWriter().write(objectMapper.writeValueAsString(new OperationResultDto(false, message, null)));
    }
}