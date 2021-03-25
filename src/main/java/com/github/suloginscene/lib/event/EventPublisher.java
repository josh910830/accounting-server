package com.github.suloginscene.lib.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    public void publish(Event event) {
        applicationEventPublisher.publishEvent(event);
    }

}
