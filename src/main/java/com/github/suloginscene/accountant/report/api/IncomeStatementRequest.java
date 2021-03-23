package com.github.suloginscene.accountant.report.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.github.suloginscene.accountant.lib.time.DateTimeFormatters.DATE_MESSAGE;
import static com.github.suloginscene.accountant.lib.time.DateTimeFormatters.DATE_REGEXP;


@Data
public class IncomeStatementRequest {

    @NotNull
    @Pattern(regexp = DATE_REGEXP, message = DATE_MESSAGE)
    private final String begin;

    @NotNull
    @Pattern(regexp = DATE_REGEXP, message = DATE_MESSAGE)
    private final String inclusiveEnd;

}
