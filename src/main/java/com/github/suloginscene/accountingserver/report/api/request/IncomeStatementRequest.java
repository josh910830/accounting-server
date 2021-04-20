package com.github.suloginscene.accountingserver.report.api.request;

import com.github.suloginscene.string.QueryStringBuilder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.github.suloginscene.time.DateTimeFormatters.DATE;
import static com.github.suloginscene.time.DateTimeFormatters.DATE_MESSAGE;
import static com.github.suloginscene.time.DateTimeFormatters.DATE_REGEXP;


@Data
public class IncomeStatementRequest {

    @NotNull
    @Pattern(regexp = DATE_REGEXP, message = DATE_MESSAGE)
    private final String beginDate;

    @NotNull
    @Pattern(regexp = DATE_REGEXP, message = DATE_MESSAGE)
    private final String inclusiveEndDate;


    public static String queryString(LocalDate begin, LocalDate inclusiveEnd) {
        return queryString(begin.format(DATE), inclusiveEnd.format(DATE));
    }

    public static String queryString(String beginDate, String inclusiveEndDate) {
        return QueryStringBuilder.create()
                .add("beginDate", beginDate)
                .add("inclusiveEndDate", inclusiveEndDate)
                .build();
    }

}
