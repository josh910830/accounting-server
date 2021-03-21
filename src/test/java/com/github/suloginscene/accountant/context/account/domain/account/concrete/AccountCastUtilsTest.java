package com.github.suloginscene.accountant.context.account.domain.account.concrete;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.account.domain.account.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("계정 변환 유틸리티")
class AccountCastUtilsTest {

    Account asset;
    Account liability;
    Account revenue;
    Account expense;


    @BeforeEach
    void setup() {
        asset = asset();
        liability = liability();
        revenue = revenue();
        expense = expense();
    }


    @Test
    @DisplayName("자산 정상 - 자산 객체 반환")
    void toAsset_onSuccess_returnsInstanceOfAsset() {
        Asset result = AccountCastUtils.toAsset(asset);

        assertThat(result).isInstanceOf(Asset.class);
    }

    @Test
    @DisplayName("자산 실패 - 예외 발생")
    void toAsset_fromInvalidObject_throwsException() {
        Executable fromLiability = () -> AccountCastUtils.toAsset(liability);
        Executable fromRevenue = () -> AccountCastUtils.toAsset(revenue);
        Executable fromExpense = () -> AccountCastUtils.toAsset(expense);

        assertThrows(AccountCastException.class, fromLiability);
        assertThrows(AccountCastException.class, fromRevenue);
        assertThrows(AccountCastException.class, fromExpense);
    }

    @Test
    @DisplayName("부채 정상 - 부채 객체 반환")
    void toLiability_onSuccess_returnsInstanceOfLiability() {
        Liability result = AccountCastUtils.toLiability(liability);

        assertThat(result).isInstanceOf(Liability.class);
    }

    @Test
    @DisplayName("부채 실패 - 예외 발생")
    void toLiability_fromInvalidObject_throwsException() {
        Executable fromAsset = () -> AccountCastUtils.toLiability(asset);
        Executable fromRevenue = () -> AccountCastUtils.toLiability(revenue);
        Executable fromExpense = () -> AccountCastUtils.toLiability(expense);

        assertThrows(AccountCastException.class, fromAsset);
        assertThrows(AccountCastException.class, fromRevenue);
        assertThrows(AccountCastException.class, fromExpense);
    }

    @Test
    @DisplayName("수입 정상 - 수입 객체 반환")
    void toRevenue_onSuccess_returnsInstanceOfRevenue() {
        Revenue result = AccountCastUtils.toRevenue(revenue);

        assertThat(result).isInstanceOf(Revenue.class);
    }

    @Test
    @DisplayName("수입 실패 - 예외 발생")
    void toRevenue_fromInvalidObject_throwsException() {
        Executable fromAsset = () -> AccountCastUtils.toRevenue(asset);
        Executable fromLiability = () -> AccountCastUtils.toRevenue(liability);
        Executable fromExpense = () -> AccountCastUtils.toRevenue(expense);

        assertThrows(AccountCastException.class, fromAsset);
        assertThrows(AccountCastException.class, fromLiability);
        assertThrows(AccountCastException.class, fromExpense);
    }

    @Test
    @DisplayName("지출 정상 - 지출 객체 반환")
    void toExpense_onSuccess_returnsInstanceOfExpense() {
        Expense result = AccountCastUtils.toExpense(expense);

        assertThat(result).isInstanceOf(Expense.class);
    }

    @Test
    @DisplayName("지출 실패 - 예외 발생")
    void toExpense_fromInvalidObject_throwsException() {
        Executable fromAsset = () -> AccountCastUtils.toExpense(asset);
        Executable fromLiability = () -> AccountCastUtils.toExpense(liability);
        Executable fromRevenue = () -> AccountCastUtils.toExpense(revenue);

        assertThrows(AccountCastException.class, fromAsset);
        assertThrows(AccountCastException.class, fromLiability);
        assertThrows(AccountCastException.class, fromRevenue);
    }


    @Test
    @DisplayName("저량 정상 - 저량 객체 반환")
    void toStock_onSuccess_returnsInstanceOfStock() {
        Stock result1 = AccountCastUtils.toStock(asset);
        Stock result2 = AccountCastUtils.toStock(liability);

        assertThat(result1).isInstanceOf(Stock.class);
        assertThat(result2).isInstanceOf(Stock.class);
    }

    @Test
    @DisplayName("저량 실패 - 예외 발생")
    void toStock_fromInvalidObject_throwsException() {
        Executable fromRevenue = () -> AccountCastUtils.toStock(revenue);
        Executable fromExpense = () -> AccountCastUtils.toStock(expense);

        assertThrows(AccountCastException.class, fromRevenue);
        assertThrows(AccountCastException.class, fromExpense);
    }

    @Test
    @DisplayName("유량 정상 - 유량 객체 반환")
    void toFlow_onSuccess_returnsInstanceOfFlow() {
        Flow result1 = AccountCastUtils.toFlow(revenue);
        Flow result2 = AccountCastUtils.toFlow(expense);

        assertThat(result1).isInstanceOf(Flow.class);
        assertThat(result2).isInstanceOf(Flow.class);
    }

    @Test
    @DisplayName("유량 실패 - 예외 발생")
    void toFlow_fromInvalidObject_throwsException() {
        Executable fromAsset = () -> AccountCastUtils.toFlow(asset);
        Executable fromLiability = () -> AccountCastUtils.toFlow(liability);

        assertThrows(AccountCastException.class, fromAsset);
        assertThrows(AccountCastException.class, fromLiability);
    }

}
