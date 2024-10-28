package com.example.Nets_demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.Nets_demo.DAO.NumDAO;
import com.example.Nets_demo.DTO.NumDTO;
import com.example.Nets_demo.Exceptions.NoContentException;
import com.example.Nets_demo.Exceptions.NumNotFoundException;
import com.example.Nets_demo.Exceptions.WrongDataTypeException;
import com.example.Nets_demo.model.Nums;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NumServiceTest {

  @Mock private NumDAO dao;

  @InjectMocks private NumService numService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetNums_ShouldReturnNumsList() throws NoContentException {
    Nums num1 = new Nums();
    num1.setNumber(1);
    Nums num2 = new Nums();
    num2.setNumber(2);
    List<Nums> numList = List.of(num1, num2);

    when(dao.findAll()).thenReturn(numList);

    Iterable<Nums> result = numService.getNums();

    assertNotNull(result);
    assertEquals(numList, result);
    verify(dao, times(1)).findAll();
  }

  @Test
  void testGetNums_ShouldThrowNoContentException() {
    when(dao.findAll()).thenReturn(new ArrayList<>());

    assertThrows(NoContentException.class, () -> numService.getNums());
    verify(dao, times(1)).findAll();
  }

  @Test
  void testPostNums_ShouldSaveNums() throws WrongDataTypeException {
    NumDTO dto = new NumDTO();
    dto.setNumbers(List.of(1, 2, 3)); // Assuming there’s a setter for `numbers`

    List<Nums> savedNums = new ArrayList<>();
    for (Integer number : dto.getNumbers()) {
      Nums numEntity = new Nums();
      numEntity.setNumber(number);
      savedNums.add(numEntity);
      when(dao.save(any(Nums.class))).thenReturn(numEntity);
    }

    Iterable<Nums> result = numService.postNums(dto);

    assertNotNull(result);
    assertEquals(savedNums.size(), ((List<Nums>) result).size());
    verify(dao, times(3)).save(any(Nums.class));
  }

  @Test
  void testDeleteNums_ShouldThrowNumNotFoundException() {
    when(dao.existsById(1L)).thenReturn(false);

    assertThrows(NumNotFoundException.class, () -> numService.deleteNums(1L));
    verify(dao, times(1)).existsById(1L);
    verify(dao, never()).deleteById(anyLong());
  }
}
