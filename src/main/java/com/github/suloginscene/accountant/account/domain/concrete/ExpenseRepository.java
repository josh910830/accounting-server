package com.github.suloginscene.accountant.account.domain.concrete;

import com.github.suloginscene.accountant.common.Holder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByHolder(Holder holder);

}
