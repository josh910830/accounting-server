package com.github.suloginscene.accountant.context.report.domain.balanceSheet;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheetKey.ASSET_SUM;
import static com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheetKey.LIABILITY_SUM;
import static com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheetKey.NET;


@RequiredArgsConstructor
public class BalanceSheet {

    private final Map<BalanceSheetKey, Integer> total;

    private final List<Asset> assets;

    private final List<Liability> liabilities;


    public Integer getNet() {
        return total.get(NET);
    }

    public Integer getAssetSum() {
        return total.get(ASSET_SUM);
    }

    public Integer getLiabilitySum() {
        return total.get(LIABILITY_SUM);
    }

}
