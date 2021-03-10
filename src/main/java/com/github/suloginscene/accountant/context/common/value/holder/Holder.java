package com.github.suloginscene.accountant.context.common.value.holder;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;


@Embeddable
@EqualsAndHashCode @ToString
@NoArgsConstructor(access = PROTECTED)
public class Holder {

    @Column(name = "holder_id")
    private Long id;


    public Holder(Long id) {
        this.id = id;
    }


    public Long get() {
        return id;
    }

}
