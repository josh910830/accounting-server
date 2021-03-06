package com.github.suloginscene.accountingserver.root.application;

import com.github.suloginscene.accountingserver.account.application.AccountConfiguringService;
import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.report.application.LedgerDeletingService;
import com.github.suloginscene.accountingserver.report.domain.ledger.DoubleTransactionType;
import com.github.suloginscene.accountingserver.report.domain.ledger.Ledger;
import com.github.suloginscene.accountingserver.report.listener.TransactionInformation;
import com.github.suloginscene.accountingserver.testing.base.IntegrationTest;
import com.github.suloginscene.accountingserver.transaction.domain.AccountPair;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.accountingserver.transaction.domain.impl.SellTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.param.TransactionExecutionParameter;
import com.github.suloginscene.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.github.suloginscene.accountingserver.report.listener.EventMappingUtils.mappedInformation;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;


@DisplayName("?????? ?????????")
class RootClearingServiceTest extends IntegrationTest {

    @Autowired RootClearingService rootClearingService;

    @SpyBean LedgerDeletingService ledgerDeletingService;
    @SpyBean AccountConfiguringService accountConfiguringService;


    @Test
    @DisplayName("?????? - ?????? ???????????? ????????? ??????")
    void clearAll_onSuccess_callServices() {
        Asset asset = asset();
        Revenue revenue = revenue();
        AccountPair pair = AccountPair.of(revenue, asset);
        TransactionExecutionParameter param = new TransactionExecutionParameter(pair, MONEY_ONE, DESCRIPTION);
        TransactionExecutedEvent event = new SellTransactionService().execute(param);
        given(asset, revenue);

        TransactionInformation information = mappedInformation(event);
        DoubleTransactionType type = information.getType();
        Account debit = information.getDebit();
        Account credit = information.getCredit();
        Money amount = information.getAmount();
        String description = information.getDescription();

        Ledger ledger = new Ledger(TESTING_HOLDER);
        ledger.scribe(type, debit, credit, amount, description);
        given(ledger);

        rootClearingService.clearAll(TESTING_HOLDER);

        Executable findingAsset = () -> sync(asset);
        Executable findingRevenue = () -> sync(revenue);
        Executable findingLedger = () -> sync(ledger);
        assertThrows(NotFoundException.class, findingAsset);
        assertThrows(NotFoundException.class, findingRevenue);
        assertThrows(NotFoundException.class, findingLedger);

        then(ledgerDeletingService).should().deleteLedger(TESTING_HOLDER);
        then(accountConfiguringService).should().deleteByHolderForce(TESTING_HOLDER);
    }

}
