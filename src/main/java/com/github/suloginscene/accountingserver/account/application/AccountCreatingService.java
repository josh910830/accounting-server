package com.github.suloginscene.accountingserver.account.application;

import com.github.suloginscene.accountingserver.account.application.input.AccountCreationInput;
import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.AccountFactory;
import com.github.suloginscene.accountingserver.account.domain.AccountRepository;
import com.github.suloginscene.accountingserver.account.domain.param.AccountCreationParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountCreatingService {

    private final AccountRepository accountRepository;


    public Long createAccount(AccountCreationInput input) {
        AccountCreationParameter param = toParam(input);

        Account newAccount = AccountFactory.create(param);
        return accountRepository.save(newAccount);
    }

    private AccountCreationParameter toParam(AccountCreationInput input) {
        return new AccountCreationParameter(
                input.getType(), input.getHolder(), input.getName(), input.getMoney());
    }

}
