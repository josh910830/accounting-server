package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정 설정 서비스")
class AccountConfiguringServiceTest extends IntegrationTest {

    @Autowired AccountConfiguringService accountConfiguringService;


    // TODO 입력값 검증은 웹계층
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
    @DisplayName("예산 변경 - 변경")
    void changeBudget_onSuccess_updatesBudget() {
        // TODO
    }

    @Test
    @DisplayName("예산 변경(저량 계정) - 예외 발생")
    void changeBudget_onStock_throwsException() {
        // TODO
    }

    @Test
    @DisplayName("계정 삭제 - 삭제")
    void deleteAccount_onSuccess_removesAccount() {
        // TODO
    }

    @Test
    @DisplayName("계정 삭제(저량 계정 잔고 남음) - 예외 발생")
    void deleteAccount_onBalanceRemain_throwsException() {
        // TODO
    }

}
