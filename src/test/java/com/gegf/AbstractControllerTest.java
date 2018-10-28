package com.gegf;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
@Slf4j
public abstract class AbstractControllerTest {

    @Autowired
    WebApplicationContext wac;

    protected static MockMvc mockMvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(AbstractControllerTest.class);

        try {
            mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        } catch (Exception e) {
            log.error("创建模拟test异常", e);
        }
    }

}
