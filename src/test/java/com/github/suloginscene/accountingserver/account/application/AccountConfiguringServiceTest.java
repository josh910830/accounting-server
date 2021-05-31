package com.github.suloginscene.accountingserver.account.application;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.Flow;
import com.github.suloginscene.accountingserver.account.domain.Stock;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Revenue;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.testing.base.IntegrationTest;
import com.github.suloginscene.accountingserver.transaction.application.TransactionExecutingService;
import com.github.suloginscene.accountingserver.transaction.application.input.TransactionExecutionInput;
import com.github.suloginscene.exception.NotFoundException;
import com.github.suloginscene.exception.RequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.TRANSFER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("계정 설정 서비스")
class AccountConfiguringServiceTest extends IntegrationTest {

    @Autowired AccountConfiguringService accountConfiguringService;

    @Autowired TransactionExecutingService transactionExecutingService;


    @Test
    @DisplayName("이름 변경 - 변경")
    void changeName_onSuccess_updatesName() {
        Account account = asset();
        given(account);

        Long id = account.getId();
        String newName = "새 이름";
        accountConfiguringService.changeName(id, newName);

        account = sync(account);
        assertThat(account.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("예산 변경(유량 계정) - 변경")
    void changeBudget_onSuccess_updatesBudget() {
        Flow flow = expense();
        given(flow);

        Long id = flow.getId();
        Money newBudget = Money.of(1_000_000);
        accountConfiguringService.changeBudget(id, newBudget);

        flow = sync(flow);
        assertThat(flow.getBudget()).isEqualTo(newBudget);
    }

    @Test
    @DisplayName("예산 변경(저량 계정) - 요청 예외")
    void changeBudget_onStock_throwsException() {
        Stock stock = asset();
        given(stock);

        Long id = stock.getId();
        Money newBudget = Money.of(1_000_000);
        Executable action = () -> accountConfiguringService.changeBudget(id, newBudget);

        assertThrows(RequestException.class, action);
    }

    @Test
    @DisplayName("계정 삭제 - 삭제")
    void deleteAccount_onSuccess_removesAccount() {
        Asset account = asset(0);
        Asset other = asset(1);
        given(account, other);

        Long id = account.getId();
        Long o = other.getId();
        transactionExecutingService.executeTransaction(
                new TransactionExecutionInput(TRANSFER, o, id, MONEY_ONE, DESCRIPTION));
        transactionExecutingService.executeTransaction(
                new TransactionExecutionInput(TRANSFER, id, o, MONEY_ONE, DESCRIPTION));

        accountConfiguringService.delete(id);

        Executable findingAction = () -> sync(account);
        assertThrows(NotFoundException.class, findingAction);
    }

    @Test
    @DisplayName("소유자 전 계정 강제 삭제(계정 존재) - 성공")
    void deleteAccounts_onExistent_deletes() {
        Asset asset = asset(0);
        asset.increase(MONEY_ONE, DESCRIPTION);
        asset.increase(MONEY_ONE, DESCRIPTION);
        Revenue revenue = revenue();
        revenue.occur(MONEY_ONE, DESCRIPTION);
        given(asset, revenue);

        accountConfiguringService.deleteByHolderForce(TESTING_HOLDER);

        Executable findAsset = () -> sync(asset);
        Executable findRevenue = () -> sync(revenue);
        assertThrows(NotFoundException.class, findAsset);
        assertThrows(NotFoundException.class, findRevenue);
    }


    @Test
    @DisplayName("소유자 전 계정 강제 삭제(계정 없음) - 성공")
    void deleteAccounts_onNonExistent_deletes() {
        Executable action = () -> accountConfiguringService.deleteByHolderForce(TESTING_HOLDER);

        assertDoesNotThrow(action);
    }

}
