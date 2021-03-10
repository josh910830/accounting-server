package com.github.suloginscene.accountant.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class SingleTransaction {

    @Id @GeneratedValue
    @Column(name = "single_transaction_id")
    private Long id;

}
