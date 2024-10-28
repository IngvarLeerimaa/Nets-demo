package com.example.Nets_demo.service;

import com.example.Nets_demo.DAO.NumDAO;
import com.example.Nets_demo.DTO.NumDTO;
import com.example.Nets_demo.Exceptions.*;
import com.example.Nets_demo.model.Nums;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NumService {

  private NumDAO dao;

  @Autowired
  public NumService(NumDAO dao) {
    this.dao = dao;
  }

  public Iterable<Nums> getNums() throws NoContentException {
    List<Nums> numList = dao.findAll();
    if (numList.isEmpty()) {
      throw new NoContentException("Empty db");
    }
    return numList;
  }

  public Iterable<Nums> postNums(NumDTO dto) throws WrongDataTypeException {
    List<Nums> savedNums = new ArrayList<>();
    for (Integer number : dto.getNumbers()) {
      if (number == null) {
        throw new WrongDataTypeException("Wrong dataType");
      }
      Nums numEntity = new Nums();
      numEntity.setNumber(number);
      savedNums.add(dao.save(numEntity));
    }
    return savedNums;
  }

  public Nums updateNums(long id, Nums nums) throws NumNotFoundException {
    Optional<Nums> optionalOldNum = dao.findById(id);

    if (optionalOldNum.isPresent()) {
      Nums oldNum = optionalOldNum.get();
      if (nums.getNumber() != null) {
        oldNum.setNumber(nums.getNumber());
      }
      return dao.save(oldNum);
    } else {
      throw new NumNotFoundException("Not Found");
    }
  }

  public void deleteNums(long id) throws NumNotFoundException {
    if (dao.existsById(id)) {
      dao.deleteById(id);
    } else {
      throw new NumNotFoundException("No such num");
    }
  }
}
