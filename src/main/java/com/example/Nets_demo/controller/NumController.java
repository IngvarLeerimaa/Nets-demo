package com.example.Nets_demo.controller;

import com.example.Nets_demo.model.Nums;
import com.example.Nets_demo.repo.NumRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class NumController {

    @Autowired
    private NumRepo numRepo;

    @GetMapping("/get")
    public ResponseEntity<List<Nums>> getNums(){
        try {
            List<Nums> numList = numRepo.findAll();
            if (numList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(numList, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/numbers")
    public ResponseEntity<List<Nums>> addNums(@RequestBody NumberListWrapper wrapper) {
        try {
            List<Nums> savedNums = new ArrayList<>();
            for (Integer number : wrapper.getNumbers()) {
                if (number == null) { // Ensure the list doesn't contain null values
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                Nums numEntity = new Nums();
                numEntity.setNumber(number);  // Store each number separately
                savedNums.add(numRepo.save(numEntity));
            }
            return new ResponseEntity<>(savedNums, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/updateById/{id}")
    public ResponseEntity<Nums> updateNums(@PathVariable Long id, @RequestBody NumberListWrapper wrapper) {
        return numRepo.findById(id).map(oldNum -> {
            if (wrapper.getNumbers() != null && !wrapper.getNumbers().isEmpty()) {
                oldNum.setNumber(wrapper.getNumbers().get(0));  // Set the first number from the list
            }
            Nums updatedNum = numRepo.save(oldNum);
            return new ResponseEntity<>(updatedNum, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @DeleteMapping("/deleteNumById/{id}")
    public ResponseEntity<Void> deleteNums(@PathVariable Long id){
        try {
            if (numRepo.existsById(id)) {
                numRepo.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}