package com.github.suloginscene.accountant.report.domain.ledger;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.common.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString(of = "holder")
public class Ledger {

    @Id
    @Getter
    private Holder holder;

    @OneToMany(mappedBy = "ledger", cascade = ALL, orphanRemoval = true)
    @BatchSize(size = 100)
    private final List<DoubleTransaction> doubleTransactions = new ArrayList<>();


    public Ledger(Holder holder) {
        this.holder = holder;
    }

    public void scribe(DoubleTransactionType type,
                       Account debit,
                       Account credit,
                       Money amount,
                       String description) {
        doubleTransactions.add(
                new DoubleTransaction(this, type, debit, credit, amount, description));
    }

    public List<DoubleTransaction> readDoubleTransactions() {
        return new ArrayList<>(doubleTransactions);
    }

}
