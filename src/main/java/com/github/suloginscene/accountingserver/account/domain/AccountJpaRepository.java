package com.github.suloginscene.accountingserver.account.domain;

import com.github.suloginscene.accountingserver.common.Holder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


interface AccountJpaRepository extends JpaRepository<Account, Long> {

    List<Account> findByHolder(Holder holder);

    void deleteByHolder(Holder holder);

    @Modifying
    @Query("delete from SingleTransaction as t where t.account=:account")
    void bulkDeleteChildren(@Param("account") Account account);

    @Modifying
    @Query("delete from SingleTransaction as t where t.account in :accounts")
    void bulkDeleteChildren(@Param("accounts") List<Account> accounts);

}
