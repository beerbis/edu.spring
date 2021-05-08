package ru.beerbis.springer.repo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h2>Перехватчик исключений работы с моделью/данными БД
 *
 * <p>
 *   Вместо исключений это могли бы быть просто методы наполняющие HttpResponse, после
 *   наполнения Controller бы просто выходил из обработки, по количеству кода так-на-так бы и получилось
 *
 */
@ControllerAdvice
public class ModelExceptionInterceptor {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<Object> onNotFoundError(EntityNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String onNotFoundError(ForbiddenInputValue e) {
        return e.getMessage();
    }
}
