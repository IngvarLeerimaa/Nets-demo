package com.example.Nets_demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="Numbers")
public class Nums {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection
    private List<Integer> numbers;

    public Nums() {
    }

    public Nums(long id, List<Integer> numbers) {
        this.id = id;
        this.numbers = numbers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "Nums{" +
                "id=" + id +
                ", numbers=" + numbers +
                '}';
    }
}
