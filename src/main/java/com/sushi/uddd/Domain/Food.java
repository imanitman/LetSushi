package com.sushi.uddd.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long price;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private String logo;

    @OneToMany(mappedBy = "food")
    @JsonIgnore
    private List<Line> lines;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
