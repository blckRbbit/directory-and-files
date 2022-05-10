package com.gmail.leonidov.lex.porter.configs;

import com.gmail.leonidov.lex.porter.dtos.AppError;
import com.gmail.leonidov.lex.porter.exceptions.NotDirectoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchNotDirectoryException(NotDirectoryException e) {
        log.error(String.valueOf(e));
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
