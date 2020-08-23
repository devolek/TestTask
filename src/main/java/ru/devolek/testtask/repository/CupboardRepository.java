package ru.devolek.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devolek.testtask.model.Cupboard;

@Repository
public interface CupboardRepository extends JpaRepository<Cupboard, Integer> {
    Boolean existsByName(String name);

    Cupboard getByName(String name);
}
