package br.com.erudio.restprojecterudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequireObjectIsNullException extends  RuntimeException{

    public RequireObjectIsNullException(String exception){
        super(exception);
    }
    public RequireObjectIsNullException(){
        super("It is not allowed to persist a null object!");
    }
}
