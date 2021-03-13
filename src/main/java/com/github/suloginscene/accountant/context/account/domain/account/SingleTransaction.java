package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.money.Money;
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
    private SingleTransactionType singleTransactionType;

    private Money amount;

    private String description;

    private final LocalDateTime createdAt = LocalDateTime.now();


    SingleTransaction(SingleTransactionType singleTransactionType,
                      Money amount,
                      String description) {
        this.singleTransactionType = singleTransactionType;
        this.amount = amount;
        this.description = description;
    }

}
