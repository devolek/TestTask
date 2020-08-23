package ru.devolek.testtask.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cupboards")
@Data
public class Cupboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "cupboard", fetch = FetchType.EAGER)
    private List<Inventory> inventories;
}
