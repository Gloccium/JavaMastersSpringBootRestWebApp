package ru.sergeev.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Request — содержит данные входящего запроса от клиента
 * и используется для передачи информации в сервисы приложения.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /** Уникальный идентификатор сообщения */
    @NotBlank(message = "UID не может быть пустым")
    private String uid;

    /** Идентификатор операции */
    private String operationUid;

    /** Наименование внешней системы, отправившей запрос */
    private String systemName;

    /** Время формирования запроса во внешней системе (в формате yyyy-MM-dd HH:mm:ss) */
    private String systemTime;

    /** Источник данных — система, из которой поступает сообщение */
    private String source;

    /** Идентификатор канала коммуникации */
    private Integer communicationId;

    /** Идентификатор шаблона сообщения */
    private Integer templateId;

    /** Код продукта, по которому производится операция */
    private Integer productCode;

    /** Код SMS-шаблона, если используется SMS-уведомление */
    private Integer smsCode;

    /** Позиция сотрудника (роль/должность) */
    private Positions positions;

    /** Основная заработная плата сотрудника */
    private Double salary;

    /** Коэффициент премии */
    private Double bonus;

    /** Количество фактически отработанных дней */
    private Integer workDays;

    /** Время отправки запроса из Сервиса 1 (в миллисекундах) */
    private Long startTime;

    @Override
    public String toString() {
        return "Request{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                ", positions=" + positions +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", workDays=" + workDays +
                ", startTime=" + startTime +
                '}';
    }
}