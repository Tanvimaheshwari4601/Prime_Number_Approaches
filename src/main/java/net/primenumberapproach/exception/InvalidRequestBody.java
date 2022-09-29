package net.primenumberapproach.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class InvalidRequestBody extends RuntimeException {
    public InvalidRequestBody(String message){
        super(message);
    }

}
