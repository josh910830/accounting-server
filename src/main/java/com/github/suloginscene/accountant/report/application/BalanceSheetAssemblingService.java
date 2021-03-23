package com.github.suloginscene.accountant.report.application;

import com.github.suloginscene.accountant.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.account.concrete.AssetRepository;
import com.github.suloginscene.accountant.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.account.domain.account.concrete.LiabilityRepository;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.report.domain.balanceSheet.BalanceSheet;
import com.github.suloginscene.accountant.report.domain.balanceSheet.BalanceSheetFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BalanceSheetAssemblingService {

    private final AssetRepository assetRepository;
    private final LiabilityRepository liabilityRepository;


    public BalanceSheetData assembleBalanceSheet(Holder holder) {
        List<Asset> assets = assetRepository.findByHolder(holder);
        List<Liability> liabilities = liabilityRepository.findByHolder(holder);

        BalanceSheet balanceSheet = BalanceSheetFactory.create(assets, liabilities);

        return new BalanceSheetData(balanceSheet);
    }

}
