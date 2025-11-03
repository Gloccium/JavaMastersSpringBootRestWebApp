package ru.sergeev.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @NotBlank(message = "UID обязателен")
    @Size(max = 32, message = "UID не может превышать 32 символа")
    private String uid;

    @NotBlank(message = "Operation UID обязателен")
    @Size(max = 32, message = "Operation UID не может превышать 32 символа")
    private String operationUid;

    private Systems systemName; // ← изменено с String на Systems

    @NotBlank(message = "System time обязателен")
    private String systemTime;

    private String source;

    @Min(value = 1, message = "Communication ID должен быть не меньше 1")
    @Max(value = 100000, message = "Communication ID должен быть не больше 100000")
    private int communicationId;

    private int templateId;

    private int productCode;

    private int smsCode;

    private Long startTime;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName=" + systemName +  // enum, без кавычек
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                '}';
    }
}