package com.github.suloginscene.accountant.report.domain.ledger;

import com.github.suloginscene.accountant.common.Holder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString(of = "holder")
public class Ledger {

    @Id
    @Getter
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
