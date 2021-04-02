package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.time.TimeRange;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class SingleTransaction {

    @Id @GeneratedValue
    @Column(name = "single_transaction_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(STRING)
    @Getter
    private SingleTransactionType type;

    @Getter
    private Money amount;

    @Getter
    private String description;

    @Getter
    private final LocalDateTime createdAt = LocalDateTime.now();


    SingleTransaction(Account account,
                      SingleTransactionType type,
                      Money amount,
                      String description) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public boolean isCreatedDuring(TimeRange timeRange) {
        return timeRange.contains(createdAt);
    }

}
