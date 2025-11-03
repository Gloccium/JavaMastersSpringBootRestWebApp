package ru.sergeev.MySecondTestAppSpringBoot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sergeev.MySecondTestAppSpringBoot.model.Request;

@Service
public class ModifySourceRequestService implements ModifyRequestService {

    private static final Logger log = LoggerFactory.getLogger(ModifySourceRequestService.class);

    @Override
    public void modify(Request request) {
        request.setSource("Modified Source");
        log.info("Source изменен на: {}", request.getSource());
    }
}