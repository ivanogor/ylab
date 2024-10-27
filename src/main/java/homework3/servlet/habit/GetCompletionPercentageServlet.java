package homework3.servlet.habit;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework3.dto.GetCompletionPercentageDto;
import homework3.dto.OperationResultDto;
import homework3.exception.HabitNotFoundException;
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

@WebServlet("/habit/getCompletionPercentage")
@Slf4j
public class GetCompletionPercentageServlet extends BaseHabitServlet {

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
            log.info("Received request to get completion percentage: " + requestBody);

            // Десериализация тела запроса
            GetCompletionPercentageDto getCompletionPercentageDto = objectMapper.readValue(requestBody, GetCompletionPercentageDto.class);
            getCompletionPercentageDto.setRequest(req);
            OperationResultDto result = habitService.getCompletionPercentage(getCompletionPercentageDto);
            sendResponse(resp, result, HttpServletResponse.SC_OK);
        } catch (HabitNotFoundException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, "Habit not found");
        } catch (NoPermissionsException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_FORBIDDEN, "Access denied");
        } catch (Exception e) {
            log.warn("Internal server error: " + e.getMessage(), e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}