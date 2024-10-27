package homework3.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthUtils {

    public static boolean checkAuthentication(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null");
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }
        return true;
    }
}