package ru.sergeev.MySecondTestAppSpringBoot.service;

import ru.sergeev.MySecondTestAppSpringBoot.model.Request;
import org.springframework.stereotype.Service;

public interface ModifyRequestService {
    void modify(Request request);
}
