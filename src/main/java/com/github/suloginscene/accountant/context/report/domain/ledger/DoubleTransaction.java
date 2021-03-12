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

    private String debit;

    private String credit;

    private Money amount;

    private String description;

    private LocalDateTime createdAt;


    private DoubleTransaction(String debit, String credit, Money amount, String description, LocalDateTime createdAt) {
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    static DoubleTransaction of(DoubleTransactionExecutedEvent event) {
        String credit = event.getFrom();
        String debit = event.getTo();
        Money amount = event.getAmount();
        String description = event.getDescription();
        LocalDateTime createdAt = event.getCreatedAt();
        return new DoubleTransaction(debit, credit, amount, description, createdAt);
    }

}
