package com.github.suloginscene.accountingserver.account.application;

import com.github.suloginscene.accountingserver.account.application.data.AccountData;
import com.github.suloginscene.accountingserver.account.application.data.AccountSimpleData;
import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.AccountRepository;
import com.github.suloginscene.accountingserver.common.Holder;
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
