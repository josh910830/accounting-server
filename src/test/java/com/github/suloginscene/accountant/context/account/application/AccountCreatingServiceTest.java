package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DisplayName("계정 생성 서비스")
class AccountCreatingServiceTest {

    @Autowired AccountCreatingService accountCreatingService;
    @Autowired AccountRepository accountRepository;

    AccountCreationParameter param;


    @BeforeEach
    void setup() {
        Holder holder = new Holder(1L);
        String name = "계정명";
        Money money = Money.of(1);
        param = new AccountCreationParameter(ASSET, holder, name, money);
    }

    @AfterEach
    void clear() {
        // TODO refactor with proxy
        System.out.println("clear");
        accountRepository.deleteAll();
    }


    @Test
    @DisplayName("생성 성공 - Id 반환")
    void createAccount_onSuccess_returnsId() {
        Long id = accountCreatingService.createAccount(param);

        assertThat(id).isNotNull();
    }

}
