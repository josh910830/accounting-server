package com.github.suloginscene.accountant.context.common.value.range;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TimeRange {

    private final LocalDateTime begin;
    private final LocalDateTime end;

}
