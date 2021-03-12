package com.github.suloginscene.accountant.context.common.event;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class DoubleTransactionExecutedEvent implements AccountantEvent {

    private final String from;
    private final String to;
    private final Money amount;
    private final String description;

    private final LocalDateTime createdAt = LocalDateTime.now();

}
