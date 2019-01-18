package vertice.edsbe.web.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vertice.edsbe.web.model.User;
import vertice.edsbe.web.services.UserService;

import java.sql.Timestamp;

@Data
public class ApiUtil {
    private Timestamp timestamp;
    private int status;
    private boolean result;

    private String token;
    private String message;
    private Object body;

    public ApiUtil(){
        this.status = 200;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

}
