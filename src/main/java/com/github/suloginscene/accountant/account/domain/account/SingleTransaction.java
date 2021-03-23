package com.github.suloginscene.accountant.account.domain.account;

import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.lib.time.TimeRange;
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
    @Getter
    private SingleTransactionType type;

    @Getter
    private Money amount;

    @Getter
    private String description;

    @Getter
    private final LocalDateTime createdAt = LocalDateTime.now();


    SingleTransaction(SingleTransactionType type,
                      Money amount,
                      String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public boolean isCreatedDuring(TimeRange timeRange) {
        return timeRange.contains(createdAt);
    }

}
