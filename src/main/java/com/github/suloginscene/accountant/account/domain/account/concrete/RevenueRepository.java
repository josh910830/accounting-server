package com.github.suloginscene.accountant.account.domain.account.concrete;

import com.github.suloginscene.accountant.common.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    List<Revenue> findByHolder(Holder holder);

}
