package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.HOLDER;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.liability;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정 조회 서비스")
class AccountFindingServiceTest extends IntegrationTest {

    @Autowired AccountFindingService accountFindingService;


    @Test
    @DisplayName("정상 - 계정 리스트 반환")
    void find_onSuccess_returnsList() {
        Asset asset = asset(1);
        Liability liability = liability(1);
        Revenue revenue = revenue(1);
        Expense expense = expense(1);
        repositoryFacade.given(asset, liability, revenue, expense);

        List<AccountSimpleData> accounts = accountFindingService.findAccounts(HOLDER);

        assertThat(accounts).hasSize(4);
    }

}
