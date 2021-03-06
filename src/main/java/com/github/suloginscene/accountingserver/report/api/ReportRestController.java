package com.github.suloginscene.accountingserver.report.api;

import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.report.api.request.IncomeStatementRequest;
import com.github.suloginscene.accountingserver.report.application.BalanceSheetAssemblingService;
import com.github.suloginscene.accountingserver.report.application.IncomeStatementAssemblingService;
import com.github.suloginscene.accountingserver.report.application.LedgerFindingService;
import com.github.suloginscene.accountingserver.report.application.data.BalanceSheetData;
import com.github.suloginscene.accountingserver.report.application.data.IncomeStatementData;
import com.github.suloginscene.accountingserver.report.application.data.LedgerData;
import com.github.suloginscene.security.Authenticated;
import com.github.suloginscene.time.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportRestController {

    public static final String PATH = "/api/reports";

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
    ResponseEntity<IncomeStatementData> getIncomeStatement(@Valid IncomeStatementRequest request,
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
