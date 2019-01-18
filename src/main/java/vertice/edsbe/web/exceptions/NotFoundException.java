package vertice.edsbe.web.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vertice.edsbe.web.utils.ApiUtil;

import java.util.Date;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
//public class NotFoundException extends RuntimeException {
//@ControllerAdvice
//@RestController
//public class NotFoundException extends ResponseEntityExceptionHandler {
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                              HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ApiUtil rstl = new ApiUtil();
//        rstl.setMessage(ex.getBindingResult().toString());
//        rstl.setStatus(HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity(rstl, HttpStatus.BAD_REQUEST);
//    }
//}
