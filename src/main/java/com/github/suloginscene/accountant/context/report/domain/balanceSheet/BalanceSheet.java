package com.github.suloginscene.accountant.context.report.domain.balanceSheet;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
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
