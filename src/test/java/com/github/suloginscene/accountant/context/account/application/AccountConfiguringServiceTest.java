package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.account.domain.account.Stock;
import com.github.suloginscene.accountant.context.common.exception.NotFoundException;
import com.github.suloginscene.accountant.context.common.exception.RequestException;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("계정 설정 서비스")
class AccountConfiguringServiceTest extends IntegrationTest {

    @Autowired AccountConfiguringService accountConfiguringService;


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
        Account account = asset(0);
        given(account);

        Long id = account.getId();
        accountConfiguringService.delete(id);

        Executable findingAction = () -> sync(account);
        assertThrows(NotFoundException.class, findingAction);
    }

    @Test
    @DisplayName("계정 삭제(저량 계정 잔고 남음) - 요청 예외")
    void deleteAccount_onBalanceRemain_throwsException() {
        Account account = asset(1);
        given(account);

        Long id = account.getId();
        Executable action = () -> accountConfiguringService.delete(id);

        assertThrows(RequestException.class, action);
    }

}
