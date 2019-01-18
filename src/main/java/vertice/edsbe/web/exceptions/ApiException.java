package vertice.edsbe.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vertice.edsbe.web.utils.ApiUtil;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiUtil handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> listFieldErrors =   bindingResult.getFieldErrors();
        List<String> allErrors = new ArrayList<>();
        for (FieldError fieldError:listFieldErrors ){
            allErrors.add(fieldError.getDefaultMessage());
        }
        ApiUtil rstl = new ApiUtil();
        rstl.setMessage("validate errors");
        rstl.setBody(allErrors);
        rstl.setStatus(HttpStatus.BAD_REQUEST.value());
        return rstl;
    }
}
