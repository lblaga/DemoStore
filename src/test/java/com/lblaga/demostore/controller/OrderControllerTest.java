package com.lblaga.demostore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lblaga.demostore.service.OrderService;
import com.lblaga.demostore.transfer.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link OrderController}, mocks {@link OrderService}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void whenCreateOrderWithoutItem_thenReturnStatus400()
            throws Exception {
        OrderRequest orderRequest = new OrderRequest("buyer1@email.com", new ArrayList<>());

        mvc.perform(MockMvcRequestBuilders.post("/orders")
                .content((new ObjectMapper().writeValueAsString(orderRequest)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }
}
