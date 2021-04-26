package com.github.suloginscene.accountingserver.account.api.request;

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

    @NotNull(message = "새 예산을 입력하십시오.")
    @Min(value = 0, message = "예산은 음수일 수 없습니다.")
    @Getter
    private Integer newBudget;

}
