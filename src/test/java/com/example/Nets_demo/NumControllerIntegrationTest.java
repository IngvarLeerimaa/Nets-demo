package com.example.Nets_demo;

import com.example.Nets_demo.model.Nums;
import com.example.Nets_demo.repo.NumRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NumRepo numRepo;

    @Test
    public void testAddNums() throws Exception {
        String jsonContent = "{\"numbers\": [1, 2, 3, 4, 5]}";

        mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numbers", hasSize(5)));
    }

    @Test
    public void testGetNums() throws Exception {
        numRepo.deleteAll(); // Clear existing data in the repository

        Nums nums = new Nums(0, List.of(1, 2, 3));
        numRepo.save(nums);

        mockMvc.perform(get("/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numbers", hasSize(3)));
    }

    @Test
    public void testUpdateNums() throws Exception {
        Nums nums = new Nums(0, List.of(1, 2, 3));
        Nums savedNums = numRepo.save(nums);

        String jsonContent = "{\"numbers\": [4, 5, 6]}";

        mockMvc.perform(post("/updateById/" + savedNums.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numbers[0]").value(4));
    }

    @Test
    public void testDeleteNums() throws Exception {
        Nums nums = new Nums(0, List.of(1, 2, 3));
        Nums savedNums = numRepo.save(nums);

        mockMvc.perform(delete("/deleteNumById/" + savedNums.getId()))
                .andExpect(status().isOk());
    }
}
