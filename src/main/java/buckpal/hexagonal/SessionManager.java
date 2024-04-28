package buckpal.hexagonal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionResolver {


    private static Long getMemberIdFromSession(HttpServletRequest servletRequest, boolean create) {
        HttpSession session = servletRequest.getSession(create);
        Long memberId = (Long)session.getAttribute("memberId"); //could occur null pointer exception
        return memberId;
    }
}
