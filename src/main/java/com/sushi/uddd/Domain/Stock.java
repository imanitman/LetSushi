package com.sushi.uddd.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "stocks")
@Getter
@Setter
public class Stock{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long  idFood;
    private int quantity;
    @OneToMany(mappedBy = "stock")
    @JsonIgnore
    List<Food> foods;
}
