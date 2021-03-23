package com.github.suloginscene.accountant.report.domain.incomestatement;

import com.github.suloginscene.accountant.account.domain.account.Flow;
import com.github.suloginscene.accountant.common.Money;

import java.util.List;


public class FlowUtils {

    public static Integer sumIndividualOccurredAmounts(List<? extends Flow> flows) {
        return flows.stream()
                .map(Flow::occurred)
                .map(Money::get)
                .reduce(0, Integer::sum);
    }

}
