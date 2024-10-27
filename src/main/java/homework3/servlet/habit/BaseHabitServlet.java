package homework3.servlet.habit;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework3.dto.OperationResultDto;
import homework3.mapper.ObjectMapperConfig;
import homework3.repository.HabitRepository;
import homework3.repository.impl.HabitRepositoryImpl;
import homework3.service.HabitService;
import homework3.service.impl.HabitServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseHabitServlet extends HttpServlet {
    protected final ObjectMapper objectMapper = ObjectMapperConfig.createObjectMapper();
    protected final HabitService habitService;

    public BaseHabitServlet() {
        HabitRepository habitRepository = new HabitRepositoryImpl();
        this.habitService = new HabitServiceImpl(habitRepository);
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