package com.github.suloginscene.accountant.context.common.util;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class UriFactory {

    public static URI of(Object controller, Object... paths) {
        Class<?> aClass = controller.getClass();
        WebMvcLinkBuilder link = linkTo(aClass);

        for (Object path : paths) {
            link = link.slash(path);
        }

        return link.toUri();
    }

}
