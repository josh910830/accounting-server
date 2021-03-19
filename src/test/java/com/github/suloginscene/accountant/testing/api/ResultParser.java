package com.github.suloginscene.accountant.testing.api;

import lombok.NoArgsConstructor;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class ResultParser {

    private static final JacksonJsonParser JSON_PARSER = new JacksonJsonParser();


    public static Map<String, Object> jsonMap(MvcResult mvcResult) throws UnsupportedEncodingException {
        String responseString = mvcResult.getResponse().getContentAsString();
        return JSON_PARSER.parseMap(responseString);
    }

}
