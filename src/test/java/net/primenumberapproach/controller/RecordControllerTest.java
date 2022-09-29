package net.primenumberapproach.controller;

import net.primenumberapproach.dto.Approach;
import net.primenumberapproach.exception.InvalidRequestBody;
import net.primenumberapproach.service.RecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = RecordController.class)
@WithMockUser
class RecordControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecordService recordService;

    @Test
    void getAllApproaches() throws Exception {
        Approach[] approaches = { new Approach(1 , "Basic Approach"), new Approach(2 ,"Optimized Basic Approach")};
        Mockito.when(recordService.getAllApproaches()).thenReturn(approaches);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/approaches").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "[{\"id\":1,\"name\":\"Basic Approach\"},{\"id\":2,\"name\":\"Optimized Basic Approach\"}]";


        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }


    @Test
    void calculatePrime() throws Exception{
        String requestBody = "{\"upperBound\": 20 ,\"lowerBound\": 6,\"approachId\":2}";
        Integer[] primeNumbers = {2, 3, 5};
        Mockito.when(recordService.calculatePrime(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(primeNumbers);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/calculation")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[2,3,5]";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    void calculatePrimeWithException() throws Exception{
        String requestBody = "{\"upperBound\": 2 ,\"lowerBound\": 6,\"approachId\":2}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/calculation")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);

       mockMvc.perform(MockMvcRequestBuilders
                .post("/api/calculation")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidRequestBody))
                .andExpect(result -> assertEquals("Invalid Range", result.getResolvedException().getMessage()));



    }
}