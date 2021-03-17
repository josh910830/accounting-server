package com.github.suloginscene.accountant.context.account.domain.account.concrete;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByHolder(Holder holder);

}
