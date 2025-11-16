package ru.sergeev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sergeev.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}