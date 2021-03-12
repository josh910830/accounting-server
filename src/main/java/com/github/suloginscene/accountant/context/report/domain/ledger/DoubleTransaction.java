package com.github.suloginscene.accountant.context.report.domain.ledger;

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


    public DoubleTransaction(String debit,
                             String credit,
                             Money amount,
                             String description,
                             LocalDateTime createdAt) {
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

}
