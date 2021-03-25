package com.github.suloginscene.accountant.report.api;

import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.report.application.BalanceSheetAssemblingService;
import com.github.suloginscene.accountant.report.application.BalanceSheetData;
import com.github.suloginscene.accountant.report.application.IncomeStatementAssemblingService;
import com.github.suloginscene.accountant.report.application.IncomeStatementData;
import com.github.suloginscene.accountant.report.application.LedgerData;
import com.github.suloginscene.accountant.report.application.LedgerFindingService;
import com.github.suloginscene.jwtconfig.Authenticated;
import com.github.suloginscene.lib.time.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/reports")
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

    @GetMapping("/income-statement")
    ResponseEntity<IncomeStatementData> getIncomeStatement(@RequestBody @Valid IncomeStatementRequest request,
                                                           @Authenticated Long memberId) {
        Holder holder = new Holder(memberId);
        DateRange dateRange = toDateRange(request);
        IncomeStatementData incomeStatement = incomeStatementAssemblingService.assembleIncomeStatement(holder, dateRange);

        return ResponseEntity.ok(incomeStatement);
    }

    private DateRange toDateRange(IncomeStatementRequest request) {
        LocalDate begin = LocalDate.parse(request.getBeginDate());
        LocalDate inclusiveEnd = LocalDate.parse(request.getInclusiveEndDate());
        LocalDate exclusiveEnd = inclusiveEnd.plusDays(1);
        return DateRange.of(begin, exclusiveEnd);
    }

}
