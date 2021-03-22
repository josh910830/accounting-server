package com.github.suloginscene.accountant.context.common.util;

import com.github.suloginscene.accountant.context.common.exception.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class ProfileChecker {

    private static final String TEST = "test";

    private final Environment environment;
    private final Set<String> activeProfiles = new HashSet<>();


    @PostConstruct
    void readActiveProfiles() {
        activeProfiles.addAll(Arrays.asList(environment.getActiveProfiles()));
    }


    public void checkTest() {
        if (!activeProfiles.contains(TEST)) {
            throw new InternalException("profile is not test");
        }
    }

}
