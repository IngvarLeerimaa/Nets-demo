package com.example.Nets_demo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Nets_demo.model.Nums;
import com.example.Nets_demo.repo.NumRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class NumControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private NumRepo numRepo;

  @BeforeEach
  public void setup() {
    numRepo.deleteAll();
  }

  @Test
  public void testAddNums_SavesEachNumberSeparately() throws Exception {
    String jsonContent = "{\"numbers\": [1, 2, 3, 4, 5]}";

    mockMvc
        .perform(post("/numbers").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  public void testGetNums_ReturnsAllStoredNumbers() throws Exception {
    Nums num1 = new Nums(0, 1);
    Nums num2 = new Nums(0, 2);
    Nums num3 = new Nums(0, 3);
    numRepo.save(num1);
    numRepo.save(num2);
    numRepo.save(num3);

    mockMvc
        .perform(get("/get"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].number").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].number").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].number").value(3));
  }

  @Test
  public void testDeleteNums_DeletesStoredNumber() throws Exception {
    Nums nums = new Nums(0, 1);
    Nums savedNum = numRepo.save(nums);

    mockMvc.perform(delete("/deleteNumById/" + savedNum.getId())).andExpect(status().isOk());
    mockMvc.perform(get("/get")).andExpect(status().isNoContent());
  }
}
