package com.github.suloginscene.accountant.context.account.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.account.domain.account.SingleTransaction;
import com.github.suloginscene.accountant.context.account.domain.account.Stock;
import com.github.suloginscene.accountant.context.common.exception.InternalException;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;

import java.util.List;

import static com.github.suloginscene.accountant.context.common.util.DateTimeFormatters.DEFAULT;
import static java.util.stream.Collectors.toList;


@Data
public class AccountData {

    @JsonIgnore
    private final Long holderId;

    private final Long id;
    private final String name;
    private final String type;
    private final Integer moneyAmount;
    private final List<SingleTransactionData> singleTransactions;


    AccountData(Account account) {
        holderId = account.getHolder().get();
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


    public boolean isOwnedBy(Long memberId) {
        return holderId.equals(memberId);
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
            createdAt = singleTransaction.getCreatedAt().format(DEFAULT);
        }

    }

}
