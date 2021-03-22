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

        Account newAccount = AccountFactory.create(param);
        return accountRepository.save(newAccount);
    }

    private AccountCreationParameter toParam(AccountCreationInput input) {
        return new AccountCreationParameter(
                input.getType(), input.getHolder(), input.getName(), input.getMoney());
    }

}
