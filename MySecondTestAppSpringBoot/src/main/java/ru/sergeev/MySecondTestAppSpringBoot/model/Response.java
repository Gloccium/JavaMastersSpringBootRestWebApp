// ru.sergeev.MySecondTestAppSpringBoot.model.Response
package ru.sergeev.MySecondTestAppSpringBoot.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String uid;
    private String operationUid;
    private String systemTime;
    private String code;          // ← String
    private String errorCode;     // ← String
    private String errorMessage;  // ← String
}