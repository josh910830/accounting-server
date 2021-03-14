package com.github.suloginscene.accountant.context.report.domain.balanceSheet;

import com.github.suloginscene.accountant.context.account.domain.account.Stock;
import com.github.suloginscene.accountant.context.common.value.money.Money;

import java.util.List;


public class StockUtils {

    public static Integer sumBalances(List<? extends Stock> stocks) {
        return stocks.stream()
                .map(Stock::getBalance)
                .map(Money::get)
                .reduce(0, Integer::sum);
    }

}
