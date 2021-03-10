package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.holder.Holder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import static javax.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Inheritance(strategy = JOINED)
@NoArgsConstructor(access = PROTECTED)
public abstract class Account {

    @Id @GeneratedValue
    private Long id;

    private Holder holder;

    private String name;


    protected Account(Holder holder, String name) {
        this.holder = holder;
        this.name = name;
    }

}
