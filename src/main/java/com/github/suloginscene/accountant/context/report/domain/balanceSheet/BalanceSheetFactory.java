package com.github.suloginscene.accountant.context.report.domain.balanceSheet;

import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheetKey.ASSET_SUM;
import static com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheetKey.LIABILITY_SUM;
import static com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheetKey.NET;
import static com.github.suloginscene.accountant.context.report.domain.balanceSheet.StockUtils.sumBalances;


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

}
