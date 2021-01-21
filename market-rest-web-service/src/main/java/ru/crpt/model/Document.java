package ru.crpt.model;

import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

@Data
@Builder
public class Document {

    @ApiModelProperty(value = "Идентификатор продавца", required = true)
    @NotNull
    @Size(min = 9)
    private String seller;

    @ApiModelProperty(value = "Идентфикатор покупателя", required = true)
    @NotNull
    @Size(min = 9)
    private String customer;

    @ApiModelProperty(value = "Список товаров", required = true)
    @NotNull
    @Valid
    private List<Product> products;

    public String getUUID() {
        return String.format("%s.%s", seller, customer);
    }

}
