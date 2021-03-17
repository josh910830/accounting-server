package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.HOLDER;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DisplayName("계정 생성 서비스")
class AccountCreatingServiceTest {

    @Autowired AccountCreatingService accountCreatingService;
    @Autowired RepositoryFacade repositoryFacade;

    AccountCreationData data;


    @BeforeEach
    void setup() {
        String name = "계정명";
        Money money = Money.of(1);
        data = new AccountCreationData(ASSET, HOLDER, name, money);
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("생성 성공 - Id 반환")
    void createAccount_onSuccess_returnsId() {
        Long id = accountCreatingService.createAccount(data);

        assertThat(id).isNotNull();
    }

}
