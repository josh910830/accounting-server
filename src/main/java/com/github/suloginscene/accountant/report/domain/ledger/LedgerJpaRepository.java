package com.github.suloginscene.accountant.report.domain.ledger;

import com.github.suloginscene.accountant.common.Holder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


interface LedgerJpaRepository extends JpaRepository<Ledger, Holder> {

    @Modifying
    @Query("delete from DoubleTransaction as t where t.ledger=:ledger")
    void bulkDeleteChildren(@Param("ledger") Ledger ledger);

}
