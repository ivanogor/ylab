package homework3.servlet.habit;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework3.dto.CreateHabitDto;
import homework3.dto.OperationResultDto;
import homework3.exception.HabitAlreadyException;
import homework3.exception.NoPermissionsException;
import homework3.mapper.ObjectMapperConfig;
import homework3.utils.AuthUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@WebServlet("/habit/create")
@Slf4j
public class CreateHabitServlet extends BaseHabitServlet {

    private final ObjectMapper objectMapper = ObjectMapperConfig.createObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Проверка авторизации
            if (!AuthUtils.checkAuthentication(req, resp)) {
                return;
            }

            // Чтение тела запроса в буфер
            String requestBody;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
                requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }

            // Логирование тела запроса
            log.info("Received request to create habit: " + requestBody);

            // Десериализация тела запроса
            CreateHabitDto createHabitDto = objectMapper.readValue(requestBody, CreateHabitDto.class);
            createHabitDto.setRequest(req);

            OperationResultDto result = habitService.createHabit(createHabitDto);
            sendResponse(resp, result, HttpServletResponse.SC_CREATED);
        } catch (NoPermissionsException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_FORBIDDEN, "Access denied");
        } catch (HabitAlreadyException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Habit already exists");
        } catch (Exception e) {
            log.warn("Internal server error: " + e.getMessage(), e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}