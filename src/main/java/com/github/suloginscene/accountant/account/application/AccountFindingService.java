package com.github.suloginscene.accountant.account.application;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.AccountRepository;
import com.github.suloginscene.accountant.common.Holder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountFindingService {

    private final AccountRepository accountRepository;


    public List<AccountSimpleData> findAccounts(Holder holder) {
        List<Account> accounts = accountRepository.findByHolder(holder);
        return accounts.stream()
                .map(AccountSimpleData::new)
                .collect(toList());
    }

    public AccountData findAccount(Long id) {
        Account account = accountRepository.findById(id);
        return new AccountData(account);
    }

}
