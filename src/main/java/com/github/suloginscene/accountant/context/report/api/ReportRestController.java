package com.github.suloginscene.accountant.context.report.api;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.application.BalanceSheetAssemblingService;
import com.github.suloginscene.accountant.context.report.application.BalanceSheetData;
import com.github.suloginscene.accountant.context.report.application.IncomeStatementAssemblingService;
import com.github.suloginscene.accountant.context.report.application.LedgerData;
import com.github.suloginscene.accountant.context.report.application.LedgerFindingService;
import com.github.suloginscene.jwtconfig.Authenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportRestController {

    private final LedgerFindingService ledgerFindingService;
    private final BalanceSheetAssemblingService balanceSheetAssemblingService;
    private final IncomeStatementAssemblingService incomeStatementAssemblingService;


    @GetMapping("/ledger")
    ResponseEntity<LedgerData> getLedger(@Authenticated Long memberId) {
        Holder holder = new Holder(memberId);
        LedgerData ledger = ledgerFindingService.findLedger(holder);

        return ResponseEntity.ok(ledger);
    }

    @GetMapping("/balance-sheet")
    ResponseEntity<BalanceSheetData> getBalanceSheet(@Authenticated Long memberId) {
        Holder holder = new Holder(memberId);
        BalanceSheetData balanceSheet = balanceSheetAssemblingService.assembleBalanceSheet(holder);

        return ResponseEntity.ok(balanceSheet);
    }

}
