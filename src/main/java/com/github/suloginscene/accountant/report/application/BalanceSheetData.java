package com.github.suloginscene.accountant.report.application;

import com.github.suloginscene.accountant.account.domain.Stock;
import com.github.suloginscene.accountant.report.domain.balanceSheet.BalanceSheet;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


@Data
public class BalanceSheetData {

    private final Map<String, Integer> total;
    private final List<StockInformation> assets;
    private final List<StockInformation> liabilities;


    BalanceSheetData(BalanceSheet balanceSheet) {
        total = new HashMap<>();
        balanceSheet.getTotal()
                .forEach((k, v) -> total.put(k.name(), v));

        assets = balanceSheet.getAssets().stream()
                .map(StockInformation::new)
                .collect(toList());
        liabilities = balanceSheet.getLiabilities().stream()
                .map(StockInformation::new)
                .collect(toList());
    }


    @Data
    public static class StockInformation {

        private final Long id;
        private final String name;
        private final Integer balance;


        StockInformation(Stock stock) {
            this.id = stock.getId();
            this.name = stock.getName();
            this.balance = stock.getBalance().get();
        }

    }

}
