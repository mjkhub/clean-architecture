package buckpal.hexagonal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class SessionManager {


    public void createSessionAndRestore(HttpServletRequest servletRequest, Long memberId){
        HttpSession session = servletRequest.getSession(); //세션 생성
        System.out.println("session = " + session.getClass());
        session.setAttribute("memberId", memberId);
    }

    public Long getMemberIdFromSession(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        Long memberId = (Long)session.getAttribute("memberId"); //could occur null pointer exception

        return memberId;
    }

    public void invalidateSession(HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession(false);
        session.invalidate();
    }

}
