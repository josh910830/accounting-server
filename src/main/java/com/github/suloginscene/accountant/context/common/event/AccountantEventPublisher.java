package com.github.suloginscene.accountant.context.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AccountantEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    public void publish(AccountantEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

}
