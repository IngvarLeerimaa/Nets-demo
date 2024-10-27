package com.example.Nets_demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Nets_demo.model.Nums;
import com.example.Nets_demo.repo.NumRepo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class NumControllerTest {

  @Mock private NumRepo numRepo;

  @InjectMocks private NumController numController;

  @Autowired private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetNums_ReturnsListOfNums() {
    Nums num1 = new Nums(1, 10);
    Nums num2 = new Nums(2, 20);
    when(numRepo.findAll()).thenReturn(Arrays.asList(num1, num2));

    ResponseEntity<List<Nums>> response = numController.getNums();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    verify(numRepo, times(1)).findAll();
  }

  @Test
  public void testAddNums_SavesEachNumSeparately() {
    List<Integer> numbers = Arrays.asList(1, 2, 3);
    NumberListWrapper wrapper = new NumberListWrapper();
    wrapper.setNumbers(numbers);

    when(numRepo.save(any(Nums.class))).thenAnswer(invocation -> invocation.getArgument(0));

    ResponseEntity<List<Nums>> response = numController.addNums(wrapper);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(3, response.getBody().size());

    verify(numRepo, times(3)).save(any(Nums.class));
  }

  @Test
  public void testAddNums_ReturnsBadRequestForInvalidInput() throws Exception {
    String invalidJson = "{ \"numbers\": [1, \"invalid\", 3] }";

    mockMvc
        .perform(post("/numbers").contentType("application/json").content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateNums_ExistingId_UpdatesNums() {
    Nums oldNum = new Nums(1, 10);
    Nums updatedNum = new Nums(1, 20);
    NumberListWrapper wrapper = new NumberListWrapper();
    wrapper.setNumbers(Arrays.asList(20));

    when(numRepo.findById(1L)).thenReturn(Optional.of(oldNum));
    when(numRepo.save(any(Nums.class))).thenReturn(updatedNum);

    ResponseEntity<Nums> response = numController.updateNums(1L, wrapper);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedNum.getNumber(), response.getBody().getNumber());
    verify(numRepo, times(1)).findById(1L);
    verify(numRepo, times(1)).save(oldNum);
  }

  @Test
  public void testDeleteNums_DeletesNums() {
    when(numRepo.existsById(1L)).thenReturn(true);
    doNothing().when(numRepo).deleteById(1L);

    ResponseEntity<?> response = numController.deleteNums(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    verify(numRepo, times(1)).deleteById(1L);
  }
}
