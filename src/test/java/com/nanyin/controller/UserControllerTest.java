package com.nanyin.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 单元测试测试Controller层
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void userStandardSex() throws Exception{
        String response = mockMvc.perform(get("/user/sex").
                accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }


    @Test
    public void userStandardStatus() {
    }

    @Test
    public void userInfo() throws Exception{
        mockMvc.perform(get("/user/info").
                accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andExpect(status().isOk()).
                andReturn();
    }
}