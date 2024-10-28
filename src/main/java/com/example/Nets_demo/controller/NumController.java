package com.example.Nets_demo.controller;

import com.example.Nets_demo.Exceptions.NoContentException;
import com.example.Nets_demo.Exceptions.NumNotFoundException;
import com.example.Nets_demo.Exceptions.WrongDataTypeException;
import com.example.Nets_demo.DTO.NumDTO;
import com.example.Nets_demo.model.Nums;
import com.example.Nets_demo.service.NumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NumController {

  private NumService service;

  @Autowired
  public NumController(NumService service) {
    this.service = service;
  }

  @GetMapping("/get")
  public Iterable<Nums> get() throws NoContentException {
    return service.getNums();
  }

  @PostMapping("/numbers")
  public Iterable<Nums> addNums(@RequestBody NumDTO dto) throws WrongDataTypeException {
    return service.postNums(dto);
  }

  @PutMapping("/updateById/{id}")
  public Nums updateNums(@PathVariable int id, @RequestBody Nums nums) throws Exception {
    return service.updateNums(id, nums);
  }

  @DeleteMapping("/deleteNumById/{id}")
  public Iterable<Nums> deleteNums(@PathVariable int id)
      throws NumNotFoundException, NoContentException {
    service.deleteNums(id);
    return service.getNums();
  }
}
  /*@GetMapping("/get")
  public ResponseEntity<List<Nums>> getNums() {
    try {
      List<Nums> numList = numRepo.findAll();
      if (numList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(numList, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/numbers")
  public ResponseEntity<List<Nums>> addNums(@RequestBody NumberListWrapper wrapper) {
    try {
      List<Nums> savedNums = new ArrayList<>();
      for (Integer number : wrapper.getNumbers()) {
        if (number == null) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Nums numEntity = new Nums();
        numEntity.setNumber(number);
        savedNums.add(numRepo.save(numEntity));
      }
      return new ResponseEntity<>(savedNums, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/updateById/{id}")
  public ResponseEntity<Nums> updateNums(
      @PathVariable Long id, @RequestBody NumberListWrapper wrapper) {
    return numRepo
        .findById(id)
        .map(
            oldNum -> {
              if (wrapper.getNumbers() != null && !wrapper.getNumbers().isEmpty()) {
                oldNum.setNumber(wrapper.getNumbers().get(0));
              }
              Nums updatedNum = numRepo.save(oldNum);
              return new ResponseEntity<>(updatedNum, HttpStatus.OK);
            })
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/deleteNumById/{id}")
  public ResponseEntity<Void> deleteNums(@PathVariable Long id) {
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
  }*/

