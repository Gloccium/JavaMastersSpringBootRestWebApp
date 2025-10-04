package ru.sergeev.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.sergeev.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.sergeev.MySecondTestAppSpringBoot.model.Request;
import ru.sergeev.MySecondTestAppSpringBoot.model.Response;
import ru.sergeev.MySecondTestAppSpringBoot.service.ValidationService;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            Response errorResponse = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(formatter.format(new Date()))
                    .code("failed")
                    .errorCode("ValidationException")
                    .errorMessage("Ошибка валидации")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        if ("123".equals(request.getUid())) {
            Response errorResponse = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(formatter.format(new Date()))
                    .code("failed")
                    .errorCode("UnsupportedCodeException")
                    .errorMessage("UID '123' не поддерживается")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Response successResponse = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(formatter.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}