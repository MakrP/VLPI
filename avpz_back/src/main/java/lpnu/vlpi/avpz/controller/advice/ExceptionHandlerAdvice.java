package lpnu.vlpi.avpz.controller.advice;

import lpnu.vlpi.avpz.dto.error.ErrorsDto;
import lpnu.vlpi.avpz.service.exceptions.LoginException;
import lpnu.vlpi.avpz.service.exceptions.StatisticNotFountException;
import lpnu.vlpi.avpz.service.exceptions.TaskNotFountException;
import lpnu.vlpi.avpz.service.exceptions.UserNotFountException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({LoginException.class, UserNotFountException.class, TaskNotFountException.class, StatisticNotFountException.class})
    public ResponseEntity<ErrorsDto> handleNotFount(Exception exception) {
        ErrorsDto errorsDto = new ErrorsDto();
        errorsDto.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorsDto, HttpStatus.NOT_FOUND);
    }

}
