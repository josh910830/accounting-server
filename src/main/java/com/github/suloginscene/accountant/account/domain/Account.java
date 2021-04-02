package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.time.TimeRange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "holder", "name"})
public abstract class Account {

    @Id @GeneratedValue
    @Column(name = "account_id")
    @Getter
    private Long id;

    @Getter
    private Holder holder;

    @Getter
    private String name;

    @OneToMany(mappedBy = "account", cascade = ALL, orphanRemoval = true)
    @BatchSize(size = 100)
    private final List<SingleTransaction> singleTransactions = new ArrayList<>();


    protected Account(Holder holder, String name) {
        this.holder = holder;
        this.name = name;
    }


    public void changeName(String newName) {
        this.name = newName;
    }


    protected void writeSingleTransaction(SingleTransaction singleTransaction) {
        singleTransactions.add(singleTransaction);
    }

    public List<SingleTransaction> readSingleTransactions() {
        return new ArrayList<>(singleTransactions);
    }

    public List<SingleTransaction> readSingleTransactions(TimeRange timeRange) {
        return readSingleTransactions().stream()
                .filter(transaction -> transaction.isCreatedDuring(timeRange))
                .collect(toList());
    }

}
