package ru.crpt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@ApiModel(description = "Результат обработки запроса при неуспешном ответе")
@Data
public class ErrorResponse {

    @ApiModelProperty(value = "Сообщение об ошибке")
    private final String errorMessage;

}
