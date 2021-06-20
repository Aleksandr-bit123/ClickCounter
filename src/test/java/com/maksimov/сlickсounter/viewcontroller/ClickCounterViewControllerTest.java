package com.maksimov.сlickсounter.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimov.сlickсounter.ClickCounterApplication;
import com.maksimov.сlickсounter.db.entity.ClickCounterEntity;
import com.maksimov.сlickсounter.db.repository.ClickCounterRepository;
import com.maksimov.сlickсounter.dto.ClickCounter;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest(classes = ClickCounterApplication.class)
@RunWith(SpringRunner.class)

public class ClickCounterViewControllerTest {

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

    String uri = "/";

    @Test
    public void read() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter",0L))
                .andDo(MockMvcRestDocumentation.document(uri));
    }

    @Test
    @Transactional
    public void updateAndRead100Values() throws Exception{
        for (int i = 0; i < 100; i++) {
            clickCounterService.update();
        }
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter",100L))
                .andDo(MockMvcRestDocumentation.document(uri));
    }

    @Test
    @Transactional
    public void updateAndRead10000valuesWithThreads() {

        final Long[] status = {0L};

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                clickCounterService.update();
                if (i==999) {
                    status[0]++;
                }
            }
        });

        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(thread);
            threads[i].start();
        }

        Thread thread1 = new Thread(() -> {
            while (status[0] != 1000L) {
                try {
                    this.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                mockMvc.perform(MockMvcRequestBuilders.get(uri))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(model().attribute("counter",100000L))
                        .andDo(MockMvcRestDocumentation.document(uri));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        thread1.start();
    }

    @Test
    @Transactional
    public void update() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post(uri))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcRestDocumentation.document(uri));
    }

//    ***********************************************Negative tests*****************************************************

    @Test
    @Transactional
    public void readNegativeValue() throws Exception{
        ClickCounter clickCounter = new ClickCounter(-1L);
        ClickCounterEntity clickCounterEntity = objectMapper.convertValue(clickCounter, ClickCounterEntity.class);
        clickCounterRepository.save(clickCounterEntity);
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter","error"))
                .andDo(MockMvcRestDocumentation.document(uri));
    }

    @Test
    @Transactional
    public void updateMaxValue() throws Exception{
        ClickCounter clickCounter = new ClickCounter(Long.MAX_VALUE);
        ClickCounterEntity clickCounterEntity = objectMapper.convertValue(clickCounter, ClickCounterEntity.class);
        clickCounterRepository.save(clickCounterEntity);
        mockMvc.perform(MockMvcRequestBuilders.post(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter","memory is full"))
                .andDo(MockMvcRestDocumentation.document(uri));
    }

    @Test
    @Transactional
    public void readNullCountValue() throws Exception{
        ClickCounter clickCounter = new ClickCounter(null);
        ClickCounterEntity clickCounterEntity = objectMapper.convertValue(clickCounter, ClickCounterEntity.class);
        clickCounterRepository.save(clickCounterEntity);
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter","error"))
                .andDo(MockMvcRestDocumentation.document(uri));
    }

    @Test
    @Transactional
    public void readWithDeletedRecord() throws Exception{
        clickCounterRepository.deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter","error"))
                .andDo(MockMvcRestDocumentation.document(uri));
    }

    @Test
    @Transactional
    public void updateNullCountValue() throws Exception{
        ClickCounter clickCounter = new ClickCounter(null);
        ClickCounterEntity clickCounterEntity = objectMapper.convertValue(clickCounter, ClickCounterEntity.class);
        clickCounterRepository.save(clickCounterEntity);
        mockMvc.perform(MockMvcRequestBuilders.post(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter","error"))
                .andDo(MockMvcRestDocumentation.document(uri));
    }

    @Test
    @Transactional
    public void updateWithDeletedRecord() throws Exception{
        clickCounterRepository.deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.post(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter","error"))
                .andDo(MockMvcRestDocumentation.document(uri));
    }

    @Test
    @Transactional
    public void updateNegativeCountValue() throws Exception{
        ClickCounter clickCounter = new ClickCounter(-101L);
        ClickCounterEntity clickCounterEntity = objectMapper.convertValue(clickCounter, ClickCounterEntity.class);
        clickCounterRepository.save(clickCounterEntity);
        mockMvc.perform(MockMvcRequestBuilders.post(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("counter","error"))
                .andDo(MockMvcRestDocumentation.document(uri));
    }
}