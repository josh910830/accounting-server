package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.accountant.common.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


interface AccountJpaRepository extends JpaRepository<Account, Long> {

    List<Account> findByHolder(Holder holder);

    void deleteByHolder(Holder holder);

}
