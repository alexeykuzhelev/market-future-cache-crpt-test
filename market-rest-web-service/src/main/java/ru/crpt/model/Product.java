package ru.crpt.model;

import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@ToString
public class Product {

    @ApiModelProperty(value = "Наименование товара", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "Идентификатор товара", required = true)
    @NotNull
    @Size(min = 13)
    private String code;

}
