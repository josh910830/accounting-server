package com.github.suloginscene.accountant.context.report.domain.ledger;

import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class DoubleTransaction {

    @Id @GeneratedValue
    @Column(name = "double_transaction_id")
    private Long id;

    private String from;

    private String to;

    private Money amount;

    private String description;

    private LocalDateTime createdAt;


    private DoubleTransaction(String from, String to, Money amount, String description, LocalDateTime createdAt) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    static DoubleTransaction of(DoubleTransactionExecutedEvent event) {
        String from = event.getFrom();
        String to = event.getTo();
        Money amount = event.getAmount();
        String description = event.getDescription();
        LocalDateTime createdAt = event.getCreatedAt();
        return new DoubleTransaction(from, to, amount, description, createdAt);
    }

}
