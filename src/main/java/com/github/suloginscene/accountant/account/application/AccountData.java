package com.github.suloginscene.accountant.account.application;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.Flow;
import com.github.suloginscene.accountant.account.domain.SingleTransaction;
import com.github.suloginscene.accountant.account.domain.Stock;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.lib.exception.InternalException;
import lombok.Data;

import java.util.List;

import static com.github.suloginscene.accountant.lib.time.DateTimeFormatters.DATE_TIME;
import static java.util.stream.Collectors.toList;


@Data
public class AccountData {

    private final Long id;
    private final String name;
    private final String type;
    private final Integer moneyAmount;
    private final List<SingleTransactionData> singleTransactions;


    AccountData(Account account) {
        id = account.getId();
        name = account.getName();
        type = account.getClass().getSimpleName();
        moneyAmount = getMoney(account).get();
        singleTransactions = account.readSingleTransactions().stream()
                .map(SingleTransactionData::new)
                .collect(toList());
    }

    private Money getMoney(Account account) {
        if (account instanceof Stock) {
            Stock stock = (Stock) account;
            return stock.getBalance();
        }
        if (account instanceof Flow) {
            Flow flow = (Flow) account;
            return flow.getBudget();
        }
        throw new InternalException("account is nor stock or flow");
    }


    @Data
    public static class SingleTransactionData {

        private final String type;
        private final Integer amount;
        private final String description;
        private final String createdAt;


        SingleTransactionData(SingleTransaction singleTransaction) {
            type = singleTransaction.getType().name();
            amount = singleTransaction.getAmount().get();
            description = singleTransaction.getDescription();
            createdAt = singleTransaction.getCreatedAt().format(DATE_TIME);
        }

    }

}
