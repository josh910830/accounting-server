package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.lib.exception.NotFoundException;
import com.github.suloginscene.lib.profile.ProfileChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class AccountRepository {

    private final AccountJpaRepository accountJpaRepository;
    private final ProfileChecker profileChecker;


    public Long save(Account newAccount) {
        Account saved = accountJpaRepository.save(newAccount);
        return saved.getId();
    }


    public Account findById(Long id) {
        return accountJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Account.class, id));
    }

    public List<Account> findByHolder(Holder holder) {
        return accountJpaRepository.findByHolder(holder);
    }


    public void deleteById(Long id) {
        accountJpaRepository.deleteById(id);
    }


    public void deleteAll() {
        profileChecker.checkTest();
        accountJpaRepository.deleteAll();
    }

}
