package ru.sergeev.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Response — описывает структуру ответа,
 * возвращаемого сервисом после обработки запроса.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /** Уникальный идентификатор сообщения (тот же, что и в запросе) */
    private String uid;

    /** Идентификатор операции */
    private String operationUid;

    /** Имя системы, формирующей ответ */
    private String systemName;

    /** Код ответа: SUCCESS / FAILED */
    private String code;

    /** Текстовое сообщение, описывающее результат операции */
    private String message;

    /** Технический код ошибки (если есть) */
    private String errorCode;

    /** Описание ошибки (если есть) */
    private String errorMessage;

    /** Рассчитанная годовая премия сотрудника */
    private Double annualBonus;

    /** Время формирования ответа системой */
    private String systemTime;
}