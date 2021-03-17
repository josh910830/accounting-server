package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
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

}
