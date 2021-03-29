package com.github.suloginscene.accountant.account.api.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode @ToString
@NoArgsConstructor @AllArgsConstructor
public class AccountBudgetChangeRequest {

    @NotNull
    @Min(value = 0, message = "최소 0")
    @Getter
    private Integer newBudget;

}
