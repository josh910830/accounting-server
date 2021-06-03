package com.github.suloginscene.accountingserver.report.domain.ledger;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        DoubleTransaction doubleTransaction = new DoubleTransaction(
                this, type, debit.getName(), credit.getName(), amount, description);
        doubleTransactions.add(doubleTransaction);
    }

    public List<DoubleTransaction> readDoubleTransactions() {
        return new ArrayList<>(doubleTransactions);
    }

}
