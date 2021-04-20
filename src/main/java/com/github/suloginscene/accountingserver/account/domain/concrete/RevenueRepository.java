package com.github.suloginscene.accountingserver.account.domain.concrete;

import com.github.suloginscene.accountingserver.common.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    List<Revenue> findByHolder(Holder holder);

}
