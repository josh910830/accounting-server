package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.range.TimeRange;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class SingleTransaction {

    @Id @GeneratedValue
    @Column(name = "single_transaction_id")
    private Long id;

    @Enumerated(STRING)
    private SingleTransactionType type;

    @Getter
    private Money amount;

    private String description;

    private final LocalDateTime createdAt = LocalDateTime.now();


    SingleTransaction(SingleTransactionType type,
                      Money amount,
                      String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public boolean isCreatedDuring(TimeRange timeRange) {
        LocalDateTime begin = timeRange.getBegin();
        LocalDateTime end = timeRange.getEnd();

        boolean closed = createdAt.isEqual(begin) || createdAt.isAfter(begin);
        boolean open = createdAt.isBefore(end);
        return closed && open;
    }

}
