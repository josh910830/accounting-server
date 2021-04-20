package com.github.suloginscene.accountingserver.account.application;

import com.github.suloginscene.accountingserver.account.application.data.AccountData;
import com.github.suloginscene.accountingserver.account.application.data.AccountSimpleData;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.TESTING_HOLDER;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정 조회 서비스")
class AccountFindingServiceTest extends IntegrationTest {

    @Autowired AccountFindingService accountFindingService;


    @Test
    @DisplayName("리스트 - 단순 계정 데이터 리스트 반환")
    void findList_onSuccess_returnsDataList() {
        given(asset(), liability(), revenue(), expense());

        List<AccountSimpleData> accounts = accountFindingService.findAccounts(TESTING_HOLDER);

        assertThat(accounts).hasSize(4);
    }

    @Test
    @DisplayName("계정 - 계정 데이터 반환")
    void findOne_onSuccess_returnsData() {
        Asset asset = asset();
        asset.increase(MONEY_ONE, DESCRIPTION);
        given(asset);

        AccountData account = accountFindingService.findAccount(asset.getId());

        assertThat(account.getSingleTransactions()).hasSize(1);
    }

}
