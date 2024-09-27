package com.sushi.uddd.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String Role;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Cart>  carts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

}
