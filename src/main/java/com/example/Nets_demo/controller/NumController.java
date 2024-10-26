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
            List<Nums> numList = new ArrayList<>();
            numRepo.findAll().forEach(numList::add);
            if (numList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(numList, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/post")
    public ResponseEntity<Nums> addNums(@RequestBody Nums nums){
        try {
            Nums numObj = numRepo.save(nums);
            return new ResponseEntity<>(numObj, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/updateById/{id}")
    public ResponseEntity<Nums> updateNums(@PathVariable Long id, @RequestBody Nums nums){
    try {
        Optional<Nums> oldNum = numRepo.findById(id);
        if (oldNum.isPresent()) {
            Nums newNumData = oldNum.get();
            newNumData.setNumbers(nums.getNumbers());

            Nums numObj = numRepo.save(newNumData);
            return new ResponseEntity<>(numObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @DeleteMapping("/deleteNumById/{id}")
    public ResponseEntity<ResponseEntity<List<Nums>>> deleteNums(@PathVariable Long id){
        numRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
