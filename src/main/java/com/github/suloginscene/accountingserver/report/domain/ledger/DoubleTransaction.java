package com.github.suloginscene.accountingserver.report.domain.ledger;

import com.github.suloginscene.accountingserver.common.Money;
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
public class DoubleTransaction {

    @Id @GeneratedValue
    @Column(name = "double_transaction_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ledger_holder")
    private Ledger ledger;

    @Enumerated(STRING)
    @Getter
    private DoubleTransactionType type;

    @Getter
    private String debit;

    @Getter
    private String credit;

    @Getter
    private Money amount;

    @Getter
    private String description;

    @Getter
    private final LocalDateTime createdAt = LocalDateTime.now();


    public DoubleTransaction(Ledger ledger,
                             DoubleTransactionType type,
                             String debit,
                             String credit,
                             Money amount,
                             String description) {
        this.ledger = ledger;
        this.type = type;
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.description = description;
    }

}
