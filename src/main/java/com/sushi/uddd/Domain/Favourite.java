package com.sushi.uddd.Domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "favourite")
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "favourite_foods",  // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "favourite_id"),  // Cột khóa ngoại tới Favourite
            inverseJoinColumns = @JoinColumn(name = "food_id")  // Cột khóa ngoại tới Food
    )
    private List<Food> foods;
}
