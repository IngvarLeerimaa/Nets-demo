package com.example.Nets_demo.controller;

import com.example.Nets_demo.controller.NumController;
import com.example.Nets_demo.model.Nums;
import com.example.Nets_demo.repo.NumRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NumControllerTest {

    @Mock
    private NumRepo numRepo;

    @InjectMocks
    private NumController numController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNums_ReturnsListOfNums() {
        Nums nums = new Nums(1, Arrays.asList(1, 2, 3));
        when(numRepo.findAll()).thenReturn(Arrays.asList(nums));

        ResponseEntity<List<Nums>> response = numController.getNums();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(numRepo, times(1)).findAll();
    }

    @Test
    public void testAddNums_SavesNums() {
        Nums nums = new Nums(0, Arrays.asList(1, 2, 3));
        when(numRepo.save(any(Nums.class))).thenReturn(nums);

        ResponseEntity<Nums> response = numController.addNums(nums);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(numRepo, times(1)).save(nums);
    }

    @Test
    public void testUpdateNums_ExistingId_UpdatesNums() {
        Nums oldNums = new Nums(1, Arrays.asList(1, 2, 3));
        Nums updatedNums = new Nums(1, Arrays.asList(4, 5, 6));
        when(numRepo.findById(1L)).thenReturn(Optional.of(oldNums));
        when(numRepo.save(any(Nums.class))).thenReturn(updatedNums);

        ResponseEntity<Nums> response = numController.updateNums(1L, updatedNums);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNums.getNumbers(), response.getBody().getNumbers());
        verify(numRepo, times(1)).findById(1L);
        verify(numRepo, times(1)).save(oldNums);
    }

    @Test
    public void testDeleteNums_DeletesNums() {
        doNothing().when(numRepo).deleteById(1L);

        ResponseEntity<?> response = numController.deleteNums(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(numRepo, times(1)).deleteById(1L);
    }
}
