package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountCreatingService {

    private final AccountRepository accountRepository;


    public Long createAccount(AccountCreationParameter accountCreationParameter) {
        Account created = Account.create(accountCreationParameter);
        Account saved = accountRepository.save(created);

        return saved.getId();
    }

}
