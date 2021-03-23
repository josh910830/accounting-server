package com.github.suloginscene.accountant.report.domain.ledger;

import com.github.suloginscene.accountant.common.Holder;
import org.springframework.data.jpa.repository.JpaRepository;


interface LedgerJpaRepository extends JpaRepository<Ledger, Holder> {

}
