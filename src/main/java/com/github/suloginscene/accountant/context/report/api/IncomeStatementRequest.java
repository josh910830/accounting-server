package com.github.suloginscene.accountant.context.report.api;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class IncomeStatementRequest {

    // TODO validate by pattern
    @NotNull
    private final String begin;

    @NotNull
    private final String inclusiveEnd;

}
