package com.github.suloginscene.accountant.common;

import com.github.suloginscene.exception.RequestException;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;


@Embeddable
@EqualsAndHashCode @ToString
@NoArgsConstructor(access = PROTECTED)
public class Money {

    private int amount;


    private Money(int amount) {
        if (amount < 0) {
            throw new RequestException("money amount is negative");
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
