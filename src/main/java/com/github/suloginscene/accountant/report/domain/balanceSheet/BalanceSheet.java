package com.github.suloginscene.accountant.report.domain.balanceSheet;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Liability;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class BalanceSheet {

    @Getter
    private final Map<BalanceSheetKey, Integer> total;

    @Getter
    private final List<Asset> assets;

    @Getter
    private final List<Liability> liabilities;

}
