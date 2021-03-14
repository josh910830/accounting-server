package com.github.suloginscene.accountant.context.report.domain.incomestatement;

import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.account.domain.account.SingleTransaction;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import java.time.LocalDateTime;
import java.util.List;


public class FlowUtils {

    public static Integer sumAmount(Flow flow, LocalDateTime from, LocalDateTime to) {
        List<SingleTransaction> transactions = flow.readSingleTransactions(from, to);
        return transactions.stream()
                .map(SingleTransaction::getAmount)
                .map(Money::get)
                .reduce(0, Integer::sum);
    }

}
