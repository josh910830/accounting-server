package com.github.suloginscene.accountant.account.domain.transaction;

import com.github.suloginscene.accountant.account.domain.account.Account;
import com.github.suloginscene.accountant.lib.exception.RequestException;
import com.github.suloginscene.accountant.common.Holder;
import lombok.Getter;


@Getter
public class AccountPair {

    private final Holder holder;

    private final Account source;
    private final Account destination;


    private AccountPair(Holder holder, Account source, Account destination) {
        this.holder = holder;
        this.source = source;
        this.destination = destination;
    }

    public static AccountPair of(Account source, Account destination) {
        checkEqualHolder(source, destination);
        Holder holder = source.getHolder();
        return new AccountPair(holder, source, destination);
    }

    private static void checkEqualHolder(Account a, Account b) {
        Holder holder1 = a.getHolder();
        Holder holder2 = b.getHolder();

        if (!holder1.equals(holder2)) {
            throw new RequestException("account holder is not matched");
        }
    }

}
