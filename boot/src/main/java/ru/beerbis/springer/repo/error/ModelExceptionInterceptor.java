package ru.beerbis.springer.repo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    ResponseEntity<Object> onError(EntityNotFound e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<Object> onError(ForbiddenInputValue e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
