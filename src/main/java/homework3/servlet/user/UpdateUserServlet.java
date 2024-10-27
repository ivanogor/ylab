package homework3.servlet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework3.dto.OperationResultDto;
import homework3.dto.UpdateUserDto;
import homework3.exception.NoPermissionsException;
import homework3.exception.UserNotFoundException;
import homework3.mapper.ObjectMapperConfig;
import homework3.utils.AuthUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@WebServlet("/user/update")
@Slf4j
public class UpdateUserServlet extends BaseUserServlet {

    private final ObjectMapper objectMapper = ObjectMapperConfig.createObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            log.info("Received request to update user: " + requestBody);

            // Десериализация тела запроса
            UpdateUserDto updateUserDto = objectMapper.readValue(requestBody, UpdateUserDto.class);
            updateUserDto.setRequest(req);
            OperationResultDto result = userService.update(updateUserDto);
            sendResponse(resp, result, HttpServletResponse.SC_OK);
        } catch (UserNotFoundException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (NoPermissionsException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            log.warn("Internal server error: " + e.getMessage(), e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}