package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
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
    private Long id;

    private Holder holder;

    private String name;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private final List<SingleTransaction> singleTransactions = new ArrayList<>();


    protected Account(Holder holder, String name) {
        this.holder = holder;
        this.name = name;
    }

    public static Account create(AccountCreationParameter parameters) {
        AccountType accountType = parameters.getAccountType();
        Holder holder = parameters.getHolder();
        String name = parameters.getName();
        Money money = parameters.getMoney();
        switch (accountType) {
            case ASSET:
                return new Asset(holder, name, money);
            case LIABILITY:
                return new Liability(holder, name, money);
            case REVENUE:
                return new Revenue(holder, name, money);
            case EXPENSE:
                return new Expense(holder, name, money);
            default:
                throw new AccountTypeNotFoundException(accountType);
        }
    }


    protected void writeSingleTransaction(Money amount, String description) {
        singleTransactions.add(SingleTransaction.of(amount, description));
    }

    public List<SingleTransaction> readSingleTransaction() {
        return new ArrayList<>(singleTransactions);
    }

}
