package com.github.suloginscene.accountingserver.account.domain.concrete;

import com.github.suloginscene.accountingserver.common.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LiabilityRepository extends JpaRepository<Liability, Long> {

    List<Liability> findByHolder(Holder holder);

}
