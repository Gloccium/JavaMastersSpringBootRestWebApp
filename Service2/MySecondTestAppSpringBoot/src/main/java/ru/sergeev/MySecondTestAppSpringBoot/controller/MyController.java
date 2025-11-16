package ru.sergeev.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Date;

import ru.sergeev.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.sergeev.MySecondTestAppSpringBoot.model.*;
import ru.sergeev.MySecondTestAppSpringBoot.service.*;
import ru.sergeev.MySecondTestAppSpringBoot.util.DateTimeUtil;

@RestController
public class MyController {

    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    private final ValidationService validationService;
    private final AnnualBonusService annualBonusService;

    @Autowired
    public MyController(ValidationService validationService, AnnualBonusService annualBonusService) {
        this.validationService = validationService;
        this.annualBonusService = annualBonusService;
    }


    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        logRequestInfo(request);

        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            return buildErrorResponse(request, e);
        }

        double annualBonus = annualBonusService.calculate(
                request.getPositions(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays()
        );

        Response response = buildSuccessResponse(request, annualBonus);
        log.info("Расчёт завершён успешно. Итоговая премия: {}", annualBonus);
        return ResponseEntity.ok(response);
    }


    private void logRequestInfo(Request request) {
        log.info("Получен модифицированный запрос: {}", request);

        if (request.getStartTime() != null) {
            long diff = System.currentTimeMillis() - request.getStartTime();
            log.info("⏱️ Время обработки от Сервиса 1 до Сервиса 2: {} мс", diff);
        }
    }


    private ResponseEntity<Response> buildErrorResponse(Request request, ValidationFailedException e) {
        log.error("Ошибка валидации UID='{}': {}", request.getUid(), e.getMessage());

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


    private Response buildSuccessResponse(Request request, double annualBonus) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(ErrorCodes.SUCCESS.getName())
                .errorCode(ErrorCodes.EMPTY.getName())
                .errorMessage(ErrorMessages.EMPTY.getDescription())
                .annualBonus(annualBonus)
                .build();
    }
}