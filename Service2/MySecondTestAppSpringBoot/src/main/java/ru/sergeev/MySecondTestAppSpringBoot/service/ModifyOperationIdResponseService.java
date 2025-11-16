package ru.sergeev.MySecondTestAppSpringBoot.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.sergeev.MySecondTestAppSpringBoot.model.Response;
import java.util.UUID;

@Service
@Qualifier("ModifyOperationIdResponseService")
public class ModifyOperationIdResponseService implements ModifyResponseService {

    @Override
    public Response modify(Response response) {
        UUID uuid = UUID.randomUUID();
        response.setOperationUid(uuid.toString());
        return response;
    }
}