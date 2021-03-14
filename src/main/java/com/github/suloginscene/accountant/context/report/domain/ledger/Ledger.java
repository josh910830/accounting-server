package com.github.suloginscene.accountant.context.report.domain.ledger;

import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransaction;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor(access = PROTECTED)
public class Ledger {

    @Id
    private Holder holder;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "holder_id")
    private final List<DoubleTransaction> doubleTransactions = new ArrayList<>();


    public Ledger(Holder holder) {
        this.holder = holder;
    }

    public void writeDoubleTransaction(DoubleTransaction doubleTransaction) {
        doubleTransactions.add(doubleTransaction);
    }

    public List<DoubleTransaction> readDoubleTransactions() {
        return new ArrayList<>(doubleTransactions);
    }

}
