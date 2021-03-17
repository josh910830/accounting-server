package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountFactory;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
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

        Account created = AccountFactory.create(param);
        Account saved = accountRepository.save(created);

        return saved.getId();
    }

    private AccountCreationParameter toParam(AccountCreationInput input) {
        return new AccountCreationParameter(
                input.getAccountType(), input.getHolder(), input.getName(), input.getMoney());
    }

}
