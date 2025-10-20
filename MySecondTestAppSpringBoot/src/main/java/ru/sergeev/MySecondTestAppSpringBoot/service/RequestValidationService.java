// ru.sergeev.MySecondTestAppSpringBoot.service.ValidationService
package ru.sergeev.MySecondTestAppSpringBoot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.sergeev.MySecondTestAppSpringBoot.exception.ValidationFailedException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestValidationService {

    private static final Logger log = LoggerFactory.getLogger(ValidationService.class);

    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            String errorMessage = "Ошибки валидации: " + String.join("; ", errors);
            log.error("Ошибка валидации: {}", errorMessage); // ← ERROR лог
            throw new ValidationFailedException(errorMessage);
        }
        log.info("Валидация пройдена успешно"); // ← INFO лог
    }
}