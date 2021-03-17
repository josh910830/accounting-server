package com.github.suloginscene.accountant.context.account.domain.account.concrete;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByHolder(Holder holder);

}
