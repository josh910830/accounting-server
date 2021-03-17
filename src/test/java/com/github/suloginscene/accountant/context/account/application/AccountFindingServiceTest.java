package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.liability;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DisplayName("계정 조회 서비스")
class AccountFindingServiceTest {

    @Autowired AccountFindingService accountFindingService;
    @Autowired RepositoryFacade repositoryFacade;

    Holder holder;


    @BeforeEach
    void setup() {
        holder = DefaultAccounts.HOLDER;
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("정상 - 계정 리스트 반환")
    void find_onSuccess_returnsList() {
        repositoryFacade.given(asset(1), liability(1), revenue(), expense());

        List<Account> accounts = accountFindingService.findAccounts(holder);

        assertThat(accounts).hasSize(4);
    }

}
