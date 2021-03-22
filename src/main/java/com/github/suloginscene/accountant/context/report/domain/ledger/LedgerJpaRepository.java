package com.github.suloginscene.accountant.context.report.domain.ledger;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import org.springframework.data.jpa.repository.JpaRepository;


interface LedgerJpaRepository extends JpaRepository<Ledger, Holder> {

}
