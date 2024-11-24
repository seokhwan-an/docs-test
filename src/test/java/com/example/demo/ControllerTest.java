package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.support.ApiDocumentUtils.getDocumentRequest;
import static com.example.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private Controller controller;

    @DisplayName("")
    @Test
    void test() throws Exception {
        // given

        Response response = new Response("hello");

        // when
        given(controller.hello()).willReturn(ResponseEntity.ok(response));

        ResultActions result = mockMvc.perform(get("/hello"));

        // then
        result.andExpect(status().isOk())
            .andDo(document("hello",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("res").type(JsonFieldType.STRING).description("응답 데이터")
                )
            ));

    }
}
