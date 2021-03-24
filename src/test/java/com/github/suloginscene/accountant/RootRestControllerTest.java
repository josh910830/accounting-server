package com.github.suloginscene.accountant;

import com.github.suloginscene.accountant.testing.base.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.github.suloginscene.accountant.lib.test.RequestBuilder.ofGet;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RootRestControllerTest extends ControllerTest {

    static final String URL = linkTo(RootRestController.class).toString();


    @Test
    void getIndex() throws Exception {
        ResultActions when = mockMvc.perform(
                ofGet(URL).build());

        ResultActions then = when.andExpect(status().isOk());

        then.andDo(document("index"));
    }

}
