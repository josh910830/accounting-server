package com.github.suloginscene.accountant.context.account.domain.account;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TimeRange {

    private final LocalDateTime begin;
    private final LocalDateTime end;

}
