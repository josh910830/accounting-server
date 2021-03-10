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


    public static Account createAsset(Holder holder, String name, Money balance) {
        return new Asset(holder, name, balance);
    }

    public static Account createLiability(Holder holder, String name, Money balance) {
        return new Liability(holder, name, balance);
    }

    public static Account createRevenue(Holder holder, String name, Money budget) {
        return new Revenue(holder, name, budget);
    }

    public static Account createExpense(Holder holder, String name, Money budget) {
        return new Expense(holder, name, budget);
    }

}
