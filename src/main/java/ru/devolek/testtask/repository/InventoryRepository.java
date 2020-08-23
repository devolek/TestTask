package ru.devolek.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.devolek.testtask.model.Inventory;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Boolean existsByName(String name);

    @Query(value = "select i from Inventory i where i.cupboard is null")
    List<Inventory> getUnusedInventories();

    Inventory getByName(String name);
}
