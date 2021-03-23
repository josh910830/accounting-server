package com.github.suloginscene.accountant.account.application;

import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.ACCOUNT_NAME;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정 생성 서비스")
class AccountCreatingServiceTest extends IntegrationTest {

    @Autowired AccountCreatingService accountCreatingService;


    @Test
    @DisplayName("생성 성공 - Id 반환")
    void createAccount_onSuccess_returnsId() {
        AccountCreationInput input = new AccountCreationInput(TESTING_HOLDER, ASSET, ACCOUNT_NAME, MONEY_ONE);
        Long id = accountCreatingService.createAccount(input);

        assertThat(id).isNotNull();
    }

}
