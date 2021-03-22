package com.github.suloginscene.accountant.testing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD;
import static org.springframework.http.HttpHeaders.ORIGIN;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@RequiredArgsConstructor(access = PRIVATE)
public class RequestBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final MockHttpServletRequestBuilder builder;


    public static RequestBuilder ofPreflight(String url, String origin, HttpMethod requestMethod, Object... requestHeaders) {
        return new RequestBuilder(options(url))
                .origin(origin)
                .header(ACCESS_CONTROL_REQUEST_METHOD, requestMethod)
                .header(ACCESS_CONTROL_REQUEST_HEADERS, requestHeaders);
    }

    public static RequestBuilder ofPost(String url) {
        return new RequestBuilder(post(url));
    }

    // TODO slash
    public static RequestBuilder ofGet(String url) {
        return new RequestBuilder(get(url));
    }

    public static RequestBuilder ofPut(String url) {
        return new RequestBuilder(put(url));
    }

    public static RequestBuilder ofDelete(String url) {
        return new RequestBuilder(delete(url));
    }


    public RequestBuilder json(Object object) throws JsonProcessingException {
        String json = OBJECT_MAPPER.writeValueAsString(object);
        return new RequestBuilder(
                builder.contentType(APPLICATION_JSON).content(json));
    }

    public RequestBuilder jwt(String jwt) {
        return new RequestBuilder(
                builder.header("X-AUTH-TOKEN", jwt));
    }

    public RequestBuilder origin(String origin) {
        return new RequestBuilder(
                builder.header(ORIGIN, origin));
    }

    public RequestBuilder header(String name, Object... value) {
        if (value.length == 0) {
            return this;
        }
        return new RequestBuilder(
                builder.header(name, value));
    }


    public MockHttpServletRequestBuilder build() {
        return builder;
    }

}
