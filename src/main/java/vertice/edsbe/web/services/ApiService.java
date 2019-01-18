package vertice.edsbe.web.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vertice.edsbe.web.utils.ApiUtil;

@Service
public class ApiService {
    public ResponseEntity JsonResponse(Object body, Integer status){
        ApiUtil rstl = new ApiUtil();
        rstl.setResult(true);
        rstl.setBody(body);

        //ResponseEntity.status(HttpStatus.BAD_REQUEST)
        if (status!=null){
            return ResponseEntity.status(status).body(rstl);
        }else{
            return ResponseEntity.status(200).body(rstl);
        }
    }
    public  ResponseEntity JsonResponseError(String message,Integer status){
        ApiUtil rstl = new ApiUtil();
        rstl.setStatus(status);
        rstl.setResult(false);
        rstl.setMessage(message);
        return ResponseEntity.status(status).body(rstl);
    }
}
