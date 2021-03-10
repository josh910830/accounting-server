package com.github.suloginscene.accountantserver.common.holder;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;


@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Holder {

    private Long id;


    public Holder(Long id) {
        this.id = id;
    }


    public Long get() {
        return id;
    }

}
