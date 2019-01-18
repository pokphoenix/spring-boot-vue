package vertice.edsbe.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import vertice.edsbe.web.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoggerInterceptor implements HandlerInterceptor {

    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object object, Exception arg3)
            throws Exception {
        log.info("Request is complete");
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object object, ModelAndView model)
            throws Exception {
        log.info("Handler execution is complete");
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        log.info("Before Handler execution");
        System.out.println("[request] url : "+request.getRequestURI());
        System.out.println("[request] query : "+request.getQueryString());
        if (request.getRequestURI().startsWith("/api")){
            String token = request.getParameter("token");
            System.out.println("[token] : "+token);
            if(!userService.checkAuthen(token)){
                //response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.sendError(401,"token mismatch");
                return false;
            }
        }

        return true;
    }

}
