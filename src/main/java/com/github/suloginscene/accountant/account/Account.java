package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.holder.Holder;
import com.github.suloginscene.accountant.common.money.Money;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import static javax.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = PROTECTED)
public abstract class Account {

    @Id @GeneratedValue
    private Long id;

    private Holder holder;

    private String name;


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

}
