package com.github.suloginscene.accountingserver.transaction.api.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
public class TransactionExecutionRequest {

    @NotNull(message = "거래 유형을 입력하십시오.")
    private final String type;

    @NotNull(message = "출발 계정 ID를 입력하십시오.")
    private final Long sourceId;

    @NotNull(message = "도착 계정 ID를 입력하십시오.")
    private final Long destinationId;

    @NotNull(message = "거래 금액을 입력하십시오.")
    @Min(value = 0, message = "거래 금액은 음수일 수 없습니다.")
    private final Integer amount;

    @NotNull(message = "설명을 입력하십시오.")
    @Length(max = 15, message = "설명은 15자 이내여야 합니다.")
    private final String description;

}
