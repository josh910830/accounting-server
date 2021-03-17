package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByHolder(Holder holder);

}
