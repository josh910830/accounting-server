package com.github.suloginscene.lib.test;

import lombok.NoArgsConstructor;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class ResultParser {

    private static final JacksonJsonParser JSON_PARSER = new JacksonJsonParser();


    public static Map<String, Object> toResponseAsJsonMap(MvcResult mvcResult) throws UnsupportedEncodingException {
        MockHttpServletResponse response = mvcResult.getResponse();
        String responseString = response.getContentAsString();
        return JSON_PARSER.parseMap(responseString);
    }

}
