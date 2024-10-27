package homework3.utils;

import homework3.entity.User;
import homework3.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RequestUtils {

    public static User getCurrentUser(HttpServletRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null");
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new UserNotFoundException("User not found in session");
        }

        return (User) session.getAttribute("user");
    }
}