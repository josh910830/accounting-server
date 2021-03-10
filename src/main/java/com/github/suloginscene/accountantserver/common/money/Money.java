package com.github.suloginscene.accountantserver.common.money;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;


@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Money {

    private int amount;


    private Money(int amount) {
        if (amount < 0) {
            throw new NegativeMoneyException();
        }
        this.amount = amount;
    }

    public static Money of(int amount) {
        return new Money(amount);
    }

    public static Money add(Money a, Money b) {
        return new Money(a.amount + b.amount);
    }

    public static Money subtract(Money a, Money b) {
        return new Money(a.amount - b.amount);
    }


    public int get() {
        return amount;
    }

}
