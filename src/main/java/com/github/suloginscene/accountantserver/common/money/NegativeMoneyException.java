package com.github.suloginscene.accountantserver.common.money;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;


@NoArgsConstructor(access = PROTECTED)
public class NegativeMoneyException extends IllegalArgumentException {

}
