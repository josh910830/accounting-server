package com.github.suloginscene.accountant.context.account.domain.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.BORROW;
import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.PURCHASE_BY_CREDIT;
import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.REPAY;
import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.SELL;
import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.TRANSFER;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스")
class TransactionServiceTest {

    @Test
    @DisplayName("판매 생성 - 판매 객체 반환")
    void createSell_onSuccess_returnsSell() {
        TransactionService sell = TransactionService.create(SELL);

        assertThat(sell).isInstanceOf(SellTransactionService.class);
    }

    @Test
    @DisplayName("현금구매 생성 - 현금구매 객체 반환")
    void createPurchaseByCash_onSuccess_returnsPurchaseByCash() {
        TransactionService purchaseByCash = TransactionService.create(PURCHASE_BY_CASH);

        assertThat(purchaseByCash).isInstanceOf(PurchaseByCashTransactionService.class);
    }

    @Test
    @DisplayName("신용구매 생성 - 신용구매 객체 반환")
    void createPurchaseByCredit_onSuccess_returnsPurchaseByCredit() {
        TransactionService purchaseByCredit = TransactionService.create(PURCHASE_BY_CREDIT);

        assertThat(purchaseByCredit).isInstanceOf(PurchaseByCreditTransactionService.class);
    }

    @Test
    @DisplayName("대출 생성 - 대출 객체 반환")
    void createBorrow_onSuccess_returnsBorrow() {
        TransactionService borrow = TransactionService.create(BORROW);

        assertThat(borrow).isInstanceOf(BorrowTransactionService.class);
    }

    @Test
    @DisplayName("상환 생성 - 상환 객체 반환")
    void createRepay_onSuccess_returnsRepay() {
        TransactionService repay = TransactionService.create(REPAY);

        assertThat(repay).isInstanceOf(RepayTransactionService.class);
    }

    @Test
    @DisplayName("이체 생성 - 이체 객체 반환")
    void createTransfer_onSuccess_returnsTransfer() {
        TransactionService transfer = TransactionService.create(TRANSFER);

        assertThat(transfer).isInstanceOf(TransferTransactionService.class);
    }

}
