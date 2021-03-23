package com.github.suloginscene.accountant.lib.profile;

import com.github.suloginscene.accountant.lib.exception.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.github.suloginscene.accountant.lib.profile.Profiles.TEST;


@Component
@RequiredArgsConstructor
public class ProfileChecker {

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
