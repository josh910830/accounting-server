package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.exception.NotFoundException;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class AccountRepository {

    private final AccountJpaRepository accountJpaRepository;


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
        accountJpaRepository.deleteAll();
    }

}
