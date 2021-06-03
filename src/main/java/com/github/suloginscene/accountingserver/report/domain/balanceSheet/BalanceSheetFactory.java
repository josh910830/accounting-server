package com.github.suloginscene.accountingserver.report.domain.balanceSheet;

import com.github.suloginscene.accountingserver.account.domain.Stock;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Liability;
import com.github.suloginscene.accountingserver.common.Money;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.suloginscene.accountingserver.report.domain.balanceSheet.BalanceSheetKey.ASSET_SUM;
import static com.github.suloginscene.accountingserver.report.domain.balanceSheet.BalanceSheetKey.LIABILITY_SUM;
import static com.github.suloginscene.accountingserver.report.domain.balanceSheet.BalanceSheetKey.NET;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class BalanceSheetFactory {

    public static BalanceSheet create(List<Asset> assets, List<Liability> liabilities) {
        Map<BalanceSheetKey, Integer> total = totalTable(assets, liabilities);
        return new BalanceSheet(total, assets, liabilities);
    }

    private static Map<BalanceSheetKey, Integer> totalTable(List<Asset> assets, List<Liability> liabilities) {
        Integer assetSum = sumBalances(assets);
        Integer liabilitySum = sumBalances(liabilities);
        Integer net = assetSum - liabilitySum;

        Map<BalanceSheetKey, Integer> total = new HashMap<>();
        total.put(ASSET_SUM, assetSum);
        total.put(LIABILITY_SUM, liabilitySum);
        total.put(NET, net);
        return total;
    }

    private static Integer sumBalances(List<? extends Stock> stocks) {
        return stocks.stream()
                .map(Stock::getBalance)
                .map(Money::get)
                .reduce(0, Integer::sum);
    }

}
