package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.AssetRepository;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.LiabilityRepository;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheet;
import com.github.suloginscene.accountant.context.report.domain.balanceSheet.BalanceSheetFactory;
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


    public BalanceSheet assembleBalanceSheet(Holder holder) {
        List<Asset> assets = assetRepository.findByHolder(holder);
        List<Liability> liabilities = liabilityRepository.findByHolder(holder);
        return BalanceSheetFactory.create(assets, liabilities);
    }

}
