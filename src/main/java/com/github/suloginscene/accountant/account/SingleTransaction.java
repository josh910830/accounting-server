package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.money.Money;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class SingleTransaction {

    @Id @GeneratedValue
    @Column(name = "single_transaction_id")
    private Long id;

    private Money amount;

    private String description;

    private final LocalDateTime createdAt = LocalDateTime.now();


    private SingleTransaction(Money amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    static SingleTransaction of(Money amount, String description) {
        return new SingleTransaction(amount, description);
    }

}
