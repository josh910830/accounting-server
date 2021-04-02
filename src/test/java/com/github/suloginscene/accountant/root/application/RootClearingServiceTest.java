package com.github.suloginscene.accountant.root.application;

import com.github.suloginscene.accountant.account.application.AccountConfiguringService;
import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Revenue;
import com.github.suloginscene.accountant.report.application.LedgerDeletingService;
import com.github.suloginscene.accountant.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import com.github.suloginscene.accountant.transaction.domain.AccountPair;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.accountant.transaction.domain.TransactionService;
import com.github.suloginscene.accountant.transaction.domain.TransactionServiceFactory;
import com.github.suloginscene.accountant.transaction.domain.param.TransactionExecutionParameter;
import com.github.suloginscene.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.github.suloginscene.accountant.report.listener.EventTransformUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.SELL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;


@DisplayName("정리 서비스")
class RootClearingServiceTest extends IntegrationTest {

    @Autowired RootClearingService rootClearingService;

    @SpyBean LedgerDeletingService ledgerDeletingService;
    @SpyBean AccountConfiguringService accountConfiguringService;


    @Test
    @DisplayName("정리")
    void clearAll_onSuccess_callServices() {
        Asset asset = asset();
        Revenue revenue = revenue();
        AccountPair pair = AccountPair.of(revenue, asset);
        TransactionExecutionParameter param = new TransactionExecutionParameter(pair, MONEY_ONE, DESCRIPTION);
        TransactionService sell = TransactionServiceFactory.create(SELL);
        TransactionExecutedEvent event = sell.execute(param);
        given(asset, revenue);

        DoubleTransaction doubleTransaction = toDoubleTransaction(event);
        Ledger ledger = new Ledger(TESTING_HOLDER);
        ledger.writeDoubleTransaction(doubleTransaction);
        given(ledger);

        rootClearingService.clearAll(TESTING_HOLDER);

        Executable findingAsset = () -> sync(asset);
        Executable findingRevenue = () -> sync(revenue);
        Executable findingLedger = () -> sync(ledger);
        assertThrows(NotFoundException.class, findingAsset);
        assertThrows(NotFoundException.class, findingRevenue);
        assertThrows(NotFoundException.class, findingLedger);

        then(ledgerDeletingService).should().deleteLedger(TESTING_HOLDER);
        then(accountConfiguringService).should().deleteByHolder(TESTING_HOLDER);
    }

}
