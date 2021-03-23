package com.github.suloginscene.accountant.account.api;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
public class TransactionExecutionRequest {

    @NotNull
    private final String type;

    @NotNull
    private final Long sourceId;

    @NotNull
    private final Long destinationId;

    @NotNull
    @Min(value = 0, message = "최소 0")
    private final Integer amount;

    @NotNull
    @Length(max = 15)
    private final String description;

}
