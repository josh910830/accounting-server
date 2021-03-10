package com.github.suloginscene.accountant;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class App {

    private static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "file:/accountant/application-db.properties,"
            + "file:/accountant/application-jwt.properties";


    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
