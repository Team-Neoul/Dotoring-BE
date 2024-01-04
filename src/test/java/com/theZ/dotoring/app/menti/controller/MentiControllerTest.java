package com.theZ.dotoring.app.menti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theZ.dotoring.app.desiredField.repository.QueryDesiredFieldRepository;
import com.theZ.dotoring.app.menti.dto.UpdatePreferredMentoringRqDTO;
import com.theZ.dotoring.app.mento.dto.UpdateTagsRqDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:db/init.sql")
class MentiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("해당 멘토의 전공은 소프트웨어공학과, 수학교육과이고, 관심 분야는 진로, 개발_언어이다.")
    @WithUserDetails(value = "dotoring11")
    void findAllMenti() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/api/menti")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findAllMenti_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.pageable.nickname").value("가나다2"));
    }

    @Test
    @DisplayName("해당 멘토의 전공은 소프트웨어공학과, 수학교육과이고, 관심 분야는 진로, 개발_언어이다." +
            "last는 false가 나와야한다." +
            "size는 5가 나와야한다.")
    @WithUserDetails(value = "dotoring11")
    void findAllMenti_first_size5() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/api/menti")
                        .param("size","5")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findAllMenti_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.pageable.nickname").value("가나다2"));
        resultActions.andExpect(jsonPath("$.response.last").value(false));
        resultActions.andExpect(jsonPath("$.response.size").value(5));
    }

    @Test
    @DisplayName("해당 멘토의 전공은 소프트웨어공학과, 수학교육과이고, 관심 분야는 진로, 개발_언어이다." +
            "last는 true가 나와야한다." +
            "size는 5가 나와야한다.")
    @WithUserDetails(value = "dotoring11")
    void findAllMenti_last_size5() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/api/menti")
                        .param("size","5")
                        .param("lastMentiId","6")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findAllMenti_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.pageable.nickname").value("가나다2"));
        resultActions.andExpect(jsonPath("$.response.last").value(true));
        resultActions.andExpect(jsonPath("$.response.size").value(5));
    }

    @Test
    @WithUserDetails(value = "dotoring11")
    void findMentiById() throws Exception {

        String mentiId = "1";

        ResultActions resultActions = mockMvc.perform(
                get("/api/menti/" + mentiId)
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findMentoById_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.grade").value(3));
    }

    @Test
    @WithUserDetails(value = "dotoring1")
    void findMyMenti() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/api/menti/my-page")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findMyMenti_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.mentiId").exists());
        resultActions.andExpect(jsonPath("$.response.grade").exists());
        resultActions.andExpect(jsonPath("$.response.nickname").exists());
        resultActions.andExpect(jsonPath("$.response.profileImage").exists());
        resultActions.andExpect(jsonPath("$.response.tags").exists());
        resultActions.andExpect(jsonPath("$.response.majors").exists());
        resultActions.andExpect(jsonPath("$.response.fields").exists());
    }

    @Test
    @WithUserDetails(value = "dotoring1")
    void updateTags() throws Exception {

        UpdateTagsRqDTO updateTagsRqDTO = new UpdateTagsRqDTO(List.of("#운영체제"));

        ResultActions resultActions = mockMvc.perform(
                patch("/api/menti/tags")
                        .content(objectMapper.writeValueAsString(updateTagsRqDTO))
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("updateTags_test : " + responseBody);

        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "dotoring1")
    void updatePreferredMentoring() throws Exception {

        UpdatePreferredMentoringRqDTO updatePreferredMentoringRqDTO = new UpdatePreferredMentoringRqDTO("온라인을 매우매우 선호합니다.");

        ResultActions resultActions = mockMvc.perform(
                patch("/api/menti/preferred-mentoring")
                        .content(objectMapper.writeValueAsString(updatePreferredMentoringRqDTO))
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("updateTags_test : " + responseBody);

        resultActions.andExpect(status().isOk());
    }

}