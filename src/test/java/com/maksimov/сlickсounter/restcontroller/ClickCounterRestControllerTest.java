package com.maksimov.сlickсounter.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimov.сlickсounter.ClickCounterApplication;
import com.maksimov.сlickсounter.db.repository.ClickCounterRepository;
import com.maksimov.сlickсounter.service.ClickCounterService;
import lombok.val;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = ClickCounterApplication.class)
@RunWith(SpringRunner.class)
public class ClickCounterRestControllerTest {
    MockMvc mockMvc;

    @Autowired
    ClickCounterService clickCounterService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ClickCounterRepository clickCounterRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Before
    public void setUp() {
        val builder =
                MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                        .apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation));
        this.mockMvc = builder.build();
    }

    String uri = "/content";

    @Test
    public void readDefault() throws Exception{
        MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document(uri)).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals("0", result );
    }

    @Test
    @Transactional
    public void updateAndRead100Values() throws Exception{
        for (int i = 0; i < 100; i++) {
            clickCounterService.update();
        }
        MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document(uri)).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals("100", result );
    }

    @Test
    public void checkContentType() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andDo(MockMvcRestDocumentation.document(uri));

    }
}