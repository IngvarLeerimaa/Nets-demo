package com.example.Nets_demo.controller;

import com.example.Nets_demo.DTO.NumDTO;
import com.example.Nets_demo.Exceptions.NoContentException;
import com.example.Nets_demo.Exceptions.NumNotFoundException;
import com.example.Nets_demo.Exceptions.WrongDataTypeException;
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
