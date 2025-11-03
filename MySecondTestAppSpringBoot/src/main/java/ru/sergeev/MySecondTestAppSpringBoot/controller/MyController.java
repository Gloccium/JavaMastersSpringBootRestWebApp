package ru.sergeev.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import ru.sergeev.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.sergeev.MySecondTestAppSpringBoot.model.ErrorCodes;
import ru.sergeev.MySecondTestAppSpringBoot.model.ErrorMessages;
import ru.sergeev.MySecondTestAppSpringBoot.model.Request;
import ru.sergeev.MySecondTestAppSpringBoot.model.Response;
import ru.sergeev.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.sergeev.MySecondTestAppSpringBoot.service.ValidationService;
import ru.sergeev.MySecondTestAppSpringBoot.util.DateTimeUtil;

@RestController
public class MyController {

    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    private final ValidationService validationService;
    private final ModifyRequestService modifyRequestService;
    private final RestTemplate restTemplate;

    @Autowired
    public MyController(ValidationService validationService,
                        ModifyRequestService modifyRequestService,
                        RestTemplate restTemplate) {
        this.validationService = validationService;
        this.modifyRequestService = modifyRequestService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        // Засекаем время получения запроса
        long startTime = System.currentTimeMillis();
        request.setStartTime(startTime);

        log.info("Получен запрос в {}: {}", startTime, request);

        try {
            validationService.isValid(bindingResult);
            log.info("Запрос прошёл валидацию: UID={}, systemName={}", request.getUid(), request.getSystemName());
        } catch (ValidationFailedException e) {
            log.error("Исключение валидации для UID '{}': {}", request.getUid(), e.getMessage());
            Response errorResponse = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                    .code(ErrorCodes.FAILED.getName())
                    .errorCode(ErrorMessages.VALIDATION_EXCEPTION.name())
                    .errorMessage(ErrorMessages.VALIDATION_EXCEPTION.getDescription())
                    .build();
            log.info("Сформирован ответ об ошибке: {}", errorResponse);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        if ("123".equals(request.getUid())) {
            log.warn("Обнаружен запрещённый UID: '123'");
            Response errorResponse = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                    .code(ErrorCodes.FAILED.getName())
                    .errorCode(ErrorMessages.UNSUPPORTED_EXCEPTION.name())
                    .errorMessage(ErrorMessages.UNSUPPORTED_EXCEPTION.getDescription())
                    .build();
            log.info("Сформирован ответ об ошибке (неподдерживаемый UID): {}", errorResponse);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Модифицируем request
        modifyRequestService.modify(request);
        log.info("Request после модификации: {}", request);

        // Отправляем модифицированный request в Сервис 2
        String service2Url = "http://localhost:8084/feedback";
        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        try {
            log.info("Отправка модифицированного request в Сервис 2");
            restTemplate.postForEntity(service2Url, httpEntity, Response.class);
            log.info("Request успешно отправлен в Сервис 2");
        } catch (Exception e) {
            log.error("Ошибка отправки request в Сервис 2: {}", e.getMessage());
        }

        Response successResponse = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(ErrorCodes.SUCCESS.getName())
                .errorCode(ErrorCodes.EMPTY.getName())
                .errorMessage(ErrorMessages.EMPTY.getDescription())
                .build();

        log.info("Успешно сформирован ответ: {}", successResponse);
        return ResponseEntity.ok(successResponse);
    }
}