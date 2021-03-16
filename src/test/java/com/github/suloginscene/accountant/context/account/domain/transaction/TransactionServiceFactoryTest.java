package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.transaction.impl.BorrowTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.PurchaseByCashTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.PurchaseByCreditTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.RepayTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.SellTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.TransferTransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.BORROW;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.PURCHASE_BY_CREDIT;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.REPAY;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.SELL;
import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.TRANSFER;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스 팩토리")
class TransactionServiceFactoryTest {

    @Test
    @DisplayName("판매 생성")
    void createSell() {
        TransactionService sell = TransactionServiceFactory.create(SELL);

        assertThat(sell).isInstanceOf(SellTransactionService.class);
    }

    @Test
    @DisplayName("현금구매 생성")
    void createPurchaseByCash() {
        TransactionService purchaseByCash = TransactionServiceFactory.create(PURCHASE_BY_CASH);

        assertThat(purchaseByCash).isInstanceOf(PurchaseByCashTransactionService.class);
    }

    @Test
    @DisplayName("신용구매 생성")
    void createPurchaseByCredit() {
        TransactionService purchaseByCredit = TransactionServiceFactory.create(PURCHASE_BY_CREDIT);

        assertThat(purchaseByCredit).isInstanceOf(PurchaseByCreditTransactionService.class);
    }

    @Test
    @DisplayName("대출 생성")
    void createBorrow() {
        TransactionService borrow = TransactionServiceFactory.create(BORROW);

        assertThat(borrow).isInstanceOf(BorrowTransactionService.class);
    }

    @Test
    @DisplayName("상환 생성")
    void createRepay() {
        TransactionService repay = TransactionServiceFactory.create(REPAY);

        assertThat(repay).isInstanceOf(RepayTransactionService.class);
    }

    @Test
    @DisplayName("이체 생성")
    void createTransfer() {
        TransactionService transfer = TransactionServiceFactory.create(TRANSFER);

        assertThat(transfer).isInstanceOf(TransferTransactionService.class);
    }

}