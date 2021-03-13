package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = PROTECTED)
public abstract class Account {

    @Id @GeneratedValue
    @Column(name = "account_id")
    @Getter
    private Long id;

    @Getter
    private Holder holder;

    @Getter
    private String name;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private final List<SingleTransaction> singleTransactions = new ArrayList<>();


    protected Account(Holder holder, String name) {
        this.holder = holder;
        this.name = name;
    }


    protected void writeSingleTransaction(SingleTransaction singleTransaction) {
        singleTransactions.add(singleTransaction);
    }

    public List<SingleTransaction> readSingleTransactions() {
        return new ArrayList<>(singleTransactions);
    }

}
