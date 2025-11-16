package ru.sergeev.MySecondTestAppSpringBoot.service;

import ru.sergeev.MySecondTestAppSpringBoot.model.Request;
import org.springframework.stereotype.Service;

@Service
public class ModifyRequestServiceImpl implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        // Базовая модификация - изменяем UID
        request.setUid(request.getUid() + "_modified");
    }
}