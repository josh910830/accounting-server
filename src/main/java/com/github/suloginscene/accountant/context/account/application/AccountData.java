package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.SingleTransaction;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import lombok.Data;

import java.util.List;

import static com.github.suloginscene.accountant.context.common.util.DateTimeFormatters.DEFAULT;
import static java.util.stream.Collectors.toList;


@Data
public class AccountData {

    private final Holder holder;
    private final Long id;
    private final String name;
    private final List<SingleTransactionData> singleTransactions;


    AccountData(Account account) {
        holder = account.getHolder();
        id = account.getId();
        name = account.getName();
        singleTransactions = account.readSingleTransactions().stream()
                .map(SingleTransactionData::new)
                .collect(toList());
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
